package io.github.cfva14.musicapp

import android.app.ActivityOptions
import android.content.Intent
import android.content.res.Configuration
import android.os.Build
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.Gravity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import io.github.cfva14.musicapp.home.HomeActivity


/**
 * Created by Carlos Valencia on 12/10/17.
 */

abstract class ActionBarActivity : AppCompatActivity() {

    private lateinit var toolbar: Toolbar
    private lateinit var drawerToggle: ActionBarDrawerToggle
    private lateinit var drawerLayout: DrawerLayout

    private var isToolbarInitialized: Boolean = false
    private var itemToOpenWhenDrawerCloses: Int = -1

    private val drawerListener = object : DrawerLayout.DrawerListener {

        override fun onDrawerClosed(drawerView: View) {
            if (drawerToggle != null) drawerToggle.onDrawerClosed(drawerView)
            if (itemToOpenWhenDrawerCloses >= 0) {

                var activityClass: Class<*>? = null
                when (itemToOpenWhenDrawerCloses) {
                    R.id.navigation_home -> activityClass = HomeActivity::class.java
                }
                if (activityClass != null) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        val extras = ActivityOptions.makeCustomAnimation(this@ActionBarActivity, R.anim.fade_in, R.anim.fade_out).toBundle()
                        startActivity(Intent(this@ActionBarActivity, activityClass), extras)
                    } else {
                        startActivity(Intent(this@ActionBarActivity, activityClass))
                    }
                }
            }
        }

        override fun onDrawerStateChanged(newState: Int) {
            if (drawerToggle != null) drawerToggle.onDrawerStateChanged(newState)
        }

        override fun onDrawerSlide(drawerView: View, slideOffset: Float) {
            if (drawerToggle != null) drawerToggle.onDrawerSlide(drawerView, slideOffset)
        }

        override fun onDrawerOpened(drawerView: View) {
            if (drawerToggle != null) drawerToggle.onDrawerOpened(drawerView)
            if (supportActionBar != null) supportActionBar?.setTitle(R.string.app_name)
        }
    }

    override fun onStart() {
        super.onStart()
        if(!isToolbarInitialized) throw IllegalStateException("You must run super.initializeToolbar at the end of your onCreate method")
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        if (drawerToggle != null) drawerToggle.syncState()
    }

    override fun onConfigurationChanged(newConfig: Configuration?) {
        super.onConfigurationChanged(newConfig)
        if(drawerToggle != null) drawerToggle.onConfigurationChanged(newConfig)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        super.onCreateOptionsMenu(menu)
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if(drawerToggle != null && drawerToggle.onOptionsItemSelected(item)) return true
        if (item != null && item.itemId == android.R.id.home) {
            onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        if (drawerLayout != null && drawerLayout.isDrawerOpen(Gravity.START)) {
            drawerLayout.closeDrawers()
            return
        }
        super.onBackPressed()
    }

    protected fun initializeToolbar() {
        toolbar = findViewById(R.id.toolbar)
        if (toolbar == null) throw IllegalStateException("Layout is required to include a Toolbar with id 'toolbar'")
        toolbar.inflateMenu(R.menu.main)

        drawerLayout = findViewById(R.id.drawer_layout)
        if (drawerLayout != null) {
            val navigationView: NavigationView = findViewById(R.id.nav_view)
            if (navigationView == null) throw IllegalStateException("Layout requires a NavigationView with id 'nav_view'")
            drawerToggle = ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open_content_drawer, R.string.close_content_drawer)
            drawerLayout.addDrawerListener(drawerListener)
            populateDrawerItems(navigationView)
            setSupportActionBar(toolbar)
            updateDrawerToggle()
        } else {
            setSupportActionBar(toolbar)
        }
        isToolbarInitialized = true
    }

    private fun populateDrawerItems(navigationView: NavigationView) {
        navigationView.setNavigationItemSelectedListener { menuItem ->
            menuItem.isChecked = true
            itemToOpenWhenDrawerCloses = menuItem.itemId
            drawerLayout.closeDrawers()
            true
        }
        if (HomeActivity::class.java.isAssignableFrom(javaClass)) {
            navigationView.setCheckedItem(R.id.navigation_home)
        }
    }

    protected fun updateDrawerToggle() {
        if (drawerToggle == null) return

        drawerToggle.isDrawerIndicatorEnabled = true
        if (supportActionBar != null) {
            supportActionBar!!.setDisplayShowHomeEnabled(true)
            supportActionBar!!.setDisplayHomeAsUpEnabled(true)
            supportActionBar!!.setHomeButtonEnabled(true)
        }
        drawerToggle.syncState()
    }
}