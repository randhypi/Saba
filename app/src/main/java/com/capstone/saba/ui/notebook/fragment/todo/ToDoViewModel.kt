package com.capstone.saba.ui.notebook.fragment.todo

import androidx.lifecycle.LiveDataReactiveStreams
import androidx.lifecycle.ViewModel
import com.capstone.saba.domain.usecase.UserUseCase
import javax.inject.Inject

class ToDoViewModel @Inject constructor(private val userUseCase: UserUseCase) : ViewModel() {


    fun getNoteTodo() =
        LiveDataReactiveStreams.fromPublisher(userUseCase.getTodo())


}
