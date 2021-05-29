package com.capstone.saba.ui.home

import androidx.lifecycle.LiveDataReactiveStreams
import androidx.lifecycle.ViewModel
import com.capstone.saba.domain.usecase.UserUseCase
import javax.inject.Inject

class HomeViewModel @Inject constructor(private val userUseCase: UserUseCase) : ViewModel() {
    fun getData() =  LiveDataReactiveStreams.fromPublisher(userUseCase.getDataUser())

}