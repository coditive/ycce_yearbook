package com.syrous.ycceyearbook.ui.papers_and_resources

import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.syrous.ycceyearbook.model.Subject
import timber.log.Timber

class ViewPagerAdapter(private val subject: Subject,
                       fm: FragmentManager,
                       lifecycle: Lifecycle) : FragmentStateAdapter(fm, lifecycle) {

    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment {
        val bundle = bundleOf("subject" to subject)
        Timber.d("Pager Adapter Initialized")
        when(position) {
            0 -> {
                val fragment = FragmentEse()
                fragment.arguments = bundle
                return fragment
            }
            1 -> {
                val fragment = FragmentMse()
                fragment.arguments = bundle
                return fragment
            }
            2 ->  {
                val fragment = FragmentResource()
                fragment.arguments = bundle
                return fragment
            }
          else -> throw Exception("No Fragment To Instantiate")
      }
    }

}