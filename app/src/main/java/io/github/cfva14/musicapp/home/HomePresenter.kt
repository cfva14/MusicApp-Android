package io.github.cfva14.musicapp.home

import android.util.Log

/**
 * Created by Carlos Vlencia on 12/10/17.
 */

class HomePresenter(

        private val homeView: HomeContract.View

) : HomeContract.Presenter {

    init {
        homeView.presenter = this
    }

    override fun start() {
        Log.e("HOME_PRESENTER", "start()")
    }
}