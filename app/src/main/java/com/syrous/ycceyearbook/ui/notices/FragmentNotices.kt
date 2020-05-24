package com.syrous.ycceyearbook.ui.notices

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.syrous.ycceyearbook.databinding.FragmentNoticeBinding

class FragmentNotices : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentNoticeBinding.inflate(layoutInflater, container, false)


        return binding.root
    }
}