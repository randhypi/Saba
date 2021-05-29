package com.capstone.saba.di

import androidx.lifecycle.ViewModel
import com.capstone.saba.ui.home.HomeViewModel
import com.capstone.saba.ui.login.SignInViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Suppress("unused")
@Module
abstract class ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(HomeViewModel::class)
    abstract fun bindHomeViewModel(viewModel: HomeViewModel): ViewModel


    @Binds
    @IntoMap
    @ViewModelKey(SignInViewModel::class)
    abstract fun bindHomeViewModel(viewModel: SignInViewModel): ViewModel



}