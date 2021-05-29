package com.capstone.saba.ui.login

import androidx.lifecycle.LiveDataReactiveStreams
import androidx.lifecycle.ViewModel
import com.capstone.saba.domain.usecase.UserUseCase
import javax.inject.Inject

class SignInViewModel  @Inject constructor(private val userUseCase: UserUseCase) : ViewModel() {

    fun signIn(email: String, password: String) =
        LiveDataReactiveStreams.fromPublisher(userUseCase.signInWithEmail(email, password))

}
