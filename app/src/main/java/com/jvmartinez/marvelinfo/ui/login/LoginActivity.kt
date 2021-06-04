package com.jvmartinez.marvelinfo.ui.login

import androidx.viewbinding.ViewBinding
import com.jvmartinez.marvelinfo.R
import com.jvmartinez.marvelinfo.core.data.remote.apiMarvel.ApiResource
import com.jvmartinez.marvelinfo.databinding.ActivityLoginBinding
import com.jvmartinez.marvelinfo.ui.base.BaseActivity
import com.jvmartinez.marvelinfo.ui.home.HomeActivity
import com.jvmartinez.marvelinfo.ui.signUp.SignUpActivity
import com.jvmartinez.marvelinfo.utils.MarvelInfoUtils
import org.koin.android.viewmodel.ext.android.viewModel

class LoginActivity : BaseActivity() {
    private lateinit var binding: ActivityLoginBinding
    private val loginViewModel: LoginViewModel by viewModel<LoginViewModel>()

    override fun layoutId(): ViewBinding {
        binding = ActivityLoginBinding.inflate(layoutInflater)
        return binding
    }

    override fun onSetup() {
        onClick()
    }

    private fun onClick() {
        binding.customLogin.btnLogin.setOnClickListener {
            loginViewModel.login(binding.customLogin.txtEmail.text.toString(), binding.customLogin.txtPassword.text.toString()).observe(::getLifecycle, ::flowLogin)
        }

        binding.customLogin.btnSignUp.setOnClickListener {
            MarvelInfoUtils.callActivity(this, SignUpActivity::class.java)
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
                MarvelInfoUtils.callActivity(this, HomeActivity::class.java)
            }
        }
    }
}