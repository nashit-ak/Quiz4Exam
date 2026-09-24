package com.example.util

import android.media.AudioAttributes
import android.media.AudioFormat
import android.media.AudioTrack
import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import kotlin.math.PI
import kotlin.math.exp
import kotlin.math.sin

/**
 * Offline Sound Manager providing crisp, synthesized audio feedback for quiz actions,
 * option taps, navigation buttons, success, and level completion celebration fanfares.
 * 100% native with zero external asset dependencies.
 */
object SoundManager {
    private const val TAG = "SoundManager"
    private const val SAMPLE_RATE = 44100
    private val scope = CoroutineScope(Dispatchers.Default + SupervisorJob())

    @Volatile
    private var isEnabled = true

    fun setSoundEnabled(enabled: Boolean) {
        isEnabled = enabled
    }

    fun isSoundEnabled(): Boolean = isEnabled

    /**
     * Plays a crisp, light tactile tap sound for buttons/navigation (25ms).
     */
    fun playTap() {
        if (!isEnabled) return
        scope.launch {
            try {
                val samples = generateTone(
                    frequency = 800.0,
                    durationMs = 25,
                    decayRate = 35.0,
                    volume = 0.45f
                )
                playPcm(samples)
            } catch (e: Throwable) {
                Log.w(TAG, "Tap sound error: ${e.message}")
            }
        }
    }

    /**
     * Plays a pleasant bubble-pop sound when an option is selected (50ms).
     */
    fun playOptionSelected() {
        if (!isEnabled) return
        scope.launch {
            try {
                val samples = generateTone(
                    frequency = 880.0,
                    durationMs = 50,
                    decayRate = 20.0,
                    volume = 0.55f
                )
                playPcm(samples)
            } catch (e: Throwable) {
                Log.w(TAG, "Option sound error: ${e.message}")
            }
        }
    }

    /**
     * Plays a 2-tone bright ascending success chime (D5 -> A5).
     */
    fun playSuccess() {
        if (!isEnabled) return
        scope.launch {
            try {
                val note1 = generateTone(587.33, 90, 10.0, 0.55f)
                val note2 = generateTone(880.00, 150, 8.0, 0.65f)
                val combined = ShortArray(note1.size + note2.size)
                System.arraycopy(note1, 0, combined, 0, note1.size)
                System.arraycopy(note2, 0, combined, note1.size, note2.size)
                playPcm(combined)
            } catch (e: Throwable) {
                Log.w(TAG, "Success sound error: ${e.message}")
            }
        }
    }

    /**
     * Plays a 2-tone descending chime for failure (A4 -> E4).
     */
    fun playFailure() {
        if (!isEnabled) return
        scope.launch {
            try {
                val note1 = generateTone(440.00, 110, 10.0, 0.5f)
                val note2 = generateTone(329.63, 180, 7.0, 0.5f)
                val combined = ShortArray(note1.size + note2.size)
                System.arraycopy(note1, 0, combined, 0, note1.size)
                System.arraycopy(note2, 0, combined, note1.size, note2.size)
                playPcm(combined)
            } catch (e: Throwable) {
                Log.w(TAG, "Failure sound error: ${e.message}")
            }
        }
    }

    /**
     * Plays a vibrant, triumphant 4-tone celebration fanfare:
     * C5 -> E5 -> G5 -> C6 arpeggio with golden resonance and harmonic sparkle!
     */
    fun playCelebration() {
        if (!isEnabled) return
        scope.launch {
            try {
                val note1 = generateTone(523.25, 90, 8.0, 0.6f)
                val note2 = generateTone(659.25, 90, 8.0, 0.65f)
                val note3 = generateTone(783.99, 100, 7.0, 0.7f)
                val note4 = generateTone(1046.50, 260, 5.0, 0.8f, addHarmonic = true)
                val totalSize = note1.size + note2.size + note3.size + note4.size
                val combined = ShortArray(totalSize)
                var offset = 0
                System.arraycopy(note1, 0, combined, offset, note1.size); offset += note1.size
                System.arraycopy(note2, 0, combined, offset, note2.size); offset += note2.size
                System.arraycopy(note3, 0, combined, offset, note3.size); offset += note3.size
                System.arraycopy(note4, 0, combined, offset, note4.size)
                playPcm(combined)
            } catch (e: Throwable) {
                Log.w(TAG, "Celebration fanfare error: ${e.message}")
            }
        }
    }

    private fun generateTone(
        frequency: Double,
        durationMs: Int,
        decayRate: Double,
        volume: Float,
        addHarmonic: Boolean = false
    ): ShortArray {
        val numSamples = (SAMPLE_RATE * durationMs) / 1000
        val samples = ShortArray(numSamples)
        val maxAmp = (Short.MAX_VALUE * volume).toInt()
        for (i in 0 until numSamples) {
            val t = i.toDouble() / SAMPLE_RATE
            val envelope = exp(-decayRate * t)
            var sampleVal = sin(2.0 * PI * frequency * t)
            if (addHarmonic) {
                sampleVal = 0.7 * sampleVal + 0.3 * sin(4.0 * PI * frequency * t)
            }
            val pcm = (sampleVal * envelope * maxAmp).toInt().coerceIn(Short.MIN_VALUE.toInt(), Short.MAX_VALUE.toInt())
            samples[i] = pcm.toShort()
        }
        return samples
    }

    private fun playPcm(pcm: ShortArray) {
        if (pcm.isEmpty()) return
        val bufferSize = pcm.size * 2
        var track: AudioTrack? = null
        try {
            val audioAttributes = AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_GAME)
                .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                .build()

            val audioFormat = AudioFormat.Builder()
                .setEncoding(AudioFormat.ENCODING_PCM_16BIT)
                .setSampleRate(SAMPLE_RATE)
                .setChannelMask(AudioFormat.CHANNEL_OUT_MONO)
                .build()

            track = AudioTrack.Builder()
                .setAudioAttributes(audioAttributes)
                .setAudioFormat(audioFormat)
                .setBufferSizeInBytes(bufferSize)
                .setTransferMode(AudioTrack.MODE_STATIC)
                .build()

            track.write(pcm, 0, pcm.size)
            track.play()
            val durationMs = (pcm.size * 1000L) / SAMPLE_RATE + 40L
            Thread.sleep(durationMs)
        } catch (_: Throwable) {
            // Silently fallback if audio track is unavailable
        } finally {
            try {
                track?.stop()
                track?.release()
            } catch (_: Throwable) {}
        }
    }
}
