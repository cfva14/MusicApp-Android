package io.github.cfva14.musicapp.artist

import io.github.cfva14.musicapp.BasePresenter
import io.github.cfva14.musicapp.BaseView

/**
 * Created by Carlos Valencia on 12/10/17.
 */

interface ArtistContract {

    interface View : BaseView<Presenter> {

        fun setLoadingIndicator(active: Boolean)
        fun showMissingArtist()
        fun showTitle(title: String)

    }

    interface Presenter : BasePresenter

}