package com.eaglesoft.imgur.framework.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.eaglesoft.imgur.R
import com.eaglesoft.imgur.framework.presentation.fragment.list.ImageListFragment
import com.eaglesoft.imgur.framework.presentation.fragment.MainFragmentFactory
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var fragmentFactory: MainFragmentFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager.fragmentFactory = fragmentFactory
        supportFragmentManager.beginTransaction()
            .replace(R.id.main_container, ImageListFragment::class.java, null)
            .commit()
    }

    fun replaceFragment(existing: Fragment, new: Fragment, tag: String? = null) {
        supportFragmentManager.beginTransaction().replace(existing.id, new, tag).commit()
    }
}



















