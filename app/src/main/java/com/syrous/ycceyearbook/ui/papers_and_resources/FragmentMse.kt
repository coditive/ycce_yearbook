package com.syrous.ycceyearbook.ui.papers_and_resources

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.syrous.ycceyearbook.YearBookApplication
import com.syrous.ycceyearbook.databinding.FragmentPaperAndResourceDetailBinding
import com.syrous.ycceyearbook.model.Subject
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview


@ExperimentalCoroutinesApi
@FlowPreview
class FragmentMse : Fragment() {
    private lateinit var subject: Subject
    private lateinit var binding: FragmentPaperAndResourceDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPaperAndResourceDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().application as YearBookApplication).appComponent.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        subject = arguments?.getSerializable(SUBJECT_OBJECT) as Subject
//        downloader = arguments?.getSerializable(PAPER_DOWNLOADER) as FragmentPaperAndResource.PaperDownloader
//        Timber.d("Subject : $subject")
//        val paperAdapter = PaperAdapter(MseClickHandler())
//        viewModel.reloadEsePaperFromRemote(true)
//        viewModel.observeEsePaper(subject.department, subject.sem, subject.course_code).observe(viewLifecycleOwner) {
//            paperAdapter.submitList(it)
//        }
//
//        binding.paperAndResourceRecycler.apply {
//            adapter = paperAdapter
//            layoutManager = LinearLayoutManager(requireContext())
//        }
    }

//    inner class MseClickHandler: ClickHandler {
//        override fun onClick(paper: Paper) {
//            lifecycleScope.launch {
//                viewModel.storeRecentlyUsedPaper(requireContext(), findNavController(), paper)
//            }
//        }
//    }
}