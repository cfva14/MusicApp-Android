package io.github.cfva14.musicapp.data.source

import io.github.cfva14.musicapp.data.Artist

/**
 * Created by Carlos Valencia on 12/14/17.
 */

interface HomeDataSource {

    interface GetNearArtistsLoad {
        fun onArtistsLoaded(artists: List<Artist>)
        fun onDataNotAvailable()
    }

    fun getNearArtists(callback: GetNearArtistsLoad)

}