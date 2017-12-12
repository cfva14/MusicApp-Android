package io.github.cfva14.musicapp.album

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.squareup.picasso.Picasso
import io.github.cfva14.musicapp.R
import io.github.cfva14.musicapp.data.Album
import io.github.cfva14.musicapp.data.Track
import io.github.cfva14.musicapp.utils.GenUtils

/**
 * Created by Carlos Valencia on 12/11/17.
 */

class AlbumFragment : Fragment(), AlbumContract.View {

    private lateinit var albumImage: ImageView
    private lateinit var albumName: TextView
    private lateinit var artistName: TextView

    private lateinit var recyclerTrack: RecyclerView
    private lateinit var trackAdapter: AlbumFragment.TrackAdapter
    private lateinit var linearLayoutManager: RecyclerView.LayoutManager

    override lateinit var presenter: AlbumContract.Presenter
    private val parent: AlbumActivity = AlbumActivity()

    private var trackListener: TrackItemListener = object : TrackItemListener {
        override fun onTrackClick(clickedTrack: Track) {
            //presenter.openAlbumUI(clickedAlbum)
        }
    }

    override fun onResume() {
        super.onResume()
        presenter.start()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.fragment_album, container, false)
        setHasOptionsMenu(true)
        with(root) {
            albumImage = findViewById(R.id.album_image)
            albumName = findViewById(R.id.album_name)
            artistName = findViewById(R.id.album_artist_name)
            recyclerTrack = findViewById(R.id.recycler_track)
        }
        linearLayoutManager = LinearLayoutManager(context)
        recyclerTrack.layoutManager = linearLayoutManager
        recyclerTrack.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))

        return root
    }

    override fun setLoadingIndicator(active: Boolean) {
        Log.e("ALBUM_FRAGMENT", active.toString())
    }

    override fun showAlbum(album: Album) {
        Picasso.with(context).load(album.imageUrl).into(albumImage)
        albumName.text = album.name
        artistName.text = album.artistName
    }

    override fun showTracks(tracks: List<Track>) {
        trackAdapter = TrackAdapter(context!!, tracks, trackListener)
        recyclerTrack.adapter = trackAdapter
    }

    override fun showMissingAlbum() {
        Toast.makeText(context, "No Album Found", Toast.LENGTH_SHORT).show()
    }

    override fun showMissingTracks() {
        Toast.makeText(context, "No Tracks Found", Toast.LENGTH_SHORT).show()
    }

    private class TrackAdapter(private val context: Context, private val tracks: List<Track>, private val itemListener: TrackItemListener) : RecyclerView.Adapter<TrackAdapter.TrackHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): TrackHolder {
            val listItem = LayoutInflater.from(parent?.context).inflate(R.layout.list_item_track, parent, false)
            return TrackHolder(listItem)
        }

        override fun onBindViewHolder(holder: TrackHolder?, position: Int) {
            Picasso.with(context).load(tracks[position].albumImageUrl).into(holder?.albumImage)
            holder?.albumName?.text = tracks[position].albumName
            holder?.number?.text = (position + 1).toString()
            holder?.title?.text = tracks[position].title
            holder?.duration?.text = GenUtils.formatTimer(tracks[position].duration)
            holder?.itemView?.setOnClickListener {
                itemListener.onTrackClick(tracks[position])
            }
        }

        override fun getItemCount() = tracks.size

        class TrackHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val albumImage: ImageView = itemView.findViewById(R.id.item_track_image)
            val number: TextView = itemView.findViewById(R.id.item_track_number)
            val title: TextView = itemView.findViewById(R.id.item_track_title)
            val albumName: TextView = itemView.findViewById(R.id.item_track_album)
            val duration: TextView = itemView.findViewById(R.id.item_track_duration)
        }

    }

    interface TrackItemListener {
        fun onTrackClick(clickedTrack: Track)
    }

    companion object {
        private val ARGUMENT_ALBUM_ID = "ALBUM_ID"
        fun newInstance(albumId: String?) = AlbumFragment().apply {
            arguments = Bundle().apply {
                putString(ARGUMENT_ALBUM_ID, albumId)
            }
        }
    }

}