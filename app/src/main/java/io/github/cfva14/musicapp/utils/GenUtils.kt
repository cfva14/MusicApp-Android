package io.github.cfva14.musicapp.utils

/**
 * Created by Carlos Valencia on 12/11/17.
 */
object GenUtils {

    fun formatTimer(time: Double) : String {

        val hours = time.toInt() / 3600
        val minutes = (time.toInt() % 3600) / 60
        val seconds = time.toInt() % 60

        if (hours > 9) return String.format("%02d:%02d:%02d", hours, minutes, seconds)
        if (hours > 0) return String.format("%02d:%02d:%02d", hours, minutes, seconds)
        if (minutes > 9) return String.format("%02d:%02d", minutes, seconds)
        if (minutes > 0) return String.format("%01d:%02d", minutes, seconds)
        if (seconds > 9) return String.format("%01d", seconds)
        return String.format("%02d", seconds)
    }

}