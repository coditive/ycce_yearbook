package com.syrous.ycceyearbook.ui.splash

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.syrous.ycceyearbook.YearBookApplication
import com.syrous.ycceyearbook.databinding.FragmentSplashBinding
import com.syrous.ycceyearbook.model.Result
import com.syrous.ycceyearbook.ui.home.ActivityHome
import com.syrous.ycceyearbook.util.user.UserDataRepository
import kotlinx.coroutines.delay

class FragmentSplash: Fragment() {

    private lateinit var userDataRepository: UserDataRepository

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = FragmentSplashBinding.inflate(layoutInflater, container, false).root


    override fun onAttach(context: Context) {
        super.onAttach(context)
       userDataRepository =  (requireActivity().application as YearBookApplication).appComponent.userRepository()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        lifecycleScope.launchWhenCreated {
            delay(2500)
            val user = userDataRepository.getLoggedInUser()
            if(user is Result.Success) {
                startActivity(Intent(requireActivity(), ActivityHome::class.java))
            } else {
                findNavController().navigate(FragmentSplashDirections.actionFragmentSplashToFragmentLogin())
            }
        }
    }
}