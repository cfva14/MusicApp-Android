package io.github.cfva14.musicapp.data.source

import io.github.cfva14.musicapp.data.Album

/**
 * Created by Carlos Valencia on 12/11/17.
 */

class AlbumRepository(
        private val albumRemoteDataSource: AlbumDataSource
) : AlbumDataSource {

    override fun getAlbum(albumId: String, callback: AlbumDataSource.GetAlbumCallback) {
        albumRemoteDataSource.getAlbum(albumId, object : AlbumDataSource.GetAlbumCallback {
            override fun onAlbumLoaded(album: Album) {
                callback.onAlbumLoaded(album)
            }

            override fun onDataNotAvailable() {
                callback.onDataNotAvailable()
            }
        })
    }

    companion object {
        private var INSTANCE: AlbumRepository? = null
        @JvmStatic fun getInstance(albumRemoteDataSource: AlbumDataSource) : AlbumRepository {
            return INSTANCE ?: AlbumRepository(albumRemoteDataSource).apply {
                INSTANCE = this
            }
        }
    }

}