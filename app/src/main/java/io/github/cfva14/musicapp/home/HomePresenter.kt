package io.github.cfva14.musicapp.home

import io.github.cfva14.musicapp.data.Artist

/**
 * Created by Carlos Valencia on 12/10/17.
 */

class HomePresenter(

        private val homeView: HomeContract.View

) : HomeContract.Presenter {

    init {
        homeView.presenter = this
    }

    override fun start() {

    }

    override fun openArtistUI(requestedArtist: Artist) {
        homeView.showArtistUI("-KvmdhXYBaYk3RCvXRtZ")
    }
}