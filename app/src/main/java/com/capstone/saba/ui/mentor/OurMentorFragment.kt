package com.capstone.saba.ui.mentor

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.capstone.saba.R
import com.capstone.saba.databinding.FragmentOurMentorBinding


class OurMentorFragment : Fragment() {


    private var _binding: FragmentOurMentorBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentOurMentorBinding.inflate(inflater, container, false)
        return binding.root

    }




    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        binding.myToolbar.setNavigationIcon(R.drawable.ic_arrow_back_24)
        binding.myToolbar.setNavigationOnClickListener {
            view.findNavController().popBackStack()
        }
    }

}