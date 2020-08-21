package com.syrous.ycceyearbook.ui.papers_and_resources

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.storage.FirebaseStorage
import com.syrous.ycceyearbook.R
import com.syrous.ycceyearbook.YearBookApplication
import com.syrous.ycceyearbook.databinding.FragmentPaperAndResourceDetailBinding
import com.syrous.ycceyearbook.model.Paper
import com.syrous.ycceyearbook.model.Subject
import timber.log.Timber
import java.io.File
import javax.inject.Inject


class FragmentEse : Fragment() {

    @Inject
    lateinit var viewModel: PaperAndResourceVM

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


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (requireActivity().application as YearBookApplication).appComponent.inject(this)

        subject = arguments?.getSerializable("subject") as Subject

        val paperAdapter = PaperAdapter(ClickHandler())
//        viewModel.reloadEsePaperFromRemote(true)

        viewModel.observeEsePaper(subject.department, subject.sem, subject.course).observe(viewLifecycleOwner) {
            paperAdapter.submitList(it)
        }

        binding.paperAndResourceRecycler.apply {
            adapter = paperAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }


    fun getFileFromRemote(paper: Paper): File {
        val path = requireActivity().getExternalFilesDir("papers")
        val paperFile = File(path, "paper${paper.id}.pdf")
        val storage = FirebaseStorage.getInstance()
        val refs = storage.getReferenceFromUrl(paper.url)
        refs.getFile(paperFile)
        Timber.d("File path : ${paperFile.name}, ${paperFile.absoluteFile}, Total Space: ${paperFile.totalSpace}")
        return paperFile
    }

    inner class ClickHandler {
        fun onClick(paper: Paper) {
            val paperFile = getFileFromRemote(paper)
            val args = bundleOf("pdfFile" to paperFile)
            findNavController().navigate(R.id.fragmentPdfRenderer, args)
        }
    }
}