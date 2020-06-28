package com.syrous.ycceyearbook.ui.recent

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.syrous.ycceyearbook.databinding.FragmentRecentBinding

class FragmentRecent : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = FragmentRecentBinding.inflate(layoutInflater, container, false)



        return binding.root
    }
}