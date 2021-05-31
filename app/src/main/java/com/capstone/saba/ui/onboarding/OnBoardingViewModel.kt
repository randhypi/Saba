package com.capstone.saba.ui.onboarding

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.capstone.saba.utils.DummyOnBoarding
import javax.inject.Inject


class OnBoardingViewModel @Inject constructor() : ViewModel(){
        fun getOnBoarding(): LiveData<List<OnBoardingModel>> = MutableLiveData(DummyOnBoarding.onBoardingDummy())
}