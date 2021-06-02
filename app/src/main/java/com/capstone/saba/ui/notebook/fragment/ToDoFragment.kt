package com.capstone.saba.ui.notebook.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.capstone.saba.MyApplication
import com.capstone.saba.R
import com.capstone.saba.databinding.FragmentToDoBinding
import com.capstone.saba.vm.ViewModelFactory
import javax.inject.Inject

class ToDoFragment : Fragment() {


    private var _binding: FragmentToDoBinding? = null
    private val binding get() = _binding!!

    companion object {
        val TAG: String = ToDoFragment::class.java.simpleName
    }

    @Inject
    lateinit var factory: ViewModelFactory

    private val noteViewModel: NoteViewModel by viewModels { factory }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().application as MyApplication).appComponent.inject(this)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_to_do, container, false)
    }
}