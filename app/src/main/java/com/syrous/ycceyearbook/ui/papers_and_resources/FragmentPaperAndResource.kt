package com.syrous.ycceyearbook.ui.papers_and_resources

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.google.android.material.tabs.TabLayoutMediator
import com.google.firebase.storage.FirebaseStorage
import com.syrous.ycceyearbook.YearBookApplication
import com.syrous.ycceyearbook.databinding.FragmentPaperAndResourcesBinding
import com.syrous.ycceyearbook.model.Paper
import kotlinx.coroutines.delay
import timber.log.Timber
import java.io.File
import java.io.Serializable
import javax.inject.Inject

class FragmentPaperAndResource : Fragment() {

    private lateinit var binding: FragmentPaperAndResourcesBinding

    @Inject
    lateinit var viewModel: PaperAndResourceVM

    private val args: FragmentPaperAndResourceArgs by navArgs()

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

        viewPagerAdapter = ViewPagerAdapter(args.subject, PaperDownloader(), childFragmentManager, lifecycle)

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

    inner class PaperDownloader: Serializable {
        suspend fun downloadPaper(paper: Paper): File {
            viewModel.storeRecentlyUsedPaper(paper)
            val path = requireActivity().getExternalFilesDir("papers")
            val paperFile = File(path, "paper${paper.id}.pdf")
            val storage = FirebaseStorage.getInstance()
            val refs = storage.getReferenceFromUrl(paper.url)
            refs.getFile(paperFile)
            delay(2000)
            Timber.d("File path : ${paperFile.name}, ${paperFile.absoluteFile}, Total Space: ${paperFile.totalSpace}")
            return paperFile
        }
    }
}