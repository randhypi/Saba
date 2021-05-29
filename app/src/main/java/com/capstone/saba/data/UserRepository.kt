package com.capstone.saba.data

import com.capstone.saba.data.source.remote.RemoteDataSource
import com.capstone.saba.domain.model.User
import com.capstone.saba.domain.repository.IUserRepository
import com.google.firebase.auth.FirebaseUser
import io.reactivex.Flowable

class UserRepository constructor(private val remoteDataSource: RemoteDataSource): IUserRepository {
    override fun signUpWithEmail(email: String, password: String) {
        remoteDataSource.signUpWithEmail(email,password)
    }

    override fun signInWithEmail(email: String, password: String): Flowable<User> =
        remoteDataSource.signInWithEmail(email,password)


    override fun signOut() = remoteDataSource.signOut()

}