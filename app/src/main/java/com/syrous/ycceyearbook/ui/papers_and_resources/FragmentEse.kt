package com.syrous.ycceyearbook.ui.papers_and_resources

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import com.syrous.ycceyearbook.YearBookApplication
import com.syrous.ycceyearbook.databinding.FragmentPaperAndResourceDetailBinding
import javax.inject.Inject


class FragmentEse : Fragment() {

    @Inject
    lateinit var viewModel: PaperAndResourceVM

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

        val paperAdapter = PaperAdapter()
        viewModel.observeEsePaper("ct", 3, "GE1001").observe(viewLifecycleOwner) {
            paperAdapter.submitList(it)
        }

        binding.paperAndResourceRecycler.apply {
            adapter = paperAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }
}