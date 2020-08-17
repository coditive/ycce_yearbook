package com.syrous.ycceyearbook.ui.papers_and_resources

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import timber.log.Timber

class ViewPagerAdapter(fm: FragmentManager, lifecycle: Lifecycle) : FragmentStateAdapter(fm, lifecycle) {

    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment {
        Timber.d("Pager Adapter Initialized")
      return when(position) {
            0 -> FragmentEse()
            1 -> FragmentMse()
            2 -> FragmentResource()
          else -> throw Exception("No Fragment To Instantiate")
      }
    }

}