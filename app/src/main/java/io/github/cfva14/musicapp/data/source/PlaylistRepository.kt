package io.github.cfva14.musicapp.data.source

import io.github.cfva14.musicapp.data.Playlist
import io.github.cfva14.musicapp.data.Track

/**
 * Created by Carlos Valencia on 12/14/17.
 */

class PlaylistRepository(
        private val playlistRemoteDataSource: PlaylistDataSource
) : PlaylistDataSource {

    override fun getPlaylist(playlistId: String, callback: PlaylistDataSource.GetPlaylistCallback) {
        playlistRemoteDataSource.getPlaylist(playlistId, object : PlaylistDataSource.GetPlaylistCallback {
            override fun onPlaylistLoaded(playlist: Playlist) {
                callback.onPlaylistLoaded(playlist)
            }

            override fun onDataNotAvailable() {
                callback.onDataNotAvailable()
            }
        })
    }

    override fun getPlaylistsByUser(userId: String, callback: PlaylistDataSource.GetPlaylistsByUserCallBack) {
        playlistRemoteDataSource.getPlaylistsByUser(userId, object : PlaylistDataSource.GetPlaylistsByUserCallBack {
            override fun onPlaylistsLoaded(playlists: List<Playlist>) {
                callback.onPlaylistsLoaded(playlists)
            }

            override fun onDataNotAvailable() {
                callback.onDataNotAvailable()
            }
        })
    }

    override fun saveTrackToPlaylist(track: Track, playlistId: String, callback: PlaylistDataSource.GetSaveTrackToPlaylistCallback) {
        playlistRemoteDataSource.saveTrackToPlaylist(track, playlistId, object : PlaylistDataSource.GetSaveTrackToPlaylistCallback {
            override fun onTrackSaved(message: String) {
                callback.onTrackSaved(message)
            }

            override fun onError(message: String) {
                callback.onError(message)
            }
        })
    }

    companion object {
        private var INSTANCE: PlaylistRepository? = null
        @JvmStatic fun getInstance(playlistRemoteDataSource: PlaylistDataSource) : PlaylistRepository {
            return INSTANCE ?: PlaylistRepository(playlistRemoteDataSource).apply {
                INSTANCE = this
            }
        }
    }

}