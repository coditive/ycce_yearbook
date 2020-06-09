package com.syrous.ycceyearbook.ui.splash

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.syrous.ycceyearbook.databinding.FragmentSplashBinding
import com.syrous.ycceyearbook.ui.home.ActivityHome
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

            val auth = FirebaseAuth.getInstance()

            if(auth.currentUser == null) {
                findNavController().navigate(FragmentSplashDirections.actionFragmentSplashToFragmentLogin())
            } else {
             startActivity(Intent(requireActivity(), ActivityHome::class.java))
            }
        }
    }
}