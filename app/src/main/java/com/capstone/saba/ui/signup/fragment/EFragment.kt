package com.capstone.saba.ui.signup.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.capstone.saba.R
import com.capstone.saba.databinding.FragmentEBinding


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

        activity?.onBackPressedDispatcher?.addCallback(viewLifecycleOwner,object : OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                Toast.makeText(context,"Kamu tidak bisa kembali\n silahkan tekan tombol finish",Toast.LENGTH_LONG).show()
            }

        })
        binding.btnNextE.setOnClickListener {
            view.findNavController().navigate(R.id.action_EFragment_to_LoginFragment)
        }
    }



}