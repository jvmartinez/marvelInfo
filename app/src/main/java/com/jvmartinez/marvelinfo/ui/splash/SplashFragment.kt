package com.jvmartinez.marvelinfo.ui.splash

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController

import com.jvmartinez.marvelinfo.R
import com.jvmartinez.marvelinfo.databinding.FragmentSplashBinding
import com.jvmartinez.marvelinfo.ui.base.BaseFragment
import com.jvmartinez.marvelinfo.ui.main.MainViewModel
import org.koin.android.viewmodel.ext.android.viewModel

class SplashFragment : BaseFragment() {
    private  var _binder: FragmentSplashBinding? = null
    private val binding: FragmentSplashBinding
        get() = _binder!!
    private val mainViewModel: MainViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binder = FragmentSplashBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onSetup() {
        mainViewModel.processValidateFlow()
        onObserver()
    }

    private fun onObserver() {
        mainViewModel.checkStatus.observe(::getLifecycle, ::flowValidate)
    }

    private fun flowValidate(splashEnum: SplashEnum?) {
        when (splashEnum) {
            SplashEnum.SUCCESS -> {
                findNavController().navigate(R.id.action_splashFragment_to_homeFragment)
            }
            SplashEnum.FAIL -> {
                findNavController().navigate(R.id.action_splashFragment_to_loginFragment)
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