package com.capstone.saba.ui.splashscreen

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.capstone.saba.MainActivity
import com.capstone.saba.R
import com.capstone.saba.ui.onboarding.OnBoardingActivity

@Suppress("DEPRECATION")
class SplashscreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splashscreen)

        supportActionBar?.hide()
        Handler().postDelayed({

            when(getValueOnBoard()){
                false->{
                    val intent = Intent(this, OnBoardingActivity::class.java)
                    startActivity(intent)
                    finish()
                }
                true->{
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            }



        }, 3000)
    }


 fun getValueOnBoard(): Boolean{
     val sharedPreferences = getSharedPreferences("onboarding", MODE_PRIVATE)
     return sharedPreferences.getBoolean("value", false)
 }


}