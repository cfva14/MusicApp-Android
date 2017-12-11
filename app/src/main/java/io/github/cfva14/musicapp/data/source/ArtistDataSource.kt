package io.github.cfva14.musicapp.data.source

import io.github.cfva14.musicapp.data.Artist

/**
 * Created by Carlos Valencia on 12/10/17.
 */

interface ArtistDataSource {

    interface GetArtistCallback {
        fun onArtistLoaded(artist: Artist)
        fun onDataNotAvailable()
    }

    fun getArtist(artistId: String, callback: GetArtistCallback)


}