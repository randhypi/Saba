package com.capstone.saba.ui.myaccount

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.capstone.saba.MyApplication
import com.capstone.saba.R
import com.capstone.saba.databinding.FragmentLoginBinding
import com.capstone.saba.databinding.FragmentMyAccountBinding
import com.capstone.saba.ui.login.LoginFragment
import com.capstone.saba.ui.login.SignInViewModel
import com.capstone.saba.vm.ViewModelFactory
import javax.inject.Inject

class MyAccountFragment : Fragment() {


    private var _binding: FragmentMyAccountBinding? = null
    private val binding get() = _binding!!

    companion object {
        val TAG = MyAccountFragment::class.java.simpleName
    }

    @Inject
    lateinit var factory: ViewModelFactory

    private val myAccountViewModel: MyAccountViewModel by viewModels { factory }


    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().application as MyApplication).appComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentMyAccountBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnSignOut.setOnClickListener{
            myAccountViewModel.signOut()
            view.findNavController().navigate(R.id.action_myAccountFragment_to_loginFragment)
        }
    }

   
}