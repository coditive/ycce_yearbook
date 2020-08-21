package com.syrous.ycceyearbook.ui.papers_and_resources

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator
import com.google.firebase.storage.FirebaseStorage
import com.syrous.ycceyearbook.databinding.FragmentPaperAndResourcesBinding
import com.syrous.ycceyearbook.model.Paper
import com.syrous.ycceyearbook.model.Subject
import com.syrous.ycceyearbook.util.SUBJECT_OBJECT
import timber.log.Timber
import java.io.File
import java.io.Serializable

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

        val subject = arguments?.getSerializable(SUBJECT_OBJECT) as Subject
        viewPagerAdapter = ViewPagerAdapter(subject, PaperDownloader(), childFragmentManager, lifecycle)

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
        fun downloadPaper(paper: Paper): File {
            val path = requireActivity().getExternalFilesDir("papers")
            val paperFile = File(path, "paper${paper.id}.pdf")
            val storage = FirebaseStorage.getInstance()
            val refs = storage.getReferenceFromUrl(paper.url)
            refs.getFile(paperFile)
            Timber.d("File path : ${paperFile.name}, ${paperFile.absoluteFile}, Total Space: ${paperFile.totalSpace}")
            return paperFile
        }
    }
}