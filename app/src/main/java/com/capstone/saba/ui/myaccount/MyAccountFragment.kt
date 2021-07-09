package com.capstone.saba.ui.myaccount


import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.edit
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.bumptech.glide.Glide
import com.capstone.saba.MyApplication
import com.capstone.saba.R
import com.capstone.saba.data.source.remote.RemoteDataSource.Companion.ON_PROGRESS
import com.capstone.saba.data.source.remote.RemoteDataSource.Companion.SUCCES_UPLOAD
import com.capstone.saba.databinding.FragmentMyAccountBinding
import com.capstone.saba.vm.ViewModelFactory
import javax.inject.Inject



class MyAccountFragment : Fragment() {


    private var _binding: FragmentMyAccountBinding? = null
    private val binding get() = _binding!!
    private lateinit var resultLauncher:  ActivityResultLauncher<String>
    private lateinit var id: String

    companion object {
        val TAG = MyAccountFragment::class.java.simpleName
        private val PICK_IMAGE_REQUEST = 22
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
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentMyAccountBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.imgButtonBackProfile.setOnClickListener {
            view.findNavController().popBackStack()
        }

        myAccountViewModel.getData().observe(viewLifecycleOwner, { data ->

            id = data.id
            binding.tvNamaLengkap.text = data.name
            binding.tvEmail.text = data.email
            binding.tvTtl.text = data.birthOfDate

            myAccountViewModel.getAva(data.id).observe(viewLifecycleOwner,{
                Log.d(TAG,it)
                Glide
                    .with(this)
                    .load(it)
                    .centerCrop()
                    .placeholder(R.drawable.ic_downloading)
                    .into(binding.circleImageView)
            })
        })

        binding.btnSignout.setOnClickListener {
            myAccountViewModel.signOut()
            val sharedPreferences = activity?.getSharedPreferences(
                "auth",
                AppCompatActivity.MODE_PRIVATE
            )
            sharedPreferences?.edit {
                putBoolean("value", false)
            }
            view.findNavController().navigate(R.id.action_myAccountFragment_to_loginFragment)
        }


        resultLauncher = registerForActivityResult(ActivityResultContracts.GetContent())
        { result: Uri? ->
         result?.let {uris->

             myAccountViewModel.uploadAva(uris,id).observe(viewLifecycleOwner,{
                 if(it.equals(SUCCES_UPLOAD)){
                     binding.tvLoading.visibility = View.GONE
                     binding.circleImageView.setImageURI(uris)
                     Log.d(TAG,"Berhasil")
                 }else if(it.equals(ON_PROGRESS)){
                    binding.tvLoading.visibility = View.VISIBLE
                     Log.d(TAG,"Loading ........")
                 }else{
                     Toast.makeText(context,it.toString(),Toast.LENGTH_LONG).show()
                     Log.d(TAG,"gagal")
                 }
             })
         }
        }

        binding.imgAddImageProfile.setOnClickListener {
            //check runtime permission
            selectImage()
        }
    }


    private fun selectImage() =
        resultLauncher.launch("image/*")


}





