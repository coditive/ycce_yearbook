package com.syrous.ycceyearbook.ui.splash

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.syrous.ycceyearbook.databinding.FragmentSplashBinding
import kotlinx.coroutines.delay

class FragmentSplash: Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = FragmentSplashBinding.inflate(layoutInflater, container, false).root


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        lifecycleScope.launchWhenCreated {
            delay(2500)
            findNavController().navigate(FragmentSplashDirections.actionFragmentSplashToFragmentLogin())
        }
    }
}