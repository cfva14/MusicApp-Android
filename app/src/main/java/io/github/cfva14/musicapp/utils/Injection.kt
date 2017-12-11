package io.github.cfva14.musicapp.utils

import io.github.cfva14.musicapp.data.source.AlbumRepository
import io.github.cfva14.musicapp.data.source.ArtistRepository
import io.github.cfva14.musicapp.data.source.remote.AlbumRemoteDataSource
import io.github.cfva14.musicapp.data.source.remote.ArtistRemoteDataSource

/**
 * Created by Carlos Valencia on 12/10/17.
 */

object Injection {

    fun provideArtistRepository() : ArtistRepository {
        return ArtistRepository.getInstance(ArtistRemoteDataSource)
    }

    fun provideAlbumRepository() : AlbumRepository {
        return AlbumRepository.getInstance(AlbumRemoteDataSource)
    }

}