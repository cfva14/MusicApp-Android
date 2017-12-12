package io.github.cfva14.musicapp.album

import io.github.cfva14.musicapp.BasePresenter
import io.github.cfva14.musicapp.BaseView
import io.github.cfva14.musicapp.data.Album
import io.github.cfva14.musicapp.data.Track

/**
 * Created by Carlos Valencia on 12/11/17.
 */

interface AlbumContract {

    interface View : BaseView<Presenter> {

        fun setLoadingIndicator(active: Boolean)
        fun showAlbum(album: Album)
        fun showTracks(tracks: List<Track>)
        fun showMissingAlbum()
        fun showMissingTracks()

    }

    interface Presenter : BasePresenter

}