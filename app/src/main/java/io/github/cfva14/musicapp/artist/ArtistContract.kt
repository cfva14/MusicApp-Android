package io.github.cfva14.musicapp.artist

import io.github.cfva14.musicapp.BasePresenter
import io.github.cfva14.musicapp.BaseView
import io.github.cfva14.musicapp.data.Album
import io.github.cfva14.musicapp.data.Artist
import io.github.cfva14.musicapp.data.Track

/**
 * Created by Carlos Valencia on 12/10/17.
 */

interface ArtistContract {

    interface View : BaseView<Presenter> {

        fun setLoadingIndicator(active: Boolean)
        fun showArtist(artist: Artist)
        fun showAlbums(albums: List<Album>)
        fun showTracks(tracks: List<Track>)
        fun showAlbumUI(album: Album)
        fun showMissingArtist()
        fun showMissingAlbums()
        fun showMissingTracks()

    }

    interface Presenter : BasePresenter {
        fun openAlbumUI(requestedAlbum: Album)
    }

}