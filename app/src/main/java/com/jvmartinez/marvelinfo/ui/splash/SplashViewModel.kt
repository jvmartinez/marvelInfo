package com.jvmartinez.marvelinfo.ui.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jvmartinez.marvelinfo.core.data.local.Preferences
import java.util.*

class SplashViewModel(private val preferences: Preferences) : ViewModel() {
    private var splashStatus: MutableLiveData<SplashEnum> = MutableLiveData()
    val check: LiveData<SplashEnum>
        get() = splashStatus

    fun processValidateFlow() {
        val task: TimerTask = object : TimerTask() {
            override fun run() {
                if (preferences.getIsLogin()) {
                    splashStatus.postValue(SplashEnum.SUCCESS)
                } else {
                    splashStatus.postValue(SplashEnum.FAIL)
                }
            }
        }
        val timer = Timer()
        timer.schedule(task, 3500)
    }
}