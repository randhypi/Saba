package com.capstone.saba.ui.home

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.bumptech.glide.Glide
import com.capstone.saba.MyApplication
import com.capstone.saba.R
import com.capstone.saba.databinding.FragmentHomeBinding
import com.capstone.saba.ui.chatbot.ChatbotFragment
import com.capstone.saba.ui.myaccount.MyAccountFragment
import com.capstone.saba.vm.ViewModelFactory
import javax.inject.Inject


class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    companion object {
        val TAG = HomeFragment::class.java.simpleName
    }

    @Inject
    lateinit var factory: ViewModelFactory

    private val homeViewModel: HomeViewModel by viewModels { factory }

    @SuppressLint("SetTextI18n")
    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().application as MyApplication).appComponent.inject(this)


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }



    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as AppCompatActivity?)!!.supportActionBar!!.hide()
        activity?.let {
            val childFragment: Fragment = ChatbotFragment()
            val transaction = childFragmentManager.beginTransaction()
            transaction.replace(R.id.parent_fragment_container, childFragment).commit()
        }

        if (getValueAuth() == true){
            homeViewModel.getData().observe(viewLifecycleOwner, { data ->
                Log.d(TAG, "$data luar")
                Log.d(TAG, data.toString())
                binding.tvGreetings.text = "Hi,\n${data?.name?.split(" ")?.get(0)}"

               homeViewModel.getAva(data.id).observe(viewLifecycleOwner,{
                    Log.d(MyAccountFragment.TAG,it)
                    Glide
                        .with(this)
                        .load(it)
                        .centerCrop()
                        .placeholder(R.drawable.ic_downloading)
                        .into(binding.imageView2)
                })


                binding.imageView2.setOnClickListener {
                    view.findNavController().navigate(R.id.action_homeFragment_to_myAccountFragment)
                }
            })
        }else if(getValueAuth() == false){
            view.findNavController().navigate(R.id.action_homeFragment_to_loginFragment)
        }


    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


    fun getValueAuth(): Boolean? {
        val sharedPreferences =
            activity?.getSharedPreferences("auth", AppCompatActivity.MODE_PRIVATE)
        return sharedPreferences?.getBoolean("value",false)
    }

}