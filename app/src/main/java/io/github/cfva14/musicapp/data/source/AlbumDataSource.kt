package io.github.cfva14.musicapp.data.source

import io.github.cfva14.musicapp.data.Album
import io.github.cfva14.musicapp.data.Track

/**
 * Created by Carlos Valencia on 12/11/17.
 */

interface AlbumDataSource {

    interface GetAlbumCallback {
        fun onAlbumLoaded(album: Album)
        fun onDataNotAvailable()
    }

    interface GetTracksCallback {
        fun onTracksLoaded(tracks: List<Track>)
        fun onDataNotAvailable()
    }

    fun getAlbum(albumId: String, callback: GetAlbumCallback)
    fun getTracks(albumId: String, callback: GetTracksCallback)

}