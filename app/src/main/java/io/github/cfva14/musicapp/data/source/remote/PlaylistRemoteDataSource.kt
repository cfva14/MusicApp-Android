package io.github.cfva14.musicapp.data.source.remote

import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import io.github.cfva14.musicapp.data.Playlist
import io.github.cfva14.musicapp.data.Track
import io.github.cfva14.musicapp.data.source.PlaylistDataSource

/**
 * Created by Carlos Valencia on 12/14/17.
 */

object PlaylistRemoteDataSource : PlaylistDataSource {

    private val databaseRef = FirebaseDatabase.getInstance().reference

    override fun getPlaylist(playlistId: String, callback: PlaylistDataSource.GetPlaylistCallback) {
        val playlistRef = databaseRef.child("playlist/" + playlistId)

        val playlistListener = object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError?) {
                callback.onDataNotAvailable()
            }

            override fun onDataChange(p0: DataSnapshot?) {
                val playlist = p0?.getValue<Playlist>(Playlist::class.java)
                callback.onPlaylistLoaded(playlist!!)
            }
        }

        playlistRef.addListenerForSingleValueEvent(playlistListener)
    }

    override fun getPlaylistsByUser(userId: String, callback: PlaylistDataSource.GetPlaylistsByUserCallBack) {
        val playlistRef = databaseRef.child("playlist").orderByChild("userId").equalTo(userId)

        val playlistListener = object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError?) {
                callback.onDataNotAvailable()
            }

            override fun onDataChange(p0: DataSnapshot?) {
                val playlists: MutableList<Playlist> = ArrayList()
                p0?.children?.forEach {
                    playlists.add(it.getValue(Playlist::class.java)!!)
                }
                callback.onPlaylistsLoaded(playlists)
            }
        }

        playlistRef.addListenerForSingleValueEvent(playlistListener)
    }

    override fun saveTrackToPlaylist(track: Track, playlistId: String, callback: PlaylistDataSource.GetSaveTrackToPlaylistCallback) {
        val playlistRef = databaseRef.child("playlist-track/" + playlistId)
        val trackPushId = playlistRef.push().key
        playlistRef.child(trackPushId).setValue(track)

        val playlistListener = object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError?) {
                callback.onError(p0?.message.toString())
            }

            override fun onDataChange(p0: DataSnapshot?) {
                if (p0?.value != null) callback.onTrackSaved("Saved Successfully") else callback.onError("There was an error, please try again later")
            }
        }

        playlistRef.child(trackPushId).addListenerForSingleValueEvent(playlistListener)
    }
}