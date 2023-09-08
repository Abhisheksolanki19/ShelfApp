package com.abhishek.shelfapp.ui.signup

import android.os.Bundle
import android.text.method.PasswordTransformationMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.abhishek.shelfapp.R
import com.abhishek.shelfapp.data.model.AppUser
import com.abhishek.shelfapp.databinding.FragmentSignUpBinding
import com.abhishek.shelfapp.utils.common.EventObserver
import com.abhishek.shelfapp.utils.common.hideKeyboard
import com.abhishek.shelfapp.utils.common.visible
import com.abhishek.shelfapp.utils.display.Toaster
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignUpFragment : Fragment() {

    private lateinit var binding: FragmentSignUpBinding
    private val viewModel: SignUpViewModel by viewModels()
    private var countryList: ArrayList<String> = arrayListOf()
    private lateinit var selectedCountry: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSignUpBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupClickListeners()
        setupObservers()
    }

    private fun setupClickListeners() {
        binding.apply {
            requireActivity().onBackPressedDispatcher.addCallback(
                viewLifecycleOwner,
                object : OnBackPressedCallback(true) {
                    override fun handleOnBackPressed() {
                        val navController = findNavController()
                        if (navController.currentDestination?.id == R.id.signUpFragment) {
                            requireActivity().finish()
                        }
                    }
                })

            ivShowHidePassword.setOnClickListener {
                togglePasswordVisibility()
            }
            ivShowHideConfirmPassword.setOnClickListener {
                toggleConfirmPasswordVisibility()
            }
            tvSignUp.setOnClickListener {
                requireContext().hideKeyboard(requireActivity())
                val userNamePattern = Regex("^[a-zA-Z0-9_-]{3,16}$")
                val passwordPattern =
                    Regex("^(?=.*[0-9])(?=.*[!@#\$%^&*()])(?=.*[a-z])(?=.*[A-Z]).{8,}\$")
                val userName = binding.etUsername.text.toString().trim()
                val password = binding.etPassword.text.toString().trim()
                if (userName.isNullOrEmpty()) {
                    tvUserNameError.visible(true)
                    return@setOnClickListener
                } else {
                    tvUserNameError.visible(false)
                }
                if (!userName.matches(userNamePattern)) {
                    tvUserNameError.text = getString(R.string.msg_user_name_invalid)
                    tvUserNameError.visible(true)
                    return@setOnClickListener
                } else {
                    tvUserNameError.visible(false)
                }
                if (selectedCountry == getString(R.string.hint_select_country)) {
                    tvCountryError.text = getString(R.string.hint_select_country)
                    tvCountryError.visible(true)
                    return@setOnClickListener
                } else {
                    tvCountryError.visible(false)
                }
                if (password.isNullOrEmpty()) {
                    tvPasswordError.visible(true)
                    return@setOnClickListener
                } else {
                    tvPasswordError.visible(false)
                }
                if (!password.matches(passwordPattern)) {
                    tvPasswordError.text = getString(R.string.msg_password_invalid)
                    tvPasswordError.visible(true)
                    return@setOnClickListener
                } else {
                    tvPasswordError.visible(false)
                }
                if (etConfirmPassword.text.isNullOrEmpty()) {
                    tvConfirmPasswordError.visible(true)
                    return@setOnClickListener
                } else {
                    tvConfirmPasswordError.visible(false)
                }
                if (etConfirmPassword.text.toString().trim() != password) {
                    tvConfirmPasswordError.text = getString(R.string.msg_confirm_password_invalid)
                    return@setOnClickListener
                } else {
                    tvConfirmPasswordError.visible(false)
                }

                viewModel.saveCurrentUser(
                    AppUser(
                        userName = userName,
                        userPassword = password,
                        country = selectedCountry
                    )
                )
                viewModel.getCurrentUser()
                findNavController().navigate(R.id.action_signUpFragment_to_loginFragment)
            }

            countrySpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parentView: AdapterView<*>?,
                    selectedItemView: View?,
                    position: Int,
                    id: Long
                ) {
                    if(position != 0){
                        tvCountryError.visible(false)
                    }
                    selectedCountry = countryList[position]
                }

                override fun onNothingSelected(parentView: AdapterView<*>?) {}
            }

            tvLogin.setOnClickListener {
                findNavController().navigate(R.id.action_signUpFragment_to_loginFragment)
            }
        }
    }

    private fun setupObservers() {
        viewModel.currentUser.observe(viewLifecycleOwner, Observer {
            if (it) {
                Toaster.show(requireContext(), R.string.msg_signup_success)
            } else {
                Toaster.show(requireContext(), R.string.msg_error)
            }
        })

        viewModel.countriesData.observe(viewLifecycleOwner, EventObserver { it ->
            countryList.add(0, getString(R.string.hint_select_country))
            countryList.addAll(it.map { it.country })
            setupCountrySpinner(countryList)
        })
    }

    override fun onResume() {
        super.onResume()
        viewModel.getCountryList()
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

    private fun toggleConfirmPasswordVisibility() {
        if (binding.etConfirmPassword.transformationMethod == null) {
            binding.etConfirmPassword.transformationMethod =
                PasswordTransformationMethod.getInstance()
            binding.ivShowHideConfirmPassword.setImageResource(R.drawable.ic_close_eye)
        } else {
            binding.etConfirmPassword.transformationMethod = null
            binding.ivShowHideConfirmPassword.setImageResource(R.drawable.ic_open_eye)
        }
    }

    private fun setupCountrySpinner(countryList: List<String>) {
        val adapter =
            ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, countryList)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.countrySpinner.adapter = adapter
    }
}