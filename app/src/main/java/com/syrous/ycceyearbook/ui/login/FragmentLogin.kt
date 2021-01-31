package com.syrous.ycceyearbook.ui.login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.syrous.ycceyearbook.R
import com.syrous.ycceyearbook.YearBookApplication
import com.syrous.ycceyearbook.action.AccountAction
import com.syrous.ycceyearbook.databinding.FragmentLoginBinding
import com.syrous.ycceyearbook.flux.Dispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import timber.log.Timber
import javax.inject.Inject

@ExperimentalCoroutinesApi
@FlowPreview
class FragmentLogin: Fragment() {

    private lateinit var binding: FragmentLoginBinding

    @Inject
    lateinit var dispatcher: Dispatcher

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        // Creates an instance of Registration component by grabbing the factory from the app graph
        (requireActivity().application as YearBookApplication).appComponent.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            loginLoadingView.setAnimation("loading_spiral.json")
            buttonSignIn.apply {
                (this.getChildAt(0) as TextView).text = resources.getText(R.string.sign_in_button)
                setOnClickListener {
                    Timber.d("Google Login initiated !!!")
                    dispatcher.dispatch(AccountAction.InitiateLogin)
                }
            }
        }
    }

    private fun blankTheScreen() {
        binding.apply {
            buttonSignIn.visibility = View.INVISIBLE
            loginLogo.visibility = View.INVISIBLE
            loginTitle.visibility = View.INVISIBLE
            loginSubtitle.visibility = View.INVISIBLE
        }
    }

    private fun makeScreenVisible() {
        binding.apply {
            buttonSignIn.visibility = View.VISIBLE
            loginLogo.visibility = View.VISIBLE
            loginTitle.visibility = View.VISIBLE
            loginSubtitle.visibility = View.VISIBLE
        }
    }
}