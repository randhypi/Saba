package com.capstone.saba.ui.chatbot

import androidx.lifecycle.LiveData
import androidx.lifecycle.LiveDataReactiveStreams
import androidx.lifecycle.ViewModel
import com.capstone.saba.domain.model.ChatBot
import com.capstone.saba.domain.usecase.UserUseCase
import javax.inject.Inject

class ChatbotViewModel @Inject constructor(private val userUseCase: UserUseCase) : ViewModel() {
    fun getChat(): LiveData<List<ChatBot>> =  LiveDataReactiveStreams.fromPublisher(userUseCase.getChat())
    fun sentChat(message: String) : LiveData<Boolean> =
        LiveDataReactiveStreams.fromPublisher(userUseCase.sentChat(message))

}