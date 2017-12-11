package io.github.cfva14.musicapp.data.source.remote

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import io.github.cfva14.musicapp.data.Album
import io.github.cfva14.musicapp.data.source.AlbumDataSource

/**
 * Created by Carlos Valencia on 12/11/17.
 */

object AlbumRemoteDataSource : AlbumDataSource {

    private val databaseRef = FirebaseDatabase.getInstance().reference

    override fun getAlbum(albumId: String, callback: AlbumDataSource.GetAlbumCallback) {

        val albumRef = databaseRef.child("album/" + albumId)

        val albumListener = object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError?) {
                callback.onDataNotAvailable()
            }

            override fun onDataChange(p0: DataSnapshot?) {
                val album = p0?.getValue<Album>(Album::class.java)
                callback.onAlbumLoaded(album!!)
            }
        }

        albumRef.addListenerForSingleValueEvent(albumListener)
    }
}