package com.syrous.ycceyearbook.ui.papers_and_resources

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.syrous.ycceyearbook.databinding.FragmentPaperAndResourcesBinding

class FragmentPaperAndResource : Fragment() {

    private lateinit var binding: FragmentPaperAndResourcesBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPaperAndResourcesBinding.inflate(layoutInflater, container, false)



        return binding.root
    }


}