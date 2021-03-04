package com.syrous.ycceyearbook.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.coroutineScope
import com.syrous.ycceyearbook.databinding.FragmentWelcomeBinding
import com.syrous.ycceyearbook.flux.Presenter
import com.syrous.ycceyearbook.presenter.WelcomePresenter
import com.syrous.ycceyearbook.presenter.WelcomeView
import com.syrous.ycceyearbook.ui.BaseFragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow

@FlowPreview
@ExperimentalCoroutinesApi
class FragmentWelcome : BaseFragment(), WelcomeView {
    private lateinit var binding: FragmentWelcomeBinding
    private val _okButtonClicks = MutableSharedFlow<Unit>()

    override val okButtonClicks: SharedFlow<Unit>
        get() = _okButtonClicks

    override val coroutineScope: CoroutineScope
        get() = viewLifecycleOwner.lifecycle.coroutineScope

    override var presenter: Presenter = WelcomePresenter(this)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentWelcomeBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
            binding.loginConfirmButton.setOnClickListener {
                viewLifecycleOwner.lifecycle.coroutineScope.launchWhenCreated {
                    _okButtonClicks.emit(Unit)
                }
            }
    }

    override fun hideViewObjects() {

    }

    override fun showLoadingIndicator() {

    }

    override fun hideLoadingIndicator() {
    }

}