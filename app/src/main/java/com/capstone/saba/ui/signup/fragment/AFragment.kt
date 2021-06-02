package com.capstone.saba.ui.signup.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.capstone.saba.MyApplication
import com.capstone.saba.R
import com.capstone.saba.databinding.FragmentABinding
import com.capstone.saba.databinding.FragmentLoginBinding
import com.capstone.saba.ui.login.LoginFragment
import com.capstone.saba.ui.login.SignInViewModel
import com.capstone.saba.ui.signup.SignUpViewModel
import com.capstone.saba.vm.ViewModelFactory
import javax.inject.Inject


class AFragment : Fragment(){

    private var _binding: FragmentABinding? = null
    private val binding get() = _binding!!


    companion object {
        val TAG = AFragment::class.java.simpleName
    }

    @Inject
    lateinit var factory: ViewModelFactory

    private val signUpViewModel: SignUpViewModel by viewModels { factory }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentABinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (requireActivity().applicationContext as MyApplication).appComponent.inject(this)

        binding.imgButtonBack.setOnClickListener {
            view.findNavController().popBackStack()
        }

        binding.btnNextA.setOnClickListener {
            view.findNavController().navigate(R.id.action_AFragment_to_BFragment)
        }
    }

}
