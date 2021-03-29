package com.jvmartinez.marvelinfo

import android.app.Application
import com.google.firebase.FirebaseApp
import com.jvmartinez.marvelinfo.core.di.repositoryModule
import com.jvmartinez.marvelinfo.core.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            FirebaseApp.initializeApp(this@App)
            androidContext(this@App)
            modules(
                listOf(
                    viewModelModule,
                    repositoryModule
                )
            )
        }
    }
}