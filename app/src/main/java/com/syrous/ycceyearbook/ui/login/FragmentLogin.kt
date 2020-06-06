package com.syrous.ycceyearbook.ui.login

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.syrous.ycceyearbook.R
import com.syrous.ycceyearbook.databinding.FragmentLoginBinding

class FragmentLogin: Fragment() {

//    @Inject
//    lateinit var viewModel: LoginVM

    private lateinit var googleSignInClient: GoogleSignInClient

    private val RC_SIGN_IN = 9001

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentLoginBinding.inflate(layoutInflater, container, false)

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestId()
            .requestEmail()
            .requestProfile()
            .build()

        googleSignInClient = GoogleSignIn.getClient(requireActivity(), gso)


//        viewModel.status.observe(viewLifecycleOwner) {
//            when (it) {
//
//                LoginState.NOT_LOGGED_IN -> {
//                    Toast.makeText(
//                        requireContext(),
//                        "Please Sign Up Using Google",
//                        Toast.LENGTH_SHORT
//                    ).show()
//                }
//
//                LoginState.IN_PROGRESS -> {
//                    Toast.makeText(
//                        requireContext(),
//                        "Login In Progress",
//                        Toast.LENGTH_SHORT
//                    ).show()
//                }
//
//                LoginState.LOGGED_IN -> {
//                    Toast.makeText(
//                        requireContext(),
//                        "Please Sign Up Using Google",
//                        Toast.LENGTH_SHORT
//                    ).show()
//                }
//
//                LoginState.LOGIN_ERROR -> TODO("Display error screen")
//            }
//        }
//

        binding.buttonSignIn.apply {
            (this.getChildAt(0) as TextView).text = resources.getText(R.string.sign_in_button)
            setOnClickListener {
                signIn()
            }
        }

        return binding.root
    }


    private fun signIn() {
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(RC_SIGN_IN == requestCode) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)

            try {
                val account = task.getResult(ApiException::class.java)

                TODO("Call login function from view model with account.getIdToken()")

            } catch (e: ApiException) {
                TODO ("Display Error message on error screen")
            }
        }

    }
}