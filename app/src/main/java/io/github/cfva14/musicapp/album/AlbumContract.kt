package io.github.cfva14.musicapp.album

import io.github.cfva14.musicapp.BasePresenter
import io.github.cfva14.musicapp.BaseView

/**
 * Created by Carlos Valencia on 12/11/17.
 */

interface AlbumContract {

    interface View : BaseView<Presenter> {

        fun setLoadingIndicator(active: Boolean)
        fun showMissingAlbum()
        fun showTitle(title: String)

    }

    interface Presenter : BasePresenter

}