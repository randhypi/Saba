package com.capstone.saba.ui.login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.capstone.saba.R
import com.capstone.saba.databinding.ActivityLoginBinding
import com.capstone.saba.databinding.ActivityOnBoardingBinding
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
        val email = binding.tilUsername.editText?.text.toString()
        val password = binding.tilPassword.editText?.text.toString()

        binding.btnLogin.setOnClickListener{
            Log.d(TAG,"${email} dan $password")
//            auth.signInWithEmailAndPassword("test@gmail.com","12345678")
//                .addOnCompleteListener(this) { task ->
//                    if (task.isSuccessful) {
//                        // Sign in success, update UI with the signed-in user's information
//                        Log.d(TAG, "signInWithEmail:success")
//                        val user = auth.currentUser
//                        Log.d(TAG,user?.email.toString() )
//                        Toast.makeText(baseContext, user?.email.toString(),
//                            Toast.LENGTH_SHORT).show()
//                    } else {
//                        // If sign in fails, display a message to the user.
//                        Log.w(TAG, "signInWithEmail:failure", task.exception)
//                        Toast.makeText(baseContext, "Authentication failed.",
//                            Toast.LENGTH_SHORT).show()
//                    }
//                }
        }

        binding.btnDaftar.setOnClickListener{
            auth.signOut()
            val user = auth.currentUser
            Log.d(TAG,"Berhasil Log out")
            Toast.makeText(baseContext, user?.email.toString(),
                Toast.LENGTH_SHORT).show()
        }

    }
}