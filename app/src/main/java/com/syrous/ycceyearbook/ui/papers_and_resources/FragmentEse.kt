package com.syrous.ycceyearbook.ui.papers_and_resources

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.syrous.ycceyearbook.YearBookApplication
import com.syrous.ycceyearbook.databinding.FragmentPaperAndResourceDetailBinding
import com.syrous.ycceyearbook.model.Paper
import com.syrous.ycceyearbook.model.Subject
import com.syrous.ycceyearbook.ui.papers_and_resources.FragmentPaperAndResource.PaperDownloader
import com.syrous.ycceyearbook.util.PAPER_DOWNLOADER
import com.syrous.ycceyearbook.util.SUBJECT_OBJECT
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject


class FragmentEse : Fragment() {

    @Inject
    lateinit var viewModel: PaperAndResourceVM

    private lateinit var subject: Subject

    private lateinit var downloader: PaperDownloader

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
        subject = arguments?.getSerializable(SUBJECT_OBJECT) as Subject
        downloader = arguments?.getSerializable(PAPER_DOWNLOADER) as PaperDownloader
        Timber.d("Subject : $subject")
        val paperAdapter = PaperAdapter(EseClickHandler())
        viewModel.reloadEsePaperFromRemote(true)
        viewModel.observeEsePaper(subject.department, subject.sem, subject.course_code).observe(viewLifecycleOwner) {
            paperAdapter.submitList(it)
        }

        binding.paperAndResourceRecycler.apply {
            adapter = paperAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    inner class EseClickHandler: ClickHandler {
       override fun onClick(paper: Paper) {
           lifecycleScope.launch {
               val paperFile = downloader.downloadPaper(paper)
               findNavController().navigate(FragmentPaperAndResourceDirections
                   .actionFragmentPaperAndResourceToFragmentPdfRenderer(paperFile))
           }
        }
    }
}