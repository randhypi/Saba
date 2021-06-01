package com.capstone.saba.di

import android.content.Context
import com.capstone.saba.data.source.remote.RemoteDataSource
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.ktx.initialize
import dagger.Module
import dagger.Provides


@Module
class NetworkModule {

    @Provides
    fun providesFirebaseStore(context: Context) : FirebaseFirestore {

        val firebaseApp = FirebaseApp.initializeApp(context)
        return FirebaseFirestore.getInstance()
    }



        @Provides
        fun providesFirebaseAuth(context: Context) : FirebaseAuth {

            val firebaseApp = FirebaseApp.initializeApp(context)

            return FirebaseAuth.getInstance()
        }


}