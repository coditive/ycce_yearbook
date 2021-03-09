package com.syrous.ycceyearbook.ui.papers_and_resources

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator
import com.syrous.ycceyearbook.databinding.FragmentPaperAndResourcesBinding
import com.syrous.ycceyearbook.model.Subject
import com.syrous.ycceyearbook.util.Constant
import com.syrous.ycceyearbook.util.SUBJECT_OBJECT
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview

@ExperimentalCoroutinesApi
@FlowPreview
class FragmentPaperAndResource : Fragment() {
    private lateinit var subject: Subject
    private lateinit var binding: FragmentPaperAndResourcesBinding
    private lateinit var viewPagerAdapter: ViewPagerAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPaperAndResourcesBinding.inflate(layoutInflater, container, false)
        subject = arguments?.getSerializable(SUBJECT_OBJECT) as Subject
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.prLoadingView.setAnimation(Constant.Indicator.loading)
        viewPagerAdapter = ViewPagerAdapter(subject, childFragmentManager, viewLifecycleOwner.lifecycle)
        binding.apply {
            paperAndResourceViewPager.adapter = viewPagerAdapter
            TabLayoutMediator(
                paperAndResourceTabLayout,
                paperAndResourceViewPager
            ) { tab, position ->
                when (position) {
                    0 -> tab.text = "ESE"
                    1 -> tab.text = "MSE"
                    2 -> tab.text = "Resources"
                }
                paperAndResourceViewPager.currentItem = tab.position
            }.attach()
        }
    }
}