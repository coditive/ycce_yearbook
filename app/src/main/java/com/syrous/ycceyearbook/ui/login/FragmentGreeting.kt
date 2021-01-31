package com.syrous.ycceyearbook.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.syrous.ycceyearbook.YearBookApplication
import com.syrous.ycceyearbook.action.RouteAction
import com.syrous.ycceyearbook.databinding.FragmentGreetingScreenBinding
import com.syrous.ycceyearbook.flux.Dispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import javax.inject.Inject

@FlowPreview
@ExperimentalCoroutinesApi
class FragmentGreeting : Fragment() {

    private lateinit var binding: FragmentGreetingScreenBinding

    @Inject
    lateinit var dispatcher: Dispatcher
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentGreetingScreenBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (requireActivity().application as YearBookApplication).appComponent.inject(this)
        binding.loginConfirmButton.setOnClickListener {
            dispatcher.dispatch(RouteAction.Login)
        }
    }
}