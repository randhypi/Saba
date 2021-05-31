package com.capstone.saba.ui.myaccount

import androidx.lifecycle.LiveData
import androidx.lifecycle.LiveDataReactiveStreams
import androidx.lifecycle.ViewModel
import com.capstone.saba.domain.model.User
import com.capstone.saba.domain.usecase.UserUseCase
import javax.inject.Inject

class MyAccountViewModel @Inject constructor(private val userUseCase: UserUseCase) : ViewModel() {
    fun getData(): LiveData<User> =  LiveDataReactiveStreams.fromPublisher(userUseCase.getDataUser())
    fun signOut() = userUseCase.signOut()
}