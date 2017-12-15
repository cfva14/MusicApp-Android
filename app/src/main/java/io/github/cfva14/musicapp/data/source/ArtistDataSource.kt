package io.github.cfva14.musicapp.data.source

import io.github.cfva14.musicapp.data.Album
import io.github.cfva14.musicapp.data.Artist
import io.github.cfva14.musicapp.data.Track

/**
 * Created by Carlos Valencia on 12/10/17.
 */

interface ArtistDataSource {

    interface GetArtistCallback {
        fun onArtistLoaded(artist: Artist)
        fun onDataNotAvailable()
    }

    interface GetAlbumsCallback {
        fun onAlbumsLoaded(albums: List<Album>)
        fun onDataNotAvailable()
    }

    interface GetTracksCallback {
        fun onTracksLoaded(tracks: List<Track>)
        fun onDataNotAvailable()
    }

    fun getArtist(artistId: String, callback: GetArtistCallback)
    fun getAlbums(artistId: String, callback: GetAlbumsCallback)
    fun getTracks(artistId: String, callback: GetTracksCallback)

}