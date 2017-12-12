package io.github.cfva14.musicapp.artist

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
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
import io.github.cfva14.musicapp.album.AlbumActivity
import io.github.cfva14.musicapp.data.Album
import io.github.cfva14.musicapp.data.Artist

/**
 * Created by Carlos Valencia on 12/10/17.
 */
class ArtistFragment : Fragment(), ArtistContract.View {

    override lateinit var presenter: ArtistContract.Presenter

    private lateinit var artistImage: ImageView
    private lateinit var recyclerAlbum: RecyclerView
    private lateinit var albumAdapter: AlbumAdapter
    private lateinit var horizontalLayoutManager: RecyclerView.LayoutManager

    private var albumListener: AlbumItemListener = object : AlbumItemListener {
        override fun onAlbumClick(clickedAlbum: Album) {
            presenter.openAlbumUI(clickedAlbum)
        }
    }

    private val parent: ArtistActivity = ArtistActivity()

    override fun onResume() {
        super.onResume()
        presenter.start()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.fragment_artist, container, false)
        setHasOptionsMenu(true)
        with(root) {
            artistImage = findViewById(R.id.artist_image)
            recyclerAlbum = findViewById(R.id.reclycler_album)
        }
        horizontalLayoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        recyclerAlbum.layoutManager = horizontalLayoutManager

        return root
    }

    override fun setLoadingIndicator(active: Boolean) {
        Log.e("ARTIST_FRAGMENT", active.toString())
    }

    override fun showArtist(artist: Artist) {
        Picasso.with(context).load(artist.imageUrl).into(artistImage)
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

    override fun showAlbums(albums: List<Album>) {
        albumAdapter = AlbumAdapter(context!!, albums, albumListener)
        recyclerAlbum.adapter = albumAdapter
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

    interface AlbumItemListener {
        fun onAlbumClick(clickedAlbum: Album)
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
