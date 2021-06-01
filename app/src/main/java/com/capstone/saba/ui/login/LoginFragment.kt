package com.capstone.saba.ui.login

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.capstone.saba.MyApplication
import com.capstone.saba.R
import com.capstone.saba.databinding.FragmentLoginBinding
import com.capstone.saba.vm.ViewModelFactory
import com.jakewharton.rxbinding2.widget.RxTextView
import javax.inject.Inject


class LoginFragment : Fragment() {


    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    companion object {
        val TAG = LoginFragment::class.java.simpleName
    }

    @Inject
    lateinit var factory: ViewModelFactory

    private val loginViewModel: SignInViewModel by viewModels { factory }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    @SuppressLint("CheckResult")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (requireActivity().applicationContext as MyApplication).appComponent.inject(this)

        val emailStream = RxTextView.textChanges(binding.inputEmail)
            .skipInitialValue()
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

        (activity as AppCompatActivity?)!!.supportActionBar!!.hide()

        binding.btnLogin.setOnClickListener {
            val email = binding.inputEmail.text?.toString()?.trim()
            val password = binding.inputPassword.text?.toString()?.trim()
            Log.d(TAG, "${email} dan $password")

            if (showFieldIfNull().equals(false)) {
                loginViewModel.signIn(email.toString(), password.toString())
                    .observe(viewLifecycleOwner, { value ->
                        if (value) view.findNavController()
                            .navigate(R.id.action_loginFragment_to_HomeFragment)
                        else
                            Toast.makeText(
                                context,
                                "Mungkin email atau password anda salah",
                                Toast.LENGTH_LONG).show()
                    })
            }
        }



        binding.btnDaftar.setOnClickListener{
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
