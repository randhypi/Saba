package com.capstone.saba.data

import android.annotation.SuppressLint
import com.capstone.saba.data.source.remote.RemoteDataSource
import com.capstone.saba.domain.model.User
import com.capstone.saba.domain.repository.IUserRepository
import io.reactivex.Flowable
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class UserRepository @Inject constructor(private val remoteDataSource: RemoteDataSource) :
    IUserRepository {


    override fun signUpWithEmail(email: String,
                                 password: String,
                                 name: String,
                                 gender: String,
                                 birthOfDate: String,
                                 urlAva: String): Flowable<Boolean> =
        remoteDataSource.signUpWithEmail(email, password,name,gender,birthOfDate,urlAva)



    override fun signInWithEmail(email: String, password: String) : Flowable<Boolean> =
        remoteDataSource.signInWithEmail(email, password)


    @SuppressLint("CheckResult")
    override fun getDataUser(): Flowable<User> =
        remoteDataSource.getDataUser()



    override fun signOut() = remoteDataSource.signOut()

}