package io.github.cfva14.musicapp.data.source

import io.github.cfva14.musicapp.data.Artist

/**
 * Created by Carlos Valencia on 12/10/17.
 */

class ArtistRepository(
        private val artistRemoteDataSource: ArtistDataSource
) : ArtistDataSource {

    override fun getArtist(artistId: String, callback: ArtistDataSource.GetArtistCallback) {
        artistRemoteDataSource.getArtist(artistId, object : ArtistDataSource.GetArtistCallback {
            override fun onArtistLoaded(artist: Artist) {
                callback.onArtistLoaded(artist)
            }

            override fun onDataNotAvailable() {
                callback.onDataNotAvailable()
            }
        })
    }

    companion object {
        private var INSTANCE: ArtistRepository? = null
        @JvmStatic fun getInstance(artistRemoteDataSource: ArtistDataSource) : ArtistRepository {
            return INSTANCE ?: ArtistRepository(artistRemoteDataSource).apply {
                INSTANCE = this
            }
        }
    }
}