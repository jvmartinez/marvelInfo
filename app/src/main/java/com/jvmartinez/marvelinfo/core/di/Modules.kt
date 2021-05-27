package com.jvmartinez.marvelinfo.core.di

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.jvmartinez.marvelinfo.core.data.local.Preferences
import com.jvmartinez.marvelinfo.core.data.remote.firebase.FirebaseManager
import com.jvmartinez.marvelinfo.core.data.remote.repository.RepositoryMarvel
import com.jvmartinez.marvelinfo.ui.detailCharacter.DetailsCharacterViewModel
import com.jvmartinez.marvelinfo.ui.home.HomeViewModel
import com.jvmartinez.marvelinfo.ui.login.LoginViewModel
import com.jvmartinez.marvelinfo.ui.signUp.SignUpViewModel
import com.jvmartinez.marvelinfo.ui.splash.SplashViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

/**
 * Inject the view model
 */
val viewModelModule = module {
    viewModel { SplashViewModel(get()) }
    viewModel { HomeViewModel(get()) }
    viewModel { DetailsCharacterViewModel(get()) }
    viewModel { LoginViewModel(get(), get()) }
    viewModel { SignUpViewModel(get()) }
}

/**
 * Inject repository
 */
val repositoryModule = module {
    single {
        RepositoryMarvel(get())
    }
    single {
        FirebaseManager()
    }
    single {
        Preferences(get(named("com.jvmartinez.marvelinfo")))
    }
    single(named("com.jvmartinez.marvelinfo")) { provideSettingsPreferences(get()) }
}

private fun provideSettingsPreferences(app: Application): SharedPreferences =
    app.getSharedPreferences("com.jvmartinez.marvelinfo.sp", Context.MODE_PRIVATE)