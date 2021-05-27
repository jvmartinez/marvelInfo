package com.jvmartinez.marvelinfo.ui.signUp

import com.jvmartinez.marvelinfo.R
import com.jvmartinez.marvelinfo.ui.base.BaseActivity
import com.jvmartinez.marvelinfo.ui.login.LoginActivity
import com.jvmartinez.marvelinfo.utils.MarvelInfoUtils
import kotlinx.android.synthetic.main.custom_sign_up.*
import org.koin.android.viewmodel.ext.android.viewModel

class SignUpActivity : BaseActivity() {
    private val signUpViewModel: SignUpViewModel by viewModel<SignUpViewModel>()

    override fun layoutId(): Int = R.layout.activity_sign_up

    override fun onSetup() {
        onClick()
    }

    private fun onClick() {
        btnCreateUser.setOnClickListener {
            signUpViewModel.signUp(
                txtName.text.toString(),
                txtEmail.text.toString(),
                txtPassword.text.toString()
            ).observe(::getLifecycle, ::flowSignUp)
        }
    }

    private fun flowSignUp(b: Boolean?) {
        if (b == true) {
            MarvelInfoUtils.callActivity(this, LoginActivity::class.java)
        } else {
            showMessage(
                getString(R.string.title_notification),
                getString(R.string.message_error_generic)
            )
        }
    }
}