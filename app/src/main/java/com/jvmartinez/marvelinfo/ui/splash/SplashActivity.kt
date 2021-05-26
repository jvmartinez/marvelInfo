package com.jvmartinez.marvelinfo.ui.splash

import com.jvmartinez.marvelinfo.R
import com.jvmartinez.marvelinfo.ui.base.BaseActivity
import com.jvmartinez.marvelinfo.ui.home.HomeActivity
import com.jvmartinez.marvelinfo.utils.MarvelInfoUtils
import org.koin.android.viewmodel.ext.android.viewModel

class SplashActivity : BaseActivity() {
    private val splashViewModel by viewModel<SplashViewModel>()

    override fun layoutId() = R.layout.splash_main

    override fun onSetup() {
        onObserver()
        splashViewModel.processValidateFlow()
    }

    fun onObserver() {
        splashViewModel.check.observe(::getLifecycle, ::flowValidate)
    }

    private fun flowValidate(splashEnum: SplashEnum?) {
        when (splashEnum) {
            SplashEnum.SUCCESS -> {
                MarvelInfoUtils.callActivity(this, HomeActivity::class.java)
            }
            SplashEnum.ERROR_GENERIC -> {
                showMessage(
                    getString(R.string.title_notification),
                    getString(R.string.message_error_generic)
                )
            }
        }
    }
}