package com.syrous.ycceyearbook.ui.recent

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.syrous.ycceyearbook.R
import com.syrous.ycceyearbook.YearBookApplication
import com.syrous.ycceyearbook.databinding.FragmentRecentBinding
import com.syrous.ycceyearbook.model.Paper
import com.syrous.ycceyearbook.ui.home.bottom_nav.BottomNavView
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview

class FragmentRecent : Fragment() {

    private lateinit var binding: FragmentRecentBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRecentBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().application as YearBookApplication).appComponent.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requireActivity().findViewById<BottomNavView>(R.id.bottomBar).visibility = View.VISIBLE
//        val recentAdapter = RecentAdapter(ClickHandler())
//        viewModel.listOfPapers.observe(viewLifecycleOwner) {
//            recentAdapter.submitList(it)
//        }
//        binding.recentRecycler.apply {
//            adapter = recentAdapter
//            layoutManager = LinearLayoutManager(requireContext())
//        }
    }

    inner class ClickHandler {
        fun onClick(paper: Paper) {
            Toast.makeText(requireContext(), "Paper = ${paper.id} selected", Toast.LENGTH_SHORT).show()
//            val pdfFile = viewModel.getFileFromStorage(requireActivity(), paper)
//            requireActivity().findViewById<BottomNavView>(R.id.bottomBar).visibility = View.GONE

        }
    }
}