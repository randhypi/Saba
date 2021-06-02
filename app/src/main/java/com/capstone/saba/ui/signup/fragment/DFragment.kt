package com.capstone.saba.ui.signup.fragment

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.capstone.saba.MyApplication
import com.capstone.saba.R
import com.capstone.saba.databinding.FragmentDBinding
import com.capstone.saba.ui.signup.SignUpViewModel

class DFragment : Fragment() {

    private var _binding: FragmentDBinding? = null
    private val binding get() = _binding!!

    companion object{
        val TAG = DFragment::class.java.simpleName
    }



    private lateinit var signUpViewModel: SignUpViewModel


    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().application as MyApplication).appComponent.inject(this)
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

        signUpViewModel = ViewModelProvider(requireActivity()).get(SignUpViewModel::class.java)

        signUpViewModel.getDataUser().observe(viewLifecycleOwner,{data->
            signUpViewModel.getBirthOfDate().observe(viewLifecycleOwner,{birthOfDate->

                Log.d(TAG, " $data \n $birthOfDate")

            })
        })

        signUpViewModel.getBirthOfDate().observe(viewLifecycleOwner,{birthOfDate->
            Log.d(TAG,"$birthOfDate")
        })

        binding.btnNextD.setOnClickListener {
            view.findNavController().navigate(R.id.action_DFragment_to_EFragment)
            val getId = binding.rgGenderSignUp.checkedRadioButtonId
            val result: RadioButton = view.findViewById(getId)
            signUpViewModel.getDataUser().observe(viewLifecycleOwner,{data->
                signUpViewModel.getBirthOfDate().observe(viewLifecycleOwner,{birthOfDate->

                    Log.d(TAG, " $data \n $birthOfDate")
                    signUpViewModel.signUp(
                        email = data.email,
                        name = data.name,
                        password = data.password,
                        birthOfDate = birthOfDate,
                        gender = result.text.toString(),
                        urlAva = "https://firebasestorage.googleapis.com/v0/b/b21-cap0415.appspot.com/o/FamGath_190720_0465.jpg?alt=media&token=c05021b3-f476-4403-a135-3e2ea8085787"
                    ).observe(viewLifecycleOwner,{value->
                        if (value){
                            view.findNavController().navigate(R.id.action_DFragment_to_EFragment)
                        } else{
                            Toast.makeText(context,"Gagal Sign Up\n Periksa data nya kembali",Toast.LENGTH_LONG).show()
                        }

                    })

                })
            })
            signUpViewModel.gender.postValue(result.text.toString())
            Log.d(TAG,result.text.toString())
        }

        binding.imgButtonBackD.setOnClickListener {
            view.findNavController().popBackStack()
        }

    }

}