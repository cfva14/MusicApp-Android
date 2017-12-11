package io.github.cfva14.musicapp.utils

import io.github.cfva14.musicapp.data.source.ArtistRepository
import io.github.cfva14.musicapp.data.source.remote.ArtistRemoteDataSource

/**
 * Created by Carlos Vlencia on 12/10/17.
 */

object Injection {

    fun provideArtistRepository() : ArtistRepository {
        return ArtistRepository.getInstance(ArtistRemoteDataSource)
    }

}