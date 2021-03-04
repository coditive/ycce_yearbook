package com.syrous.ycceyearbook.ui.papers_and_resources

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator
import com.syrous.ycceyearbook.YearBookApplication
import com.syrous.ycceyearbook.databinding.FragmentPaperAndResourcesBinding
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import timber.log.Timber

@ExperimentalCoroutinesApi
@FlowPreview
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

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().application as YearBookApplication).appComponent.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.prLoadingView.setAnimation("loading_spiral.json")

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
//
//        viewModel.dataLoading.observe(viewLifecycleOwner) {
//            if(it) {
//                binding.prLoadingView.visibility = View.VISIBLE
//                blankScreen()
//            } else {
//              binding.prLoadingView.visibility = View.GONE
//                showScreen()
//            }
//        }

    }

//    inner class PaperDownloader: Serializable {
//        suspend fun downloadPaper(paper: Paper) {
//           viewModel.storeRecentlyUsedPaper(requireActivity(), findNavController(), paper)
//        }
//    }

    private fun blankScreen() {
        binding.apply {
            paperAndResourceTabLayout.visibility = View.INVISIBLE
            paperAndResourceViewPager.visibility = View.INVISIBLE
        }
    }

    private fun showScreen() {
        binding.apply {
            paperAndResourceTabLayout.visibility = View.VISIBLE
            paperAndResourceViewPager.visibility = View.VISIBLE
        }
    }
}