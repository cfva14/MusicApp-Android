package io.github.cfva14.musicapp.album

import android.content.Context
import android.content.DialogInterface
import android.os.Build
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AlertDialog
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.*
import android.widget.*
import io.github.cfva14.musicapp.R
import io.github.cfva14.musicapp.data.Album
import io.github.cfva14.musicapp.data.Playlist
import io.github.cfva14.musicapp.data.Track
import io.github.cfva14.musicapp.utils.GenUtils
import io.github.cfva14.musicapp.utils.GlideApp

/**
 * Created by Carlos Valencia on 12/11/17.
 */

class AlbumFragment : Fragment(), AlbumContract.View, PopupMenu.OnMenuItemClickListener {

    private lateinit var albumImage: ImageView
    private lateinit var albumName: TextView
    private lateinit var artistName: TextView

    private lateinit var recyclerTrack: RecyclerView
    private lateinit var trackAdapter: AlbumFragment.TrackAdapter
    private lateinit var linearLayoutManager: RecyclerView.LayoutManager

    private lateinit var selectedTrack: Track

    override lateinit var presenter: AlbumContract.Presenter

    private var trackListener: TrackItemListener = object : TrackItemListener {
        override fun onTrackClick(clickedTrack: Track) {
            Toast.makeText(context, "Play all album starting with " + clickedTrack.title, Toast.LENGTH_SHORT).show()
        }

        override fun onMoreClick(clickedTrack: Track, view: View) {
            selectedTrack = clickedTrack
            val popUpMenu: PopupMenu

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                popUpMenu = PopupMenu(context, view, Gravity.END)
            } else {
                popUpMenu = PopupMenu(context, view)
            }

            popUpMenu.setOnMenuItemClickListener(this@AlbumFragment)
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
        GlideApp.with(context).load(album.imageUrl).into(albumImage)
        albumName.text = album.name
        artistName.text = album.artistName
    }

    override fun showTracks(tracks: List<Track>) {
        trackAdapter = TrackAdapter(context!!, tracks, trackListener)
        recyclerTrack.adapter = trackAdapter
    }

    override fun showPlaylistsByUser(playlists: List<Playlist>) {
        val alertPlaylist = AlertDialog.Builder(context!!, R.style.DialogStyle)
        alertPlaylist.setTitle(R.string.add_to_playlist)

        val playlistAdapter = ArrayAdapter<String>(context, android.R.layout.simple_list_item_single_choice)
        for (item in playlists) {
            playlistAdapter.add(item.name)
        }

        var playlist = playlists[0]

        alertPlaylist.setSingleChoiceItems(playlistAdapter, 0, DialogInterface.OnClickListener {
            _, i ->  playlist = playlists[i]
        })

        alertPlaylist.setNegativeButton(R.string.cancel, {
            dialogInterface, _ -> dialogInterface.dismiss()
        })

        alertPlaylist.setPositiveButton(R.string.ok, {
            dialogInterface, _ ->
            presenter.saveTrackToPlaylist(selectedTrack, playlist.id)
            dialogInterface.dismiss()
        })

        alertPlaylist.show()
    }

    override fun showMissingAlbum() {
        Toast.makeText(context, "No Album Found", Toast.LENGTH_SHORT).show()
    }

    override fun showMissingTracks() {
        Toast.makeText(context, "No Tracks Found", Toast.LENGTH_SHORT).show()
    }

    override fun showSaveTrackToPlaylistResult(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    private class TrackAdapter(private val context: Context, private val tracks: List<Track>, private val itemListener: TrackItemListener) : RecyclerView.Adapter<TrackAdapter.TrackHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): TrackHolder {
            val listItem = LayoutInflater.from(parent?.context).inflate(R.layout.list_item_track, parent, false)
            return TrackHolder(listItem)
        }

        override fun onBindViewHolder(holder: TrackHolder?, position: Int) {
            holder?.albumImage?.visibility = View.GONE
            holder?.number?.visibility = View.VISIBLE

            GlideApp.with(context).load(tracks[position].albumImageUrl).into(holder?.albumImage)
            holder?.albumName?.text = tracks[position].albumName
            holder?.number?.text = (position + 1).toString()
            holder?.title?.text = tracks[position].title
            holder?.duration?.text = GenUtils.formatTimer(tracks[position].duration)
            holder?.more?.setOnClickListener {
                itemListener.onMoreClick(tracks[position], holder.itemView)
            }
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
            val more: ImageView = itemView.findViewById(R.id.item_track_more)
        }
    }

    override fun onMenuItemClick(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.context_track_play -> {
                Toast.makeText(context, "Play ${selectedTrack.title}", Toast.LENGTH_SHORT).show()
            }
            R.id.context_track_play_next -> {
                Toast.makeText(context, "Play ${selectedTrack.title} after current playing song", Toast.LENGTH_SHORT).show()
            }
            R.id.context_track_add_to_playlist -> {
                presenter.getPlaylistsByUser("0cOv3qaAkpQxKRl06MxdT95QpOw2")
                return true
            }
        }
        return false
    }

    interface TrackItemListener {
        fun onTrackClick(clickedTrack: Track)
        fun onMoreClick(clickedTrack: Track, view: View)
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