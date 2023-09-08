package com.abhishek.shelfapp.ui.login

import android.os.Bundle
import android.text.method.PasswordTransformationMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.abhishek.shelfapp.R
import com.abhishek.shelfapp.databinding.FragmentLoginBinding
import com.abhishek.shelfapp.utils.common.hideKeyboard
import com.abhishek.shelfapp.utils.common.visible
import com.abhishek.shelfapp.utils.display.Toaster
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private val viewModel: LoginViewModel by viewModels()
    private lateinit var binding: FragmentLoginBinding
    private lateinit var userName: String
    private lateinit var password: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupClickListeners()
        setupObservers()
    }

    private fun setupObservers() {
        viewModel.currentUser.observe(viewLifecycleOwner, Observer {
            if (it.userName == userName && it.userPassword == password) {
                viewModel.setIsUserLoggedIn(true)
                findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
            } else {
                Toaster.show(requireContext(), R.string.label_invalid_credentials)
            }
        })
    }

    private fun setupClickListeners() {
        binding.apply {
            tvLogin.setOnClickListener {
                requireContext().hideKeyboard(requireActivity())
                userName = binding.etUsername.text.toString().trim()
                password = binding.etPassword.text.toString().trim()
                if (userName.isNullOrEmpty()) {
                    tvUserNameError.visible(true)
                    return@setOnClickListener
                } else {
                    tvUserNameError.visible(false)
                }
                if (password.isNullOrEmpty()) {
                    tvPasswordError.visible(true)
                    return@setOnClickListener
                } else {
                    tvPasswordError.visible(false)
                }
                viewModel.getCurrentUser()
            }

            ivShowHidePassword.setOnClickListener {
                togglePasswordVisibility()
            }

            tvSignup.setOnClickListener {
                findNavController().navigate(R.id.action_loginFragment_to_signUpFragment)
            }
        }
    }

    private fun togglePasswordVisibility() {
        if (binding.etPassword.transformationMethod == null) {
            binding.etPassword.transformationMethod = PasswordTransformationMethod.getInstance()
            binding.ivShowHidePassword.setImageResource(R.drawable.ic_close_eye)
        } else {
            binding.etPassword.transformationMethod = null
            binding.ivShowHidePassword.setImageResource(R.drawable.ic_open_eye)
        }
    }


}