package com.jvmartinez.marvelinfo.ui.login

import com.jvmartinez.marvelinfo.R
import com.jvmartinez.marvelinfo.core.data.remote.apiMarvel.ApiResource
import com.jvmartinez.marvelinfo.ui.base.BaseActivity
import com.jvmartinez.marvelinfo.ui.home.HomeActivity
import com.jvmartinez.marvelinfo.utils.MarvelInfoUtils
import kotlinx.android.synthetic.main.custom_login.*
import org.koin.android.viewmodel.ext.android.viewModel

class LoginActivity : BaseActivity() {
    private val loginViewModel: LoginViewModel by viewModel<LoginViewModel>()

    override fun layoutId(): Int  = R.layout.activity_login

    override fun onSetup() {
        onClick()
    }

    private fun onClick() {
        btnLogin.setOnClickListener {
            loginViewModel.login(txtEmail.text.toString(), txtPassword.text.toString()).observe(::getLifecycle, ::flowLogin)
        }
        btnSignUp.setOnClickListener {

        }
    }

    private fun flowLogin(apiResource: ApiResource<LoginEnum>?) {
        when (apiResource) {
            is ApiResource.Failure -> {
                showMessage(
                    getString(R.string.title_notification),
                    getString(R.string.message_error_generic)
                )
            }
            is ApiResource.Success -> {
                MarvelInfoUtils.callActivity(this, HomeActivity::class.java)
            }
        }
    }
}