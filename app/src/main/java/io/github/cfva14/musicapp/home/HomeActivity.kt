package io.github.cfva14.musicapp.home

import android.os.Bundle
import io.github.cfva14.musicapp.PlayerActivity
import io.github.cfva14.musicapp.R
import io.github.cfva14.musicapp.utils.replaceFragmentInActivity

class HomeActivity : PlayerActivity() {

    private lateinit var homePresenter: HomePresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        initializeToolbar()

        val homeFragment = supportFragmentManager.findFragmentById(R.id.container)
                as HomeFragment? ?: HomeFragment.newInstance().also {
            replaceFragmentInActivity(it, R.id.container)
        }

        homePresenter = HomePresenter(homeFragment)

    }
}
