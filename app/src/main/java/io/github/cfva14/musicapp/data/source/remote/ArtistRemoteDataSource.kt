package io.github.cfva14.musicapp.data.source.remote

import android.util.Log
import com.google.firebase.database.*
import io.github.cfva14.musicapp.data.Artist
import io.github.cfva14.musicapp.data.source.ArtistDataSource
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.ValueEventListener
import io.github.cfva14.musicapp.data.Album
import io.github.cfva14.musicapp.data.Track


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
        val albumRef = databaseRef.child("album").orderByChild("artistId").equalTo(artistId)

        val albumListener = object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError?) {
                callback.onDataNotAvailable()
            }

            override fun onDataChange(p0: DataSnapshot?) {
                val albums: MutableList<Album> = ArrayList()
                p0?.children?.forEach {
                    albums.add(it.getValue(Album::class.java)!!)
                }
                callback.onAlbumsLoaded(albums)
            }
        }

        albumRef.addListenerForSingleValueEvent(albumListener)
    }

    override fun getTracks(artistId: String, callback: ArtistDataSource.GetTracksCallback) {
        val trackRef = databaseRef.child("track").orderByChild("artistId").equalTo(artistId).limitToFirst(5)

        val trackListener = object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError?) {
                callback.onDataNotAvailable()
            }

            override fun onDataChange(p0: DataSnapshot?) {
                val tracks: MutableList<Track> = ArrayList()
                p0?.children?.forEach {
                    Log.e("REMOTE", it.getValue(Track::class.java)!!.title)
                    tracks.add(it.getValue(Track::class.java)!!)
                }
                callback.onTracksLoaded(tracks)
            }
        }

        trackRef.addListenerForSingleValueEvent(trackListener)
        Log.e("REMOTE", trackRef.ref.toString())
    }
}