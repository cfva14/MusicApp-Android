package io.github.cfva14.musicapp.album

import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import io.github.cfva14.musicapp.R

/**
 * Created by Carlos Valencia on 12/11/17.
 */

class AlbumFragment : Fragment(), AlbumContract.View {

    private val parent: AlbumActivity = AlbumActivity()

    override lateinit var presenter: AlbumContract.Presenter

    override fun onResume() {
        super.onResume()
        presenter.start()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.fragment_album, container, false)
        setHasOptionsMenu(true)
        return root
    }

    override fun setLoadingIndicator(active: Boolean) {
        Log.e("ALBUM_FRAGMENT", active.toString())
    }

    override fun showTitle(title: String) {

    }


    override fun showMissingAlbum() {
        Toast.makeText(context, "No Album Found", Toast.LENGTH_SHORT).show()
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