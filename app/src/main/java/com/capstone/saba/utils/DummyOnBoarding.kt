package com.capstone.saba.utils

import com.capstone.saba.R
import com.capstone.saba.ui.onboarding.OnBoardingModel

object DummyOnBoarding {

    fun onBoardingDummy(): List<OnBoardingModel> {
            val onBoarding = ArrayList<OnBoardingModel>()

        onBoarding.add(
            OnBoardingModel(
                R.drawable.ic_icon1,
                "dapat menjadi catatan untuk mu",
                R.drawable.ic_button_next
            )
        )


        onBoarding.add(
            OnBoardingModel(
                R.drawable.ic_icon2,
                "Waktu adalah uang",
                R.drawable.ic_button_next
            )
        )

        onBoarding.add(
            OnBoardingModel(
                R.drawable.icontthree,
                "Tanyakan terkait dengan fisika",
                R.drawable.ic_button_finish_onboarding
            )
        )

        return onBoarding
    }

}