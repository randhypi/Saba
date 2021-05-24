package com.capstone.saba.ui.login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.capstone.saba.R

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        supportActionBar?.hide()
    }
}