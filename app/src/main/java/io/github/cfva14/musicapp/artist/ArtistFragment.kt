package io.github.cfva14.musicapp.artist

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.*
import android.widget.*
import io.github.cfva14.musicapp.R
import io.github.cfva14.musicapp.album.AlbumActivity
import io.github.cfva14.musicapp.data.Album
import io.github.cfva14.musicapp.data.Artist
import io.github.cfva14.musicapp.data.Track
import io.github.cfva14.musicapp.utils.GenUtils
import io.github.cfva14.musicapp.utils.GlideApp

/**
 * Created by Carlos Valencia on 12/10/17.
 */

class ArtistFragment : Fragment(), ArtistContract.View, PopupMenu.OnMenuItemClickListener {

    private lateinit var artistImage: ImageView
    private lateinit var recyclerAlbum: RecyclerView
    private lateinit var albumAdapter: AlbumAdapter
    private lateinit var horizontalLayoutManager: RecyclerView.LayoutManager
    private lateinit var trackHolder: LinearLayout

    private lateinit var selectedTrack: Track

    override lateinit var presenter: ArtistContract.Presenter

    private var albumListener: AlbumItemListener = object : AlbumItemListener {
        override fun onAlbumClick(clickedAlbum: Album) {
            presenter.openAlbumUI(clickedAlbum)
        }
    }

    private var trackListener: TrackItemListener = object : TrackItemListener {
        override fun onTrackClick(clickedTrack: Track) {
            Toast.makeText(context, clickedTrack.title, Toast.LENGTH_SHORT).show()
        }

        override fun onMoreClick(clickedTrack: Track, view: View) {
            selectedTrack = clickedTrack
            val popUpMenu: PopupMenu

            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) popUpMenu = PopupMenu(context, view, Gravity.END) else popUpMenu = PopupMenu(context, view)

            popUpMenu.setOnMenuItemClickListener(this@ArtistFragment)
            val inflater = popUpMenu.menuInflater
            inflater.inflate(R.menu.popup_menu_track, popUpMenu.menu)
            popUpMenu.show()
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
            trackHolder = findViewById(R.id.track_holder)
        }
        horizontalLayoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        recyclerAlbum.layoutManager = horizontalLayoutManager
        recyclerAlbum.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.HORIZONTAL))

        return root
    }

    override fun setLoadingIndicator(active: Boolean) {

    }

    override fun showArtist(artist: Artist) {
        GlideApp.with(context).load(artist.imageUrl).into(artistImage)
    }

    override fun showAlbums(albums: List<Album>) {
        albumAdapter = AlbumAdapter(context!!, albums, albumListener)
        recyclerAlbum.adapter = albumAdapter
    }

    override fun showTracks(tracks: List<Track>) {
        trackHolder.removeAllViews()

        for (track in tracks) {
            val trackLayout = layoutInflater.inflate(R.layout.list_item_track, trackHolder, false)

            GlideApp.with(context).load(track.albumImageUrl).into(trackLayout.findViewById<ImageView>(R.id.item_track_image))
            trackLayout.findViewById<TextView>(R.id.item_track_album).text = track.albumName
            trackLayout.findViewById<TextView>(R.id.item_track_title).text = track.title
            trackLayout.findViewById<TextView>(R.id.item_track_duration).text = GenUtils.formatTimer(track.duration)
            trackLayout.findViewById<ImageView>(R.id.item_track_more).setOnClickListener { trackListener.onMoreClick(track, trackLayout) }
            trackLayout.setOnClickListener { trackListener.onTrackClick(track) }

            trackHolder.addView(trackLayout)
        }
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
            GlideApp.with(context).load(albums[position].imageUrl).into(holder?.albumImage)
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

    override fun onMenuItemClick(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.context_track_play -> {
                Toast.makeText(context, "Play ${selectedTrack.title}", Toast.LENGTH_SHORT).show()
                return true
            }
            R.id.context_track_play_next -> {
                Toast.makeText(context, "Play ${selectedTrack.title} after current playing song", Toast.LENGTH_SHORT).show()
                return true
            }
            R.id.context_track_add_to_playlist -> {
                Toast.makeText(context, "Add ${selectedTrack.title} to the playlist", Toast.LENGTH_SHORT).show()
                return true
            }
        }
        return false
    }

    interface AlbumItemListener {
        fun onAlbumClick(clickedAlbum: Album)
    }

    interface TrackItemListener {
        fun onTrackClick(clickedTrack: Track)
        fun onMoreClick(clickedTrack: Track, view: View)
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
