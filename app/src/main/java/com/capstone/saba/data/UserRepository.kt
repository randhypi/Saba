package com.capstone.saba.data

import android.annotation.SuppressLint
import android.util.Log
import com.capstone.saba.data.source.remote.RemoteDataSource
import com.capstone.saba.domain.model.User
import com.capstone.saba.domain.repository.IUserRepository
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class UserRepository @Inject constructor(private val remoteDataSource: RemoteDataSource): IUserRepository {
    override fun signUpWithEmail(email: String, password: String) {
        remoteDataSource.signUpWithEmail(email,password)
    }


    override fun signInWithEmail(email: String, password: String) =
        remoteDataSource.signInWithEmail(email,password)


    @SuppressLint("CheckResult")
    override fun getDataUser():Flowable<User> {

        val result = PublishSubject.create<User>()

        remoteDataSource.getDataUser()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                result.onNext(it)
            }, { error ->
                Log.e("RemoteDataSource", error.toString())
            })


        return result.toFlowable(BackpressureStrategy.BUFFER)

    }


    override fun signOut() = remoteDataSource.signOut()

}