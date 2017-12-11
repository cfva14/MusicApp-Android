package io.github.cfva14.musicapp.album

import io.github.cfva14.musicapp.data.Album
import io.github.cfva14.musicapp.data.source.AlbumDataSource
import io.github.cfva14.musicapp.data.source.AlbumRepository

/**
 * Created by Carlos Valencia on 12/11/17.
 */

class AlbumPresenter(

        private val albumId: String,
        private val albumRepository: AlbumRepository,
        private val albumView: AlbumContract.View

) : AlbumContract.Presenter {

    init {
        albumView.presenter = this
    }

    override fun start() {
        openAlbum()
    }

    private fun openAlbum() {
        if (albumId.isEmpty()) {
            albumView.showMissingAlbum()
            return
        }

        albumView.setLoadingIndicator(true)
        albumRepository.getAlbum(albumId, object : AlbumDataSource.GetAlbumCallback {
            override fun onAlbumLoaded(album: Album) {
                with(albumView) {
                    setLoadingIndicator(false)
                    showTitle(album.name)
                }
            }

            override fun onDataNotAvailable() {
                with(albumView) {
                    showMissingAlbum()
                }
            }
        })
    }
}