package io.github.cfva14.musicapp.artist

import io.github.cfva14.musicapp.data.Artist
import io.github.cfva14.musicapp.data.source.ArtistDataSource
import io.github.cfva14.musicapp.data.source.ArtistRepository

/**
 * Created by Carlos Valencia on 12/10/17.
 */
class ArtistPresenter(

        private val artistId: String,
        private val artistRepository: ArtistRepository,
        private val artistView: ArtistContract.View

) : ArtistContract.Presenter {

    init {
        artistView.presenter = this
    }

    override fun start() {
        openArtist()
    }

    private fun openArtist() {
        if (artistId.isEmpty()) {
            artistView.showMissingArtist()
            return
        }

        artistView.setLoadingIndicator(true)
        artistRepository.getArtist(artistId, object : ArtistDataSource.GetArtistCallback {
            override fun onArtistLoaded(artist: Artist) {
                with(artistView) {
                    setLoadingIndicator(false)
                    showTitle(artist.name)
                }
            }

            override fun onDataNotAvailable() {
                with(artistView) {
                    showMissingArtist()
                }
            }
        })
    }
}