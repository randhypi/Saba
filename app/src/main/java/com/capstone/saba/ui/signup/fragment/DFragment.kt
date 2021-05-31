package com.capstone.saba.ui.signup.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.RadioButton
import androidx.navigation.findNavController
import com.capstone.saba.R
import com.capstone.saba.databinding.FragmentCBinding
import com.capstone.saba.databinding.FragmentDBinding

class DFragment : Fragment() {

    private var _binding: FragmentDBinding? = null
    private val binding get() = _binding!!

    companion object{
        val TAG = DFragment::class.java.simpleName
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentDBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnNextD.setOnClickListener {
            view.findNavController().navigate(R.id.action_DFragment_to_EFragment)
            val getId = binding.rgGenderSignUp.checkedRadioButtonId
            val result: RadioButton = view.findViewById(getId)
            Log.d(TAG,result.text.toString())
        }

    }

}