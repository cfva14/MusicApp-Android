package io.github.cfva14.musicapp.home

import io.github.cfva14.musicapp.BasePresenter
import io.github.cfva14.musicapp.BaseView
import io.github.cfva14.musicapp.data.Artist

/**
 * Created by Carlos Valencia on 12/10/17.
 */

interface HomeContract {

    interface View : BaseView<Presenter> {

        fun setLoadingIndicator(active: Boolean)
        fun showArtistUI(artistId: String)

    }

    interface Presenter : BasePresenter {

        fun openArtistUI(requestedArtist: Artist)

    }

}