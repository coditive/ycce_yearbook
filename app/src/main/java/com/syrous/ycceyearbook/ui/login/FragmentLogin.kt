package com.syrous.ycceyearbook.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import com.syrous.ycceyearbook.databinding.FragmentLoginBinding

class FragmentLogin: Fragment() {

    private val viewModel by viewModels<LoginVM>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentLoginBinding.inflate(layoutInflater, container, false)


        viewModel.status.observe(viewLifecycleOwner) {
            when (it) {

                LoginState.NOT_LOGGED_IN -> {
                    Toast.makeText(
                        requireContext(),
                        "Please Sign Up Using Google",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                LoginState.IN_PROGRESS -> {
                    Toast.makeText(
                        requireContext(),
                        "Login In Progress",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                LoginState.LOGGED_IN -> {
                    Toast.makeText(
                        requireContext(),
                        "Please Sign Up Using Google",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                LoginState.LOGIN_ERROR -> TODO("Display error screen")
            }
        }

        return binding.root
    }
}