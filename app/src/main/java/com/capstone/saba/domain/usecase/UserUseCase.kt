package com.capstone.saba.domain.usecase

import com.capstone.saba.domain.model.User
import io.reactivex.Flowable

interface UserUseCase {
    fun signUpWithEmail(email: String,password: String)

    fun signInWithEmail(email: String,password: String)

    fun getDataUser(): Flowable<User>

    fun signOut()
}