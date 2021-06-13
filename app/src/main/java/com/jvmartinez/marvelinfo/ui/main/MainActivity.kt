package com.jvmartinez.marvelinfo.ui.main

import androidx.viewbinding.ViewBinding
import com.jvmartinez.marvelinfo.databinding.ActivityMainBinding
import com.jvmartinez.marvelinfo.ui.base.BaseActivity

class MainActivity : BaseActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun layoutId(): ViewBinding {
        binding = ActivityMainBinding.inflate(layoutInflater)
        return binding
    }

    override fun onSetup() {

    }
}