package io.github.cfva14.musicapp.data.source

import io.github.cfva14.musicapp.data.Playlist
import io.github.cfva14.musicapp.data.Track

/**
 * Created by Carlos Valencia on 12/14/17.
 */

interface PlaylistDataSource {

    interface GetPlaylistCallback {
        fun onPlaylistLoaded(playlist: Playlist)
        fun onDataNotAvailable()
    }

    interface GetPlaylistsByUserCallBack {
        fun onPlaylistsLoaded(playlists: List<Playlist>)
        fun onDataNotAvailable()
    }

    interface GetSaveTrackToPlaylistCallback {
        fun onTrackSaved(message: String)
        fun onError(message: String)
    }

    fun getPlaylist(playlistId: String, callback: GetPlaylistCallback)
    fun getPlaylistsByUser(userId: String, callback: GetPlaylistsByUserCallBack)
    fun saveTrackToPlaylist(track: Track, playlistId: String, callback: GetSaveTrackToPlaylistCallback)

}