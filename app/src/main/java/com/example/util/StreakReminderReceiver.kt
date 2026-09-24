package com.example.util

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

/**
 * BroadcastReceiver triggered by AlarmManager or System Boot.
 * Dispatches local notification if streak goal hasn't been met.
 */
class StreakReminderReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        when (intent.action) {
            Intent.ACTION_BOOT_COMPLETED -> {
                // Restore scheduled daily reminder on device restart
                StreakNotificationManager.scheduleDailyStreakReminder(context)
            }
            StreakNotificationManager.ACTION_STREAK_REMINDER -> {
                // Trigger local notification
                StreakNotificationManager.showStreakNotification(context)
            }
        }
    }
}
