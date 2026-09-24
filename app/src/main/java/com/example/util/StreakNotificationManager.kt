package com.example.util

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.media.AudioAttributes
import android.media.RingtoneManager
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import com.example.MainActivity
import com.example.R
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

/**
 * Manages daily local push notifications for streak reminders.
 *
 * Requirements:
 * - 100% offline & local (no remote servers or Firebase).
 * - Schedules a single daily reminder for 7:00 PM.
 * - Title: "🔥 Keep your Brain-Matrix streak alive!"
 * - Body: "You are just a few questions away from your daily goal. Don't lose your streak, play now!"
 * - Sound: default ringtone, High Priority.
 * - Intelligent Cancellation: Automatically cancels or suppresses reminder if user
 *   reaches their 15-point goal for the day.
 */
object StreakNotificationManager {

    const val CHANNEL_ID = "streak_reminder_channel"
    const val CHANNEL_NAME = "Streak Reminders"
    const val NOTIFICATION_ID = 4001
    const val ALARM_REQUEST_CODE = 4002
    const val ACTION_STREAK_REMINDER = "com.example.quiz4exam.STREAK_REMINDER"

    const val REMINDER_HOUR = 19 // 7:00 PM
    const val REMINDER_MINUTE = 0
    const val DAILY_GOAL_POINTS = 15

    /**
     * Initializes the notification channel on Android 8.0+ (API 26+).
     */
    fun createNotificationChannel(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
            val audioAttributes = AudioAttributes.Builder()
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .setUsage(AudioAttributes.USAGE_NOTIFICATION)
                .build()

            val channel = NotificationChannel(
                CHANNEL_ID,
                CHANNEL_NAME,
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = "Daily evening reminder to maintain your Brain-Matrix quiz streak"
                enableLights(true)
                enableVibration(true)
                vibrationPattern = longArrayOf(0, 250, 150, 250)
                setSound(soundUri, audioAttributes)
            }

            val notificationManager = context.getSystemService(NotificationManager::class.java)
            notificationManager?.createNotificationChannel(channel)
        }
    }

    /**
     * Checks if the user has already completed the 15-point goal for today.
     */
    fun isDailyGoalSatisfied(context: Context): Boolean {
        return try {
            val prefs = context.getSharedPreferences("quiz4exam_offline_prefs", Context.MODE_PRIVATE)
            val todayKey = SimpleDateFormat("yyyy-MM-dd", Locale.US).format(Date())
            val todayPoints = prefs.getInt("daily_points_$todayKey", 0)
            todayPoints >= DAILY_GOAL_POINTS
        } catch (_: Exception) {
            false
        }
    }

    /**
     * Schedules a single repeating daily local notification for 7:00 PM.
     * If today's 15-point goal is already achieved, schedules for tomorrow at 7:00 PM.
     */
    fun scheduleDailyStreakReminder(context: Context) {
        createNotificationChannel(context)

        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as? AlarmManager ?: return
        val intent = Intent(context, StreakReminderReceiver::class.java).apply {
            action = ACTION_STREAK_REMINDER
        }

        val pendingIntent = PendingIntent.getBroadcast(
            context,
            ALARM_REQUEST_CODE,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val now = Calendar.getInstance()
        val target = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, REMINDER_HOUR)
            set(Calendar.MINUTE, REMINDER_MINUTE)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }

        // If it's already past 7:00 PM today, or today's goal is already satisfied, schedule for tomorrow
        if (now.after(target) || isDailyGoalSatisfied(context)) {
            target.add(Calendar.DAY_OF_YEAR, 1)
        }

        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                alarmManager.setAndAllowWhileIdle(
                    AlarmManager.RTC_WAKEUP,
                    target.timeInMillis,
                    pendingIntent
                )
            } else {
                alarmManager.set(
                    AlarmManager.RTC_WAKEUP,
                    target.timeInMillis,
                    pendingIntent
                )
            }
        } catch (_: SecurityException) {
            // Graceful silent fallback
        }
    }

    /**
     * Intelligently cancels today's notification when the user hits their 15-point goal.
     * Also dismisses any currently active streak notification.
     */
    fun onGoalAchieved(context: Context) {
        try {
            // Dismiss active notification if displayed
            val notificationManager = NotificationManagerCompat.from(context)
            notificationManager.cancel(NOTIFICATION_ID)

            // Reschedule for tomorrow 7:00 PM so they are not prompted again today
            scheduleDailyStreakReminder(context)
        } catch (_: Exception) {
            // Safe fallback
        }
    }

    /**
     * Displays the local push notification if today's goal is not yet achieved.
     */
    fun showStreakNotification(context: Context) {
        createNotificationChannel(context)

        // Intelligent check: suppress if goal already satisfied today
        if (isDailyGoalSatisfied(context)) {
            // Reschedule for tomorrow
            scheduleDailyStreakReminder(context)
            return
        }

        // Permission check for Android 13+ (API 33+)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(
                    context,
                    android.Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                return
            }
        }

        val launchIntent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
        }

        val pendingIntent = PendingIntent.getActivity(
            context,
            0,
            launchIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)

        val notification = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle("🔥 Keep your Brain-Matrix streak alive!")
            .setContentText("You are just a few questions away from your daily goal. Don't lose your streak, play now!")
            .setStyle(
                NotificationCompat.BigTextStyle()
                    .bigText("You are just a few questions away from your daily goal. Don't lose your streak, play now!")
            )
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setCategory(NotificationCompat.CATEGORY_REMINDER)
            .setSound(soundUri)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)
            .setDefaults(NotificationCompat.DEFAULT_VIBRATE or NotificationCompat.DEFAULT_LIGHTS)
            .build()

        try {
            val notificationManager = NotificationManagerCompat.from(context)
            notificationManager.notify(NOTIFICATION_ID, notification)
        } catch (_: SecurityException) {
            // Handled silently
        }

        // Schedule next reminder for tomorrow
        scheduleDailyStreakReminder(context)
    }
}
