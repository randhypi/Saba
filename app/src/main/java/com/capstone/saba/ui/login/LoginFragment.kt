package com.capstone.saba.ui.login

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.addCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.edit
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.capstone.saba.MyApplication
import com.capstone.saba.R
import com.capstone.saba.databinding.FragmentLoginBinding
import com.capstone.saba.vm.ViewModelFactory
import com.jakewharton.rxbinding2.widget.RxTextView
import javax.inject.Inject
import kotlin.system.exitProcess


class LoginFragment : Fragment() {


    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    companion object {
        val TAG: String = LoginFragment::class.java.simpleName
    }

    @Inject
    lateinit var factory: ViewModelFactory

    private val loginViewModel: SignInViewModel by viewModels { factory }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().application as MyApplication).appComponent.inject(this)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("CheckResult")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (requireActivity().applicationContext as MyApplication).appComponent.inject(this)
        (activity as AppCompatActivity?)!!.supportActionBar!!.hide()
        (activity as AppCompatActivity?)!!.onBackPressedDispatcher.addCallback(this) {
            exitProcess(0)
        }


        val emailStream = RxTextView.textChanges(binding.inputEmail)
            .skip(7)
            .map { email ->
                !Patterns.EMAIL_ADDRESS.matcher(email).matches()
            }

        emailStream.subscribe {
            showEmailExistAlert(it)
        }

        val passwordStream = RxTextView.textChanges(binding.inputPassword)
            .skipInitialValue()
            .map { password ->
                password.isNotEmpty()
            }

        passwordStream.subscribe {
            textPassword(it)
        }



        binding.btnLogin.setOnClickListener {
            val email = binding.inputEmail.text?.toString()?.trim()
            val password = binding.inputPassword.text?.toString()?.trim()
            Log.d(TAG, "${email} dan $password")

            if (showFieldIfNull().equals(false)) {
                loginViewModel.signIn(email.toString(), password.toString())
                    .observe(viewLifecycleOwner, { value ->
                        if (value) {
                            view.findNavController()
                                .navigate(R.id.action_loginFragment_to_HomeFragment)
                            val sharedPreferences = activity?.getSharedPreferences(
                                "auth",
                                AppCompatActivity.MODE_PRIVATE
                            )
                            sharedPreferences?.edit {
                                putBoolean("value", true)
                            }
                        } else {
                            Toast.makeText(
                                context,
                                "Mungkin email atau password anda salah",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    })
            }
        }



        binding.btnDaftar.setOnClickListener {
            view.findNavController()
                .navigate(R.id.action_loginFragment_to_AFragment)
        }


    }


    private fun showEmailExistAlert(isNotValid: Boolean) {
        binding.inputEmail.error = if (isNotValid) getString(R.string.email_not_valid) else null
    }

    private fun textPassword(value: Boolean) {
        binding.tilPassword.isErrorEnabled = !value
        Log.d(TAG, value.toString())
    }


    private fun showFieldIfNull(): Boolean {
        var result = false
        if (binding.inputEmail.text.isNullOrEmpty() && binding.inputPassword.text.isNullOrEmpty()) {
            binding.inputEmail.error = getString(R.string.email_is_null)
            binding.tilPassword.error = getString(R.string.email_is_null)
            binding.tilPassword.error = getString(R.string.password_is_null)
            result = true
        } else if (binding.inputEmail.text.isNullOrEmpty()) {
            binding.inputEmail.error = getString(R.string.email_is_null)
            result = true
        } else if (binding.inputPassword.text.isNullOrEmpty()) {
            binding.tilPassword.error = getString(R.string.password_is_null)
            result = true
        }
        return result
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}
