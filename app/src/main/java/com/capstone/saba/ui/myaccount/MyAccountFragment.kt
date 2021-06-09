package com.capstone.saba.ui.myaccount

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.bumptech.glide.Glide
import com.capstone.saba.MyApplication
import com.capstone.saba.R
import com.capstone.saba.databinding.FragmentMyAccountBinding
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

        myAccountViewModel.getData().observe(viewLifecycleOwner,{data->
            Glide
                .with(this)
                .load(data?.urlAva)
                .centerCrop()
                .placeholder(R.drawable.ic_downloading)
                .into(binding.circleImageView)

            binding.tvNamaLengkap.text = data.name
            binding.tvEmail.text = data.email
            binding.tvTtl.text = data.birthOfDate

        })


        binding.btnSignout.setOnClickListener{
            myAccountViewModel.signOut()
            view.findNavController().navigate(R.id.action_myAccountFragment_to_loginFragment)
        }
    }

   
}