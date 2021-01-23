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
import com.syrous.ycceyearbook.databinding.FragmentLoginBinding
import timber.log.Timber
import javax.inject.Inject

class FragmentLogin: Fragment() {

    @Inject
    lateinit var viewModel: LoginVM

    private lateinit var binding: FragmentLoginBinding

    @Inject
    lateinit var googleSignInClient: GoogleSignInClient

    @Inject
    lateinit var auth: FirebaseAuth

    private val RC_SIGN_IN = 9001

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
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

        binding.loginLoadingView.setAnimation("loading_spiral.json")

        viewModel.status.observe(viewLifecycleOwner) {
            when (it) {
                LoginState.NOT_LOGGED_IN -> {
                    Toast.makeText(
                        requireContext(),
                        "Please Sign Up Using Google",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                LoginState.LOGGED_IN -> {
                    Toast.makeText(
                        requireContext(),
                        "Logged In Successfully",
                        Toast.LENGTH_SHORT
                    ).show()
                    blankTheScreen()
                }
                LoginState.LOGIN_ERROR -> {
                    Toast.makeText(
                        requireContext(),
                        "Please Login Again!!",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }

        viewModel.loading.observe(viewLifecycleOwner) {
            when(it) {
                true -> {
                    binding.loginLoadingView.visibility = View.VISIBLE
                    blankTheScreen()
                }
                false -> {
                    binding.loginLoadingView.visibility = View.GONE
                    makeScreenVisible()
                }
            }
        }

        binding.buttonSignIn.apply {
            (this.getChildAt(0) as TextView).text = resources.getText(R.string.sign_in_button)
            setOnClickListener {
                signIn()
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

    private fun signIn() {
        val signInIntent = googleSignInClient.signInIntent
        Timber.d("Sign in Called!!")
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(RC_SIGN_IN == requestCode) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)

            try {
                val account = task.getResult(ApiException::class.java)
                Timber.d("account details : ${account?.displayName}")
                account?.let {
                    viewModel.loginUser(account)
                }

            } catch (e: ApiException) {
                Timber.e("google sign in error : ${e.message}")
            }
        }

    }
}