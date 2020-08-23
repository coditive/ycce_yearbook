package com.syrous.ycceyearbook.ui.recent

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import com.syrous.ycceyearbook.YearBookApplication
import com.syrous.ycceyearbook.databinding.FragmentRecentBinding
import com.syrous.ycceyearbook.model.Paper
import javax.inject.Inject

class FragmentRecent : Fragment() {

    private lateinit var binding: FragmentRecentBinding

    @Inject
    lateinit var viewModel: RecentVM

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
        (requireActivity().application as YearBookApplication).appComponent.userManager()
            .userComponent!!.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recentAdapter = RecentAdapter(ClickHandler())

        viewModel.listOfPapers.observe(viewLifecycleOwner) {
            recentAdapter.submitList(it)
        }

        binding.recentRecycler.apply {
            adapter = recentAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }


    }

    inner class ClickHandler {
        fun onClick(paper: Paper) {
            Toast.makeText(requireContext(), "Paper = ${paper.id} selected", Toast.LENGTH_SHORT).show()
            TODO("Navigate to Pdf Renderer using deep link")
        }
    }
}