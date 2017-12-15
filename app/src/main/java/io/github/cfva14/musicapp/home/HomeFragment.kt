package io.github.cfva14.musicapp.home

import android.content.Context
import android.content.Intent
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
import io.github.cfva14.musicapp.R
import io.github.cfva14.musicapp.artist.ArtistActivity
import io.github.cfva14.musicapp.data.Artist
import io.github.cfva14.musicapp.utils.GlideApp

/**
 * Created by Carlos Valencia on 12/10/17.
 */

class HomeFragment : Fragment(), HomeContract.View {

    private lateinit var tileAdapter: TileAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var horizontalLayoutManager: RecyclerView.LayoutManager

    override lateinit var presenter: HomeContract.Presenter

    private var tileListener: TileItemListener = object : TileItemListener {
        override fun onTileClick(clickedTile: Artist) {
            presenter.openArtistUI(clickedTile)
        }
    }

    override fun onResume() {
        super.onResume()
        presenter.start()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.fragment_home, container, false)
        setHasOptionsMenu(true)
        with(root) {
            recyclerView = findViewById(R.id.recycler_near_artist)
        }

        horizontalLayoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        recyclerView.layoutManager = horizontalLayoutManager
        recyclerView.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.HORIZONTAL))

        return root
    }

    override fun setLoadingIndicator(active: Boolean) {
        Log.e("HOME_FRAGMENT", active.toString())
    }

    override fun showArtistUI(artistId: String) {
        val intent = Intent(context, ArtistActivity::class.java).apply {
            putExtra(ArtistActivity.EXTRA_ARTIST_ID, artistId)
        }
        startActivity(intent)
    }

    override fun showNearArtists(artist: List<Artist>) {
        tileAdapter = TileAdapter(context!!, artist, tileListener)
        recyclerView.adapter = tileAdapter
    }

    override fun showMissingNearArtists() {
        Toast.makeText(context, "No Near Artists", Toast.LENGTH_SHORT).show()
    }

    private class TileAdapter(private val context: Context, private val tiles: List<Artist>, private val itemListener: TileItemListener) : RecyclerView.Adapter<TileAdapter.TileHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): TileHolder {
            val listItem = LayoutInflater.from(parent?.context).inflate(R.layout.list_item_album, parent, false)
            return TileHolder(listItem)
        }

        override fun onBindViewHolder(holder: TileHolder?, position: Int) {
            GlideApp.with(context).load(tiles[position].imageUrl).into(holder?.tileImage)
            holder?.tileName?.text = tiles[position].name
            holder?.itemView?.setOnClickListener {
                itemListener.onTileClick(tiles[position])
            }
        }

        override fun getItemCount() = tiles.size

        class TileHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val tileImage: ImageView = itemView.findViewById(R.id.item_album_image)
            val tileName: TextView = itemView.findViewById(R.id.item_album_name)
        }

    }

    interface TileItemListener {
        fun onTileClick(clickedTile: Artist)
    }

    companion object {
        fun newInstance() = HomeFragment()
    }
}