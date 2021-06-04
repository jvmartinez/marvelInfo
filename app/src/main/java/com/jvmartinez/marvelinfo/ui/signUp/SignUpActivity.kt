package com.jvmartinez.marvelinfo.ui.signUp

import android.content.DialogInterface
import androidx.viewbinding.ViewBinding
import com.jvmartinez.marvelinfo.R
import com.jvmartinez.marvelinfo.databinding.ActivitySignUpBinding
import com.jvmartinez.marvelinfo.ui.base.BaseActivity
import com.jvmartinez.marvelinfo.ui.login.LoginActivity
import com.jvmartinez.marvelinfo.utils.MarvelInfoUtils

import org.koin.android.viewmodel.ext.android.viewModel

class SignUpActivity : BaseActivity() {
    private lateinit var binding: ActivitySignUpBinding
    private val signUpViewModel: SignUpViewModel by viewModel<SignUpViewModel>()

    override fun layoutId(): ViewBinding {
      binding = ActivitySignUpBinding.inflate(layoutInflater)
      return binding
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
            val positiveButton = { dialog: DialogInterface, _: Int -> dialog
                MarvelInfoUtils.callActivity(this, LoginActivity::class.java)
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