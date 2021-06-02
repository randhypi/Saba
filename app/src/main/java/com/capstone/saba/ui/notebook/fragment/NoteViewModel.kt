package com.capstone.saba.ui.notebook.fragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.LiveDataReactiveStreams
import androidx.lifecycle.ViewModel
import com.capstone.saba.domain.model.Todo
import com.capstone.saba.domain.usecase.UserUseCase
import javax.inject.Inject

class NoteViewModel @Inject constructor(private val userUseCase: UserUseCase) : ViewModel() {


    fun getNoteTodo(): LiveData<Todo> =
        LiveDataReactiveStreams.fromPublisher(userUseCase.getTodo())


}
