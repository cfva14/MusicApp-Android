package io.github.cfva14.musicapp.home

import io.github.cfva14.musicapp.BasePresenter
import io.github.cfva14.musicapp.BaseView

/**
 * Created by Carlos Vlencia on 12/10/17.
 */

interface HomeContract {

    interface View : BaseView<Presenter> {

        fun setLoadingIndicator(active: Boolean)

    }

    interface Presenter : BasePresenter

}