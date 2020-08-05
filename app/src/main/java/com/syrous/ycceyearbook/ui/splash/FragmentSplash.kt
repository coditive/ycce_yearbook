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
import com.syrous.ycceyearbook.data.UserSharedPrefStorage
import com.syrous.ycceyearbook.databinding.FragmentSplashBinding
import com.syrous.ycceyearbook.ui.home.ActivityHome
import com.syrous.ycceyearbook.util.user.UserManager
import kotlinx.coroutines.delay
import javax.inject.Inject

class FragmentSplash: Fragment() {

    @Inject
    lateinit var userManager: UserManager

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = FragmentSplashBinding.inflate(layoutInflater, container, false).root


    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().application as YearBookApplication).appComponent.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        lifecycleScope.launchWhenCreated {
            delay(2500)
            if(userManager.isUserLoggedIn()) {
                startActivity(Intent(requireActivity(), ActivityHome::class.java))
            } else {
                findNavController().navigate(FragmentSplashDirections.actionFragmentSplashToFragmentLogin())
            }
        }
    }
}