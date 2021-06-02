package com.capstone.saba.di

import androidx.lifecycle.ViewModel
import com.capstone.saba.ui.home.HomeViewModel
import com.capstone.saba.ui.login.SignInViewModel
import com.capstone.saba.ui.myaccount.MyAccountViewModel
import com.capstone.saba.ui.onboarding.OnBoardingViewModel
import com.capstone.saba.ui.signup.SignUpViewModel
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
    abstract fun bindSignInViewModel(viewModel: SignInViewModel): ViewModel


    @Binds
    @IntoMap
    @ViewModelKey(OnBoardingViewModel::class)
    abstract fun bindOnBoardingViewModel(viewModel: OnBoardingViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(MyAccountViewModel::class)
    abstract fun bindMyAccountViewModel(viewModel: MyAccountViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SignUpViewModel::class)
    abstract fun bindSignUpViewModel(viewModel: SignUpViewModel): ViewModel

}