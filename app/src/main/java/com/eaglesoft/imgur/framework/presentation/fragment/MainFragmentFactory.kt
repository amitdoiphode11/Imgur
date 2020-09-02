package com.eaglesoft.imgur.framework.presentation.fragment

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.eaglesoft.imgur.framework.presentation.fragment.details.DetailFragment
import com.eaglesoft.imgur.framework.presentation.fragment.list.ImageListFragment
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject

@ExperimentalCoroutinesApi
class MainFragmentFactory
@Inject
constructor(
    private val someString: String
): FragmentFactory(){

    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
        return when (className) {

            ImageListFragment::class.java.name -> {
                val fragment = ImageListFragment(someString)
                fragment
            }

            DetailFragment::class.java.name -> {
                val fragment = DetailFragment(someString)
                fragment
            }

            else -> super.instantiate(classLoader, className)
        }
    }
}