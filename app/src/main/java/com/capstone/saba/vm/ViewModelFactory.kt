package com.capstone.saba.vm

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.capstone.saba.ui.onboarding.OnBoardingViewModel

class ViewModelFactory : ViewModelProvider.NewInstanceFactory() {

    companion object {
        @Volatile
        private var instance: ViewModelFactory? = null

        fun getInstance(context: Context): ViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: ViewModelFactory().apply {
                    instance = this
                }
            }
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        when {
            modelClass.isAssignableFrom(OnBoardingViewModel::class.java) -> {
                return OnBoardingViewModel() as T
            }


            else -> throw Throwable("Unknown ViewModel class: " + modelClass.name)
        }

    }
}