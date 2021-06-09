package com.capstone.saba.ui.notebook.fragment.todo

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.capstone.saba.MyApplication
import com.capstone.saba.R
import com.capstone.saba.data.source.remote.RemoteDataSource
import com.capstone.saba.databinding.FragmentCreateBinding
import com.capstone.saba.databinding.FragmentEditTodoBinding
import com.capstone.saba.vm.ViewModelFactory
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import javax.inject.Inject


class CreateFragment : Fragment() {

    private var _binding: FragmentCreateBinding? = null
    private val binding get() = _binding!!

    companion object {
        val TAG = RemoteDataSource::class.java.simpleName
    }

    @Inject
    lateinit var factory: ViewModelFactory

    private val userViewModel: ToDoViewModel by viewModels { factory }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().application as MyApplication).appComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentCreateBinding.inflate(inflater, container, false)
        val view = binding.root

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        userViewModel.getUser().observe(viewLifecycleOwner, ) { data ->

            binding.btnCreate.setOnClickListener{

                val db = Firebase.firestore

                val getDes = binding.edtDeskripsiCreate.text.toString()

                val user = hashMapOf(
                    "todo" to "$getDes"
                )

                db.collection("users").document(data.id).collection("todo")
                    .add(user)
                    .addOnSuccessListener { documentReference ->
                        Log.d(EditTodoFragment.TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
                    }
                    .addOnFailureListener { e ->
                        Log.w(EditTodoFragment.TAG, "Error adding document", e)
                    }

            }


        }

    }
}