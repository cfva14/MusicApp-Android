package io.github.cfva14.musicapp.data

/**
 * Created by Carlos Valencia on 12/11/17.
 */

data class Track(
        val id: String = "",
        val title: String = "",
        val albumImageUrl: String = "",
        val albumName: String = "",
        val duration: Double = 0.0
)