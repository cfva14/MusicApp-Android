package io.github.cfva14.musicapp.album

import android.os.Bundle
import io.github.cfva14.musicapp.BaseActivity
import io.github.cfva14.musicapp.R
import io.github.cfva14.musicapp.utils.Injection
import io.github.cfva14.musicapp.utils.replaceFragmentInActivity

class AlbumActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_album)
        initializeToolbar()

        val albumId = intent.getStringExtra(EXTRA_ALBUM_ID)

        val albumFragment = supportFragmentManager.findFragmentById(R.id.container)
                as AlbumFragment? ?: AlbumFragment.newInstance(albumId).also {
            replaceFragmentInActivity(it, R.id.container)
        }

        AlbumPresenter(albumId, Injection.provideAlbumRepository(), albumFragment)
    }

    companion object {
        const val EXTRA_ALBUM_ID = "ALBUM_ID"
    }
}
