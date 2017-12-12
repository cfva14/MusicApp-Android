package io.github.cfva14.musicapp.data.source.remote

import android.util.Log
import com.google.firebase.database.*
import io.github.cfva14.musicapp.data.Artist
import io.github.cfva14.musicapp.data.source.ArtistDataSource
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.ValueEventListener
import io.github.cfva14.musicapp.data.Album


/**
 * Created by Carlos Valencia on 12/10/17.
 */

object ArtistRemoteDataSource : ArtistDataSource {

    private val databaseRef = FirebaseDatabase.getInstance().reference

    override fun getArtist(artistId: String, callback: ArtistDataSource.GetArtistCallback) {

        val artistRef = databaseRef.child("artist/" + artistId)

        val artistListener = object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError?) {
                callback.onDataNotAvailable()
            }

            override fun onDataChange(p0: DataSnapshot?) {
                val artist = p0?.getValue<Artist>(Artist::class.java)
                callback.onArtistLoaded(artist!!)
            }
        }

        artistRef.addListenerForSingleValueEvent(artistListener)
    }

    override fun getAlbums(artistId: String, callback: ArtistDataSource.GetAlbumsCallback) {

        val albumRef = databaseRef.child("album")
        Log.e("AL", albumRef.toString())

        val albumListener = object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError?) {
                Log.e("AL", "NO")
                callback.onDataNotAvailable()
            }

            override fun onDataChange(p0: DataSnapshot?) {
                val albums: MutableList<Album> = ArrayList()
                p0?.children?.forEach {
                    Log.e("AL", it.toString())
                    albums.add(it.getValue(Album::class.java)!!)
                }

                callback.onAlbumsLoaded(albums)
            }
        }

        albumRef.addListenerForSingleValueEvent(albumListener)
    }
}