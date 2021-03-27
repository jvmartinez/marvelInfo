package com.jvmartinez.marvelinfo.ui.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.util.*

class SplashViewModel : ViewModel() {
    private var splashStatus: MutableLiveData<SplashEnum> = MutableLiveData()
    val check: LiveData<SplashEnum>
        get() = splashStatus

    fun processValidateFlow() {
        val task: TimerTask = object : TimerTask() {
            override fun run() {
                splashStatus.postValue(SplashEnum.SUCCESS)
            }
        }
        val timer = Timer()
        timer.schedule(task, 3500)
    }
}