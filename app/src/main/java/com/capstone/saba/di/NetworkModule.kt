package com.capstone.saba.di

import android.content.Context
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import dagger.Module
import dagger.Provides


@Module
class NetworkModule {

    @Provides
    fun providesFirebaseStore(context: Context): FirebaseFirestore {

        val firebaseApp = FirebaseApp.initializeApp(context)
        return FirebaseFirestore.getInstance()
    }


    @Provides
    fun providesFirebaseAuth(context: Context): FirebaseAuth {

        val firebaseApp = FirebaseApp.initializeApp(context)

        return FirebaseAuth.getInstance()
    }


    @Provides
    fun providesFirebaseStorageReference(context: Context): StorageReference {
        val storage = FirebaseStorage.getInstance()
        return storage.reference
    }

}