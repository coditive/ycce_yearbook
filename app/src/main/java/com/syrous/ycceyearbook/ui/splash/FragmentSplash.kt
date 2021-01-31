package com.syrous.ycceyearbook.ui.splash

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.syrous.ycceyearbook.YearBookApplication
import com.syrous.ycceyearbook.action.RouteAction
import com.syrous.ycceyearbook.databinding.FragmentSplashBinding
import com.syrous.ycceyearbook.flux.Dispatcher
import com.syrous.ycceyearbook.store.RouteStore
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.delay
import javax.inject.Inject

@ExperimentalCoroutinesApi
@FlowPreview
class FragmentSplash: Fragment() {

    @Inject
    lateinit var dispatcher: Dispatcher

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentSplashBinding.inflate(layoutInflater, container, false).root

    override fun onAttachFragment(childFragment: Fragment) {
        super.onAttachFragment(childFragment)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        (requireActivity().application as YearBookApplication).appComponent.inject(this)

        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            delay(1500)
        }
    }
}