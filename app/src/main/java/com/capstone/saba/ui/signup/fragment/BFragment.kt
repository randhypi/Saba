package com.capstone.saba.ui.signup.fragment

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import com.capstone.saba.MyApplication
import com.capstone.saba.R
import com.capstone.saba.databinding.FragmentBBinding
import com.capstone.saba.ui.signup.SignUpViewModel
import com.capstone.saba.vm.ViewModelFactory
import com.jakewharton.rxbinding2.widget.RxTextView
import io.reactivex.Observable
import javax.inject.Inject


class BFragment : Fragment() {

    private var _binding: FragmentBBinding? = null
    private val binding get() = _binding!!


    companion object {
        val TAG = BFragment::class.java.simpleName
    }

    @Inject
    lateinit var factory: ViewModelFactory

    private val signUpViewModel: SignUpViewModel by activityViewModels { factory }


    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().application as MyApplication).appComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        formValidity()

        binding.btnNextB.isEnabled = false
        binding.imgButtonBackB.setOnClickListener {
            view.findNavController().popBackStack()
        }
        binding.btnNextB.setOnClickListener {
            val name = binding.inputNama.text
            val email = binding.inputEmailSignUp.text
            val password = binding.inputPasswordSignup.text

            Log.d(TAG, "Data $name , $email , $password")

            signUpViewModel.setDataUser(
                name = name.toString(),
                email = email.toString(),
                password = password.toString()
            )

            signUpViewModel.getDataUser().observe(viewLifecycleOwner,{data->
                Log.d(TAG, "$data vm")
            })

            view.findNavController()
                .navigate(R.id.action_BFragment_to_CFragment)
        }
    }


    @SuppressLint("CheckResult")
    fun formValidity() {

        val nameStream = RxTextView.textChanges(binding.inputNama)
            .skipInitialValue()
            .map { name ->
                name.length < 1
            }.doOnNext {
                Log.d(TAG, "Name = $it")
            }
        nameStream.subscribe {
            showNameExistAlert(it)
        }


        val emailStream = RxTextView.textChanges(binding.inputEmailSignUp)
            .skipInitialValue()
            .map { email ->
                !Patterns.EMAIL_ADDRESS.matcher(email).matches()
            }.doOnNext {
                Log.d(TAG, "Email = $it")
            }
        emailStream.subscribe {
            showEmailExistAlert(it)
        }


        val passwordStream = RxTextView.textChanges(binding.inputPasswordSignup)
            .skipInitialValue()
            .map { password ->
                password.length < 8
            }
        passwordStream.subscribe {
            showPasswordMinimalAlert(it)
        }


        val passwordConfirmationStream = Observable.merge(
            RxTextView.textChanges(binding.inputPasswordSignup)
                .skipInitialValue()
                .map { password ->
                    password.toString() != binding.inputPasswordSignup2.text.toString()
                },
            RxTextView.textChanges(binding.inputPasswordSignup2)
                .skipInitialValue()
                .map { confirmPassword ->
                    confirmPassword.toString() != binding.inputPasswordSignup.text.toString()
                }
        )

        passwordConfirmationStream.subscribe {
            showPasswordConfirmationAlert(it)
        }


        val invalidFieldsStream = Observable.combineLatest(
            nameStream,
            emailStream,
            passwordStream,
            passwordConfirmationStream,
            { n, e, p, pc ->
                !n && !e && !p && !pc
            }).doOnNext {
            Log.d(TAG, "field stream $it")
        }

        invalidFieldsStream.subscribe { valid ->
            enabledBtnNext(valid)
        }

    }



    private fun enabledBtnNext(valid: Boolean) {
        if (valid){
            Log.d(TAG,"valid a = $valid")
            binding.btnNextB.isEnabled = true
            binding.btnNextB.backgroundTintList =
                activity?.applicationContext?.let { AppCompatResources.getColorStateList(it,R.color.blue_700) }
        }
        else {
            Log.d(TAG,"valid b = $valid")
            binding.btnNextB.isEnabled = false
            binding.btnNextB.backgroundTintList =
                activity?.applicationContext?.let { AppCompatResources.getColorStateList(it,R.color.browser_actions_bg_grey) }
        }
    }

    private fun showNameExistAlert(isNotValid: Boolean) {
        binding.tilNama.error =
            if (isNotValid) getString(R.string.nama_tidak_boleh_kosong) else null

    }

    private fun showPasswordMinimalAlert(isNotValid: Boolean) {
        Log.d(TAG, "$isNotValid password minimal")
        binding.tilPasswordSignUp.error =
            if (isNotValid) getString(R.string.password_8) else null

        binding.tilPasswordSignUp.errorIconDrawable = null
    }

    private fun showEmailExistAlert(isNotValid: Boolean) {
        binding.tilEmailSignUp.error =
            if (isNotValid) getString(R.string.email_not_valid) else null
    }

    private fun showPasswordConfirmationAlert(isNotValid: Boolean) {
        binding.tilPasswordSignUp2.error =
            if (isNotValid) getString(R.string.password_not_same) else null

        binding.tilPasswordSignUp2.errorIconDrawable = null
    }


}



