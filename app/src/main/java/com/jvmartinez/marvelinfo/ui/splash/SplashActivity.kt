package com.jvmartinez.marvelinfo.ui.splash

import android.content.Intent
import com.jvmartinez.marvelinfo.R
import com.jvmartinez.marvelinfo.ui.base.BaseActivity
import com.jvmartinez.marvelinfo.ui.home.HomeActivity
import org.koin.android.viewmodel.ext.android.viewModel

class SplashActivity : BaseActivity() {
    private val splashViewModel by viewModel<SplashViewModel>()

    override fun layoutId() = R.layout.splash_main

    override fun onSetup() {
        splashViewModel.processValidateFlow()
    }

    override fun onObserver() {
        splashViewModel.check.observe(::getLifecycle, ::flowValidate)
    }

    private fun flowValidate(splashEnum: SplashEnum?) {
        when(splashEnum) {
            SplashEnum.SUCCESS -> {
                val intent = Intent(this, HomeActivity::class.java)
                startActivity(intent)
            }
            SplashEnum.ERROR_GENERIC ->  {
                showMessage(getString(R.string.title_notification), getString(R.string.message_error_generic))
            }
        }
    }
}