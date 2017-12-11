package io.github.cfva14.musicapp.artist

import android.os.Bundle
import io.github.cfva14.musicapp.BaseActivity
import io.github.cfva14.musicapp.R
import io.github.cfva14.musicapp.utils.Injection
import io.github.cfva14.musicapp.utils.replaceFragmentInActivity

class ArtistActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_artist)
        initializeToolbar()

        val artistId = intent.getStringExtra(EXTRA_ARTIST_ID)

        val artistFragment = supportFragmentManager.findFragmentById(R.id.container)
                as ArtistFragment? ?: ArtistFragment.newInstance(artistId).also {
            replaceFragmentInActivity(it, R.id.container)
        }

        ArtistPresenter(artistId, Injection.provideArtistRepository(), artistFragment)
    }

    companion object {
        const val EXTRA_ARTIST_ID = "ARTIST_ID"
    }
}
