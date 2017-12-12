package io.github.cfva14.musicapp.artist

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.squareup.picasso.Picasso
import io.github.cfva14.musicapp.R
import io.github.cfva14.musicapp.album.AlbumActivity
import io.github.cfva14.musicapp.data.Album
import io.github.cfva14.musicapp.data.Artist
import io.github.cfva14.musicapp.data.Track
import io.github.cfva14.musicapp.utils.GenUtils

/**
 * Created by Carlos Valencia on 12/10/17.
 */
class ArtistFragment : Fragment(), ArtistContract.View {

    private lateinit var artistImage: ImageView
    private lateinit var recyclerAlbum: RecyclerView
    private lateinit var recyclerTrack: RecyclerView
    private lateinit var albumAdapter: AlbumAdapter
    private lateinit var trackAdapter: TrackAdapter
    private lateinit var horizontalLayoutManager: RecyclerView.LayoutManager
    private lateinit var linearLayoutManager: RecyclerView.LayoutManager

    override lateinit var presenter: ArtistContract.Presenter
    private val parent: ArtistActivity = ArtistActivity()

    private var albumListener: AlbumItemListener = object : AlbumItemListener {
        override fun onAlbumClick(clickedAlbum: Album) {
            presenter.openAlbumUI(clickedAlbum)
        }
    }

    private var trackListener: TrackItemListener = object : TrackItemListener {
        override fun onTrackClick(clickedTrack: Track) {
            Toast.makeText(context, clickedTrack.title, Toast.LENGTH_SHORT).show()
        }
    }

    override fun onResume() {
        super.onResume()
        presenter.start()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.fragment_artist, container, false)
        setHasOptionsMenu(true)
        with(root) {
            artistImage = findViewById(R.id.artist_image)
            recyclerAlbum = findViewById(R.id.recycler_album)
            recyclerTrack = findViewById(R.id.recycler_track)
        }
        horizontalLayoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        linearLayoutManager = LinearLayoutManager(context)
        recyclerAlbum.layoutManager = horizontalLayoutManager
        recyclerAlbum.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.HORIZONTAL))
        recyclerTrack.layoutManager = linearLayoutManager
        recyclerTrack.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))

        return root
    }

    override fun setLoadingIndicator(active: Boolean) {

    }

    override fun showArtist(artist: Artist) {
        Picasso.with(context).load(artist.imageUrl).into(artistImage)
    }

    override fun showAlbums(albums: List<Album>) {
        albumAdapter = AlbumAdapter(context!!, albums, albumListener)
        recyclerAlbum.adapter = albumAdapter
    }

    override fun showTracks(tracks: List<Track>) {
        trackAdapter = TrackAdapter(context!!, tracks, trackListener)
        recyclerTrack.adapter = trackAdapter
    }

    override fun showAlbumUI(album: Album) {
        val intent = Intent(context, AlbumActivity::class.java).apply {
            putExtra(AlbumActivity.EXTRA_ALBUM_ID, album.id)
        }
        startActivity(intent)
    }

    override fun showMissingArtist() {
        Toast.makeText(context, "No Artist Found", Toast.LENGTH_SHORT).show()
    }

    override fun showMissingAlbums() {
        Toast.makeText(context, "No Albums Found", Toast.LENGTH_SHORT).show()
    }

    override fun showMissingTracks() {
        Toast.makeText(context, "No Tracks Found", Toast.LENGTH_SHORT).show()
    }

    private class AlbumAdapter(private val context: Context, private val albums: List<Album>, private val itemListener: AlbumItemListener) : RecyclerView.Adapter<AlbumAdapter.AlbumHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): AlbumHolder {
            val listItem = LayoutInflater.from(parent?.context).inflate(R.layout.list_item_album, parent, false)
            return AlbumHolder(listItem)
        }

        override fun onBindViewHolder(holder: AlbumHolder?, position: Int) {
            Picasso.with(context).load(albums[position].imageUrl).into(holder?.albumImage)
            holder?.albumName?.text = albums[position].name
            holder?.itemView?.setOnClickListener {
                itemListener.onAlbumClick(albums[position])
            }
        }

        override fun getItemCount() = albums.size

        class AlbumHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val albumImage: ImageView = itemView.findViewById(R.id.item_album_image)
            val albumName: TextView = itemView.findViewById(R.id.item_album_name)

        }

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

    interface AlbumItemListener {
        fun onAlbumClick(clickedAlbum: Album)
    }

    interface TrackItemListener {
        fun onTrackClick(clickedTrack: Track)
    }

    companion object {
        private val ARGUMENT_ARTIST_ID = "ARTIST_ID"
        fun newInstance(artistId: String?) = ArtistFragment().apply {
            arguments = Bundle().apply {
                putString(ARGUMENT_ARTIST_ID, artistId)
            }
        }
    }
}
