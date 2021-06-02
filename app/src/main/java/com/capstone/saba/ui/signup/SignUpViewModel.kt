package com.capstone.saba.ui.signup

import androidx.lifecycle.LiveData
import androidx.lifecycle.LiveDataReactiveStreams
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.capstone.saba.domain.model.User
import com.capstone.saba.domain.usecase.UserUseCase
import javax.inject.Inject

class SignUpViewModel @Inject constructor(private val userUseCase: UserUseCase) : ViewModel() {



    fun signUp(
        email: String,
        password: String,
        name: String,
        gender: String,
        birthOfDate: String,
        urlAva: String
    ): LiveData<Boolean> =
        LiveDataReactiveStreams.fromPublisher(
            userUseCase.signUpWithEmail(
                email, password, name, gender, birthOfDate, urlAva
            )
        )


    val user = MutableLiveData<User>()
    val birthOfDate = MutableLiveData<String>()
    val gender = MutableLiveData<String>()

    fun getBirthOfDate(): LiveData<String> = birthOfDate
    fun getGender():LiveData<String> = gender

    fun setDataUser(
        email: String,
        password: String,
        name: String,
    ) {
        val userData = User(
            email = email,
            password = password,
            name = name
        )
      user.postValue(userData)
    }



    fun getDataUser(): LiveData<User> = user
}