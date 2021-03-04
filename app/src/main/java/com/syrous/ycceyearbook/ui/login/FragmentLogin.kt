package com.syrous.ycceyearbook.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.coroutineScope
import com.syrous.ycceyearbook.R
import com.syrous.ycceyearbook.databinding.FragmentLoginBinding
import com.syrous.ycceyearbook.flux.Presenter
import com.syrous.ycceyearbook.presenter.LoginPresenter
import com.syrous.ycceyearbook.presenter.LoginView
import com.syrous.ycceyearbook.ui.BaseFragment
import com.syrous.ycceyearbook.util.Constant
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow

@ExperimentalCoroutinesApi
@FlowPreview
class FragmentLogin: BaseFragment(), LoginView {
    private lateinit var binding: FragmentLoginBinding
    private val _loginButton = MutableSharedFlow<Unit>()
    override val loginButton: SharedFlow<Unit>
        get() = _loginButton
    override val coroutineScope: CoroutineScope
        get() = viewLifecycleOwner.lifecycle.coroutineScope
    override var presenter: Presenter = LoginPresenter(this)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(layoutInflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            loginLoadingView.setAnimation(Constant.Indicator.loading)
            buttonSignIn.apply {
                (this.getChildAt(0) as TextView).text = resources.getText(R.string.sign_in_button)
                setOnClickListener {
                    viewLifecycleOwner.lifecycle.coroutineScope.launchWhenCreated {
                        _loginButton.emit(Unit)
                    }
                }
            }
        }
    }

    override fun showAllView() {
        binding.apply {
            buttonSignIn.visibility = View.VISIBLE
            loginLogo.visibility = View.VISIBLE
            loginTitle.visibility = View.VISIBLE
            loginSubtitle.visibility = View.VISIBLE
        }
    }

    override fun hideAllView() {
        binding.apply {
            buttonSignIn.visibility = View.INVISIBLE
            loginLogo.visibility = View.INVISIBLE
            loginTitle.visibility = View.INVISIBLE
            loginSubtitle.visibility = View.INVISIBLE
        }
    }

    override fun showLoadingIndicator() {
        binding.loginLoadingView.visibility = View.VISIBLE
    }

    override fun hideLoadingIndicator() {
        binding.loginLoadingView.visibility = View.GONE
    }
}