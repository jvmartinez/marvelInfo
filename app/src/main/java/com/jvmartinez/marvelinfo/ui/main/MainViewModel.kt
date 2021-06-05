package com.jvmartinez.marvelinfo.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jvmartinez.marvelinfo.core.data.local.Preferences
import com.jvmartinez.marvelinfo.ui.splash.SplashEnum
import java.util.*

class MainViewModel(private val preferences: Preferences): ViewModel() {

    private var status: MutableLiveData<SplashEnum> = MutableLiveData()
    val checkStatus: LiveData<SplashEnum>
        get() = status

    fun processValidateFlow() {
        val task: TimerTask = object : TimerTask() {
            override fun run() {
                if (preferences.getIsLogin()) {
                    status.postValue(SplashEnum.SUCCESS)
                } else {
                    status.postValue(SplashEnum.FAIL)
                }
            }
        }
        val timer = Timer()
        timer.schedule(task, 3500)
    }
}