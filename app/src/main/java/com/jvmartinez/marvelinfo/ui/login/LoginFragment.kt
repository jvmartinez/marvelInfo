package com.jvmartinez.marvelinfo.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.jvmartinez.marvelinfo.R
import com.jvmartinez.marvelinfo.core.data.remote.apiMarvel.ApiResource
import com.jvmartinez.marvelinfo.databinding.FragmentLoginBinding
import com.jvmartinez.marvelinfo.ui.base.BaseFragment
import org.koin.android.viewmodel.ext.android.viewModel

class LoginFragment : BaseFragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding: FragmentLoginBinding
        get() = _binding!!
    private val loginViewModel: LoginViewModel by viewModel<LoginViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onSetup() {
        onClick()
    }

    private fun onClick() {
        binding.customLogin.btnLogin.setOnClickListener {
            loginViewModel.login(
                binding.customLogin.txtEmail.text.toString(),
                binding.customLogin.txtPassword.text.toString()
            ).observe(::getLifecycle, ::flowLogin)
        }

        binding.customLogin.btnSignUp.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_signUpFragment)
        }
    }

    private fun flowLogin(apiResource: ApiResource<LoginEnum>?) {
        when (apiResource) {
            is ApiResource.Failure -> {
                showMessage(
                    getString(R.string.title_notification),
                    apiResource.message
                )
            }
            is ApiResource.Success -> {
                findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
            }
        }
    }
}