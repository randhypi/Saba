package com.capstone.saba.di

import com.capstone.saba.data.UserRepository
import com.capstone.saba.domain.repository.IUserRepository
import dagger.Binds
import dagger.Module

@Module(includes = [NetworkModule::class])
abstract class RepositoryModule {

    @Binds
    abstract fun provideRepository(userRepository: UserRepository): IUserRepository

}