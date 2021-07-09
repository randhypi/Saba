package com.capstone.saba.domain.usecase


import android.net.Uri
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
    override fun uploadAva(uri: Uri,name: String): Flowable<String>  =userRepository.uploadAva(uri,name)

    override fun getChat() = userRepository.getChat()
    override fun getAva(name: String): Flowable<String> = userRepository.getAva(name)

    override fun sentChat(message: String) = userRepository.sentChat(message)


}