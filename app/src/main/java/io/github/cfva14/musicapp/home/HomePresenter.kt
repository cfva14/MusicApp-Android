package io.github.cfva14.musicapp.home

import io.github.cfva14.musicapp.data.Artist
import io.github.cfva14.musicapp.data.source.HomeDataSource
import io.github.cfva14.musicapp.data.source.HomeRepository

/**
 * Created by Carlos Valencia on 12/10/17.
 */

class HomePresenter(

        private val homeRepository: HomeRepository,
        private val homeView: HomeContract.View

) : HomeContract.Presenter {

    init {
        homeView.presenter = this
    }

    override fun start() {
        homeRepository.getNearArtists(object : HomeDataSource.GetNearArtistsLoad {
            override fun onArtistsLoaded(artists: List<Artist>) {
                with(homeView) {
                    showNearArtists(artists)
                }
            }

            override fun onDataNotAvailable() {
                with(homeView) {
                    showMissingNearArtists()
                }
            }
        })
    }

    override fun openArtistUI(requestedArtist: Artist) {
        homeView.showArtistUI(requestedArtist.id)
    }
}