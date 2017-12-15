package io.github.cfva14.musicapp.data.source

import io.github.cfva14.musicapp.data.Artist
import io.github.cfva14.musicapp.data.source.remote.HomeRemoteDataSource

/**
 * Created by Carlos Valencia on 12/14/17.
 */

class HomeRepository(
        private val homeRemoteDataSource: HomeDataSource
) : HomeDataSource {

    override fun getNearArtists(callback: HomeDataSource.GetNearArtistsLoad) {
        homeRemoteDataSource.getNearArtists(object : HomeDataSource.GetNearArtistsLoad {
            override fun onArtistsLoaded(artists: List<Artist>) {
                callback.onArtistsLoaded(artists)
            }

            override fun onDataNotAvailable() {
                callback.onDataNotAvailable()
            }
        })
    }

    companion object {
        private var INSTANCE: HomeRepository? = null
        @JvmStatic fun getInstance(homeRemoteDataSource: HomeDataSource) : HomeRepository {
            return INSTANCE ?: HomeRepository(homeRemoteDataSource).apply {
                INSTANCE = this
            }
        }
    }
}