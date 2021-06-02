package com.capstone.saba.domain.usecase


import com.capstone.saba.domain.model.User
import com.capstone.saba.domain.repository.IUserRepository
import io.reactivex.Flowable
import javax.inject.Inject

class UserInteractor @Inject constructor(private val userRepository: IUserRepository): UserUseCase {
    override fun signUpWithEmail(email: String,
                                 password: String,
                                 name: String,
                                 gender: String,
                                 birthOfDate: String,
                                 urlAva: String) =
        userRepository.signUpWithEmail(email,password,name, gender, birthOfDate, urlAva)


    override fun signInWithEmail(email: String, password: String) =
        userRepository.signInWithEmail(email,password)

    override fun getDataUser() = userRepository.getDataUser()




    override fun signOut() = userRepository.signOut()
}