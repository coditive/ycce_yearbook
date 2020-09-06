package com.syrous.ycceyearbook.ui.login

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.syrous.ycceyearbook.databinding.FragmentGreetingScreenBinding
import com.syrous.ycceyearbook.ui.home.ActivityHome

class FragmentGreeting : Fragment() {

    private lateinit var binding: FragmentGreetingScreenBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentGreetingScreenBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.loginConfirmButton.setOnClickListener {
            startActivity(Intent(requireActivity(), ActivityHome::class.java))
        }
    }
}