package com.eaglesoft.imgur.framework.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.eaglesoft.imgur.R
import com.eaglesoft.imgur.framework.presentation.fragment.MainFragmentFactory
import com.eaglesoft.imgur.framework.presentation.fragment.details.DetailFragment
import com.eaglesoft.imgur.framework.presentation.fragment.list.ImageListFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var fragmentFactory: MainFragmentFactory
    var sharedViewModel: SharedViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setToolBar()
        sharedViewModel = ViewModelProvider(this).get(SharedViewModel::class.java)
        replaceFragment(ImageListFragment(""))

    }

    private fun setToolBar() {
        setSupportActionBar(toolbar)
        this.supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar.title = getString(R.string.app_name)
    }

    fun replaceFragment(fragment: Fragment) {
        if (fragment.tag == "DetailFragment") {
            if (sharedViewModel?.images != null) {
                toolbar.title = sharedViewModel?.images?.title
            }
        }
        supportFragmentManager.fragmentFactory = fragmentFactory
        supportFragmentManager.beginTransaction()
            .replace(R.id.main_container, fragment, fragment.tag)
            .addToBackStack(fragment.tag)
            .commit()
    }

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount == 1) {
            finish()
        } else {
            super.onBackPressed()
        }
    }
}