package com.capstone.saba.ui.onboarding

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.capstone.saba.utils.DummyOnBoarding


class OnBoardingViewModel : ViewModel() {

val dataOnBoarding = MutableLiveData<OnBoardingModel>()

    fun getOnBoarding(): LiveData<List<OnBoardingModel>> = MutableLiveData(DummyOnBoarding.onBoardingDummy())
}