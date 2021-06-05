package com.jvmartinez.marvelinfo.ui.signUp

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.jvmartinez.marvelinfo.R
import com.jvmartinez.marvelinfo.databinding.FragmentSignUpBinding
import com.jvmartinez.marvelinfo.ui.base.BaseFragment
import org.koin.android.viewmodel.ext.android.viewModel


class SignUpFragment : BaseFragment() {
    private var _binding: FragmentSignUpBinding? = null
    private val binding: FragmentSignUpBinding
        get() = _binding!!
    private val signUpViewModel: SignUpViewModel by viewModel<SignUpViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSignUpBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onSetup() {
        onClick()
    }

    private fun onClick() {
        binding.customSignUp.btnCreateUser.setOnClickListener {
            signUpViewModel.signUp(
                binding.customSignUp.txtName.text.toString(),
                binding.customSignUp.txtEmail.text.toString(),
                binding.customSignUp.txtPassword.text.toString()
            ).observe(::getLifecycle, ::flowSignUp)
        }
    }

    private fun flowSignUp(b: Boolean?) {
        if (b == true) {
            val positiveButton = { _: DialogInterface, _: Int ->
                findNavController().navigate(R.id.action_signUpFragment_to_loginFragment)
            }
            showMessage(getString(R.string.title_notification), getString(R.string.message_sign_up_success), positiveButton)
        } else {
            showMessage(
                getString(R.string.title_notification),
                getString(R.string.message_error_generic)
            )
        }
    }
}