package io.github.cfva14.musicapp.home

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import io.github.cfva14.musicapp.R
import io.github.cfva14.musicapp.artist.ArtistActivity
import io.github.cfva14.musicapp.data.Artist

/**
 * Created by Carlos Valencia on 12/10/17.
 */

class HomeFragment : Fragment(), HomeContract.View {

    private lateinit var button: Button

    private val parent: HomeActivity = HomeActivity()

    override lateinit var presenter: HomeContract.Presenter

    override fun onResume() {
        super.onResume()
        presenter.start()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.fragment_home, container, false)
        setHasOptionsMenu(true)
        with(root) {
            button = findViewById(R.id.button)
        }

        button.setOnClickListener{
            presenter.openArtistUI(Artist())
        }
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

    companion object {
        fun newInstance() = HomeFragment()
    }
}