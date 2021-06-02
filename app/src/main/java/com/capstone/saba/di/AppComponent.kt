package com.capstone.saba.di

import com.capstone.saba.ui.home.HomeFragment
import com.capstone.saba.ui.login.LoginFragment
import com.capstone.saba.ui.myaccount.MyAccountFragment
import com.capstone.saba.ui.notebook.fragment.NoteFragment
import com.capstone.saba.ui.notebook.fragment.ToDoFragment
import com.capstone.saba.ui.onboarding.OnBoardingActivity
import com.capstone.saba.ui.signup.fragment.*
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
    fun inject(fragment: AFragment)
    fun inject(fragment: BFragment)
    fun inject(fragment: CFragment)
    fun inject(fragment: DFragment)
    fun inject(fragment: EFragment)
    fun inject(fragment: NoteFragment)
    fun inject(fragment: ToDoFragment)
}