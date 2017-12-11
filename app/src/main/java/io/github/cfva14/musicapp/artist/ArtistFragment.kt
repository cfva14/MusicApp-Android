package io.github.cfva14.musicapp.artist

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import io.github.cfva14.musicapp.R

/**
 * Created by Carlos Valencia on 12/10/17.
 */
class ArtistFragment : Fragment(), ArtistContract.View {

    private lateinit var artistName: TextView

    override lateinit var presenter: ArtistContract.Presenter

    override fun onResume() {
        super.onResume()
        presenter.start()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.fragment_artist, container, false)
        setHasOptionsMenu(true)
        with(root) {
            artistName = findViewById(R.id.artist_name)
        }
        return root
    }

    override fun setLoadingIndicator(active: Boolean) {

    }

    override fun showTitle(title: String) {
        artistName.text = title
    }

    override fun showMissingArtist() {
        Toast.makeText(context, "No Artist Found", Toast.LENGTH_SHORT).show()
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
