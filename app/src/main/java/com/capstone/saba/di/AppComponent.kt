package com.capstone.saba.di

import com.capstone.saba.ui.home.HomeFragment
import com.capstone.saba.ui.login.LoginFragment
import com.capstone.saba.ui.myaccount.MyAccountFragment
import com.capstone.saba.ui.onboarding.OnBoardingActivity
import dagger.Component

@AppScope
@Component(
    dependencies = [CoreComponent::class],
    modules = [AppModule::class, ViewModelModule::class]
)
interface AppComponent {
    @Component.Factory
    interface Factory {
        fun create(coreComponent: CoreComponent): AppComponent
    }

    fun inject(fragment: HomeFragment)
    fun inject(fragment: LoginFragment)
    fun inject(activity: OnBoardingActivity)
    fun inject(fragment: MyAccountFragment)
}