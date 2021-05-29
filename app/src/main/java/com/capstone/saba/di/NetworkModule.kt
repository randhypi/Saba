package com.capstone.saba.di

import com.capstone.saba.data.source.remote.RemoteDataSource
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides


@Module
class NetworkModule {
    @Provides
    fun providesFirebaseStore() : FirebaseFirestore = FirebaseFirestore.getInstance()

    @Provides
    fun providesFirebaseAuth() : FirebaseAuth = FirebaseAuth.getInstance()


}