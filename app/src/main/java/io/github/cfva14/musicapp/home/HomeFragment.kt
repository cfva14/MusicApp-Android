package io.github.cfva14.musicapp.home

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.ContextMenu
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.github.cfva14.musicapp.R

/**
 * Created by Carlos Vlencia on 12/10/17.
 */

class HomeFragment : Fragment(), HomeContract.View {

    private val parent: HomeActivity = HomeActivity()

    override lateinit var presenter: HomeContract.Presenter

    override fun onResume() {
        super.onResume()
        presenter.start()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.fragment_home, container, false)
        setHasOptionsMenu(true)
        return root
    }

    override fun setLoadingIndicator(active: Boolean) {
       parent.setProgressBar(active)
    }

    companion object {
        fun newInstance() = HomeFragment()
    }
}