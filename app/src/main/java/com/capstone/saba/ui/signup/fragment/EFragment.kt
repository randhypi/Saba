package com.capstone.saba.ui.signup.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.navigation.findNavController
import com.capstone.saba.R
import com.capstone.saba.databinding.FragmentDBinding
import com.capstone.saba.databinding.FragmentEBinding
import com.capstone.saba.ui.login.LoginFragment


class EFragment : Fragment() {

    private var _binding: FragmentEBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentEBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnNextE.setOnClickListener {
            view.findNavController().navigate(R.id.action_EFragment_to_LoginFragment)
        }
    }

}