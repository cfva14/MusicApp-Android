package io.github.cfva14.musicapp.data.source.remote

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import io.github.cfva14.musicapp.data.Artist
import io.github.cfva14.musicapp.data.source.HomeDataSource

/**
 * Created by Carlos Valencia on 12/14/17.
 */

object HomeRemoteDataSource : HomeDataSource {

    private val databaseRef = FirebaseDatabase.getInstance().reference

    override fun getNearArtists(callback: HomeDataSource.GetNearArtistsLoad) {
        val artistRef = HomeRemoteDataSource.databaseRef.child("artist").limitToFirst(5)

        val artistListener = object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError?) {
                callback.onDataNotAvailable()
            }

            override fun onDataChange(p0: DataSnapshot?) {
                val artists: MutableList<Artist> = ArrayList()
                p0?.children?.forEach {
                    artists.add(it.getValue(Artist::class.java)!!)
                }
                callback.onArtistsLoaded(artists)
            }
        }

        artistRef.addListenerForSingleValueEvent(artistListener)
    }

}