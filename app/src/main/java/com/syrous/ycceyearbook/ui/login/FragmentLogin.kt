package com.syrous.ycceyearbook.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.syrous.ycceyearbook.databinding.FragmentLoginBinding

class FragmentLogin: Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentLoginBinding.inflate(layoutInflater, container, false)
        

        return binding.root
    }
}