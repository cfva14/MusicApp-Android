package io.github.cfva14.musicapp.data.source

import io.github.cfva14.musicapp.data.Album

/**
 * Created by Carlos Valencia on 12/11/17.
 */

interface AlbumDataSource {

    interface GetAlbumCallback {
        fun onAlbumLoaded(album: Album)
        fun onDataNotAvailable()
    }

    fun getAlbum(albumId: String, callback: GetAlbumCallback)

}