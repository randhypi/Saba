package com.capstone.saba.ui.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.capstone.saba.R
import com.capstone.saba.databinding.ActivityLoginBinding
import com.capstone.saba.databinding.ActivityOnBoardingBinding
import com.capstone.saba.ui.signup.DaftarActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginActivity : AppCompatActivity() {
        private lateinit var binding: ActivityLoginBinding
        private lateinit var auth: FirebaseAuth

        companion object{
        val TAG = LoginActivity::class.java.simpleName
        }

        override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        auth = Firebase.auth

        supportActionBar?.hide()



        binding.btnLogin.setOnClickListener{
                val email = binding.inputEmail.text?.toString()?.trim()
                val password = binding.inputPassword.text?.toString()?.trim()
            Log.d(TAG,"${email} dan $password")

        }

        binding.btnDaftar.setOnClickListener{
/*            auth.signOut()
            val user = auth.currentUser
            Log.d(TAG,"Berhasil Log out")
            Toast.makeText(baseContext, user?.email.toString(),
                Toast.LENGTH_SHORT).show()*/

            intent = Intent(this, DaftarActivity::class.java)
            startActivity(intent)
        }

    }
}