package com.capstone.saba.utils

import com.capstone.saba.R
import com.capstone.saba.ui.onboarding.OnBoardingModel

object DummyOnBoarding {

    fun onBoardingDummy(): List<OnBoardingModel> {
            val onBoarding = ArrayList<OnBoardingModel>()

        onBoarding.add(
            OnBoardingModel(
                R.drawable.ic_icon1,
                "can be your diary",
                R.drawable.ic_button_next
            )
        )


        onBoarding.add(
            OnBoardingModel(
                R.drawable.ic_icon2,
                "set your reminder\n" +
                        " from your homework",
                R.drawable.ic_button_next
            )
        )

        onBoarding.add(
            OnBoardingModel(
                R.drawable.icontthree,
                "ask anything \n" +
                        "to add to your insight",
                R.drawable.ic_button_finish_onboarding
            )
        )

        return onBoarding
    }

}