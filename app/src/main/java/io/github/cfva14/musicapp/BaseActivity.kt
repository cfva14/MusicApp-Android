package io.github.cfva14.musicapp

import android.view.View
import android.widget.ProgressBar
import android.app.ProgressDialog
import android.support.annotation.VisibleForTesting



/**
 * Created by Carlos Valencia on 12/10/17.
 */

abstract class BaseActivity : ActionBarActivity() {

    var progressBar: ProgressBar? = null

    fun setProgressBar(active: Boolean) {
        if (progressBar == null) {
            progressBar = findViewById(R.id.progress_bar)
        }
        if (active) {
            progressBar!!.visibility = View.VISIBLE
        } else {
            progressBar!!.visibility = View.GONE
        }
    }

    override fun onStop() {
        super.onStop()
        progressBar!!.visibility = View.GONE
    }
}

