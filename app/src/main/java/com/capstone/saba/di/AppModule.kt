package com.capstone.saba.di

import com.capstone.saba.domain.usecase.UserInteractor
import com.capstone.saba.domain.usecase.UserUseCase
import dagger.Binds
import dagger.Module

@Module
abstract class AppModule {

    @Binds
    abstract fun provideUserUseCase(userInteractor: UserInteractor): UserUseCase

}