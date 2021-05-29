package com.capstone.saba.domain.usecase


import com.capstone.saba.domain.model.User
import com.capstone.saba.domain.repository.IUserRepository
import io.reactivex.Flowable

class UserInteractor constructor(private val userRepository: IUserRepository): UserUseCase {
    override fun signUpWithEmail(email: String, password: String) =
        userRepository.signUpWithEmail(email,password)


    override fun signInWithEmail(email: String, password: String): Flowable<User> =
        userRepository.signInWithEmail(email,password)

    override fun signOut() = userRepository.signOut()
}