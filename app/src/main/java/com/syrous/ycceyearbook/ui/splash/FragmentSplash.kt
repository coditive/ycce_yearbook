package com.syrous.ycceyearbook.ui.splash

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.coroutineScope
import com.syrous.ycceyearbook.databinding.FragmentSplashBinding
import com.syrous.ycceyearbook.flux.Presenter
import com.syrous.ycceyearbook.presenter.SplashPresenter
import com.syrous.ycceyearbook.presenter.SplashView
import com.syrous.ycceyearbook.ui.BaseFragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview

@ExperimentalCoroutinesApi
@FlowPreview
class FragmentSplash: BaseFragment(), SplashView {
    override var presenter: Presenter = SplashPresenter(this)
    override val coroutineScope: CoroutineScope
        get() = viewLifecycleOwner.lifecycle.coroutineScope
    private lateinit var binding: FragmentSplashBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
       binding = FragmentSplashBinding.inflate(layoutInflater, container, false)
        return binding.root
    }
}