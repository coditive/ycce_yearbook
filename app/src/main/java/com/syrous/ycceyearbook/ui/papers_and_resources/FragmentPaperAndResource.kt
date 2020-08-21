package com.syrous.ycceyearbook.ui.papers_and_resources

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator
import com.syrous.ycceyearbook.databinding.FragmentPaperAndResourcesBinding
import com.syrous.ycceyearbook.model.Subject
import timber.log.Timber

class FragmentPaperAndResource : Fragment() {

    private lateinit var binding: FragmentPaperAndResourcesBinding

    private lateinit var viewPagerAdapter: ViewPagerAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPaperAndResourcesBinding.inflate(layoutInflater, container, false)
        Timber.d("FragmentPaperAndResources initialized $activity")
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val subject = arguments?.getSerializable("subject") as Subject

        viewPagerAdapter = ViewPagerAdapter(subject, childFragmentManager, lifecycle)

        binding.apply {
            paperAndResourceViewPager.adapter = viewPagerAdapter
            TabLayoutMediator(paperAndResourceTabLayout, paperAndResourceViewPager) { tab, position ->
                when(position) {
                    0 -> tab.text = "ESE"
                    1 -> tab.text = "MSE"
                    2 -> tab.text = "Resources"
                }
                paperAndResourceViewPager.currentItem = tab.position
            }.attach()
        }
    }
}