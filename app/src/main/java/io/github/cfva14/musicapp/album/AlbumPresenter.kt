package io.github.cfva14.musicapp.album

import io.github.cfva14.musicapp.data.Album
import io.github.cfva14.musicapp.data.Playlist
import io.github.cfva14.musicapp.data.Track
import io.github.cfva14.musicapp.data.source.AlbumDataSource
import io.github.cfva14.musicapp.data.source.AlbumRepository
import io.github.cfva14.musicapp.data.source.PlaylistDataSource
import io.github.cfva14.musicapp.data.source.PlaylistRepository

/**
 * Created by Carlos Valencia on 12/11/17.
 */

class AlbumPresenter(

        private val albumId: String,
        private val albumRepository: AlbumRepository,
        private val playlistRepository: PlaylistRepository,
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
                    showAlbum(album)
                }
            }

            override fun onDataNotAvailable() {
                with(albumView) {
                    showMissingAlbum()
                }
            }
        })

        albumRepository.getTracks(albumId, object : AlbumDataSource.GetTracksCallback {
            override fun onTracksLoaded(tracks: List<Track>) {
                with(albumView) {
                    showTracks(tracks)
                }
            }

            override fun onDataNotAvailable() {
                with(albumView) {
                    showMissingTracks()
                }
            }
        })
    }

    override fun getPlaylistsByUser(userId: String) {
        playlistRepository.getPlaylistsByUser(userId, object : PlaylistDataSource.GetPlaylistsByUserCallBack {
            override fun onPlaylistsLoaded(playlists: List<Playlist>) {
                with(albumView) {
                    showPlaylistsByUser(playlists)
                }
            }

            override fun onDataNotAvailable() {}
        })
    }

    override fun saveTrackToPlaylist(track: Track, playlistId: String) {
        playlistRepository.saveTrackToPlaylist(track, playlistId, object : PlaylistDataSource.GetResultCallback {
            override fun onResult(message: String) {
                with(albumView) {
                    showResultMessage(message)
                }
            }
        })
    }

    override fun createPlaylist(userId: String, name: String, isPrivate: Boolean, track: Track?) {
        playlistRepository.createPlaylist(userId, name, isPrivate, track, object : PlaylistDataSource.GetResultCallback {
            override fun onResult(message: String) {
                with(albumView) {
                    showResultMessage(message)
                }
            }
        })
    }
}