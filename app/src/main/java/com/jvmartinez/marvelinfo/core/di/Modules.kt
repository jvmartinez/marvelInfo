package com.jvmartinez.marvelinfo.core.di

import com.jvmartinez.marvelinfo.core.data.remote.repository.RepositoryMarvel
import com.jvmartinez.marvelinfo.ui.home.HomeViewModel
import com.jvmartinez.marvelinfo.ui.splash.SplashViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

/**
 * Inject the view model
 */
val viewModelModule = module {
    viewModel { SplashViewModel() }
    viewModel { HomeViewModel(get()) }
}

/**
 * Inject repository
 */
val repositoryModule =  module {
    single {
        RepositoryMarvel(get())
    }
}