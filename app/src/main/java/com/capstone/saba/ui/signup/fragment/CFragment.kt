package com.capstone.saba.ui.signup.fragment

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.capstone.saba.MyApplication
import com.capstone.saba.R
import com.capstone.saba.databinding.FragmentCBinding
import com.capstone.saba.ui.signup.SignUpViewModel

class CFragment : Fragment() {

    private var _binding: FragmentCBinding? = null
    private val binding get() = _binding!!


    companion object {
        val TAG = CFragment::class.java.simpleName
    }



    private lateinit var  signUpViewModel: SignUpViewModel


    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().application as MyApplication).appComponent.inject(this)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentCBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        signUpViewModel = ViewModelProvider(requireActivity()).get(SignUpViewModel::class.java)

        val date = binding.datePicker
        var month = date.month + 1
        var day = date.dayOfMonth
        var year = date.year


        binding.imgButtonBackC.setOnClickListener {
            view.findNavController().popBackStack()
        }

        signUpViewModel.getBirthOfDate().observe(viewLifecycleOwner,{birthOfDate->
            val dates = birthOfDate?.split(" ")?.toTypedArray()
            day = dates?.get(0)?.toInt() ?: 1
            month = setMonthToInt(dates?.get(1) ?: "Januari" )
            year = dates?.get(2)?.toInt() ?: 2021
        })




        binding.btnNextC.setOnClickListener {
            Log.d(TAG, "click calender")
            signUpViewModel.birthOfDate.postValue("$day ${setMonth(month)} $year")
            Log.d(TAG, month.toString())
            view.findNavController()
                .navigate(R.id.action_CFragment_to_DFragment)
        }
    }


    fun setMonth(month: Int): String {
        var nameMonth = ""
        when (month + 1) {
            1 -> {
                nameMonth = "Januari"
            }
            2 -> {
                nameMonth = "Februari"
            }
            3 -> {
                nameMonth = "Maret"
            }
            4 -> {
                nameMonth = "April"
            }
            5 -> {
                nameMonth = "Mei"
            }
            6 -> {
                nameMonth = "Juni"
            }
            7 -> nameMonth = "Juli"
            8 -> nameMonth = "Agustus"
            9 -> nameMonth = "September"
            10 -> nameMonth = "Oktober"
            11 -> nameMonth = "November"
            12 -> nameMonth = "Desember"
        }
        return nameMonth
    }

    fun setMonthToInt(month: String): Int {
        var nameMonth = 0
        when (month) {
            "Januari" -> nameMonth = 0
            "Februari" -> nameMonth = 1
            "Maret" -> nameMonth = 2
            "April" -> nameMonth = 3
            "Mei" -> nameMonth = 4
            "Juni" -> nameMonth = 5
            "Juli" -> nameMonth = 6
            "Agustus" -> nameMonth = 7
            "September" -> nameMonth = 8
            "Oktober" -> nameMonth = 9
            "November" -> nameMonth = 10
            "Desember" -> nameMonth = 11
        }
        return nameMonth
    }
}

