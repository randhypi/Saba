package com.capstone.saba.ui.notebook.fragment.todo

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.capstone.saba.MyApplication
import com.capstone.saba.databinding.FragmentToDoBinding
import com.capstone.saba.domain.model.Todo
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

    private val noteViewModel: ToDoViewModel by viewModels { factory }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().application as MyApplication).appComponent.inject(this)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentToDoBinding.inflate(inflater, container, false)
        val view = binding.root
        return view

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        //val todoAdapter = ToDoAdapter()

        binding.rvNote.setHasFixedSize(true)

        noteViewModel.getNoteTodo().observe(viewLifecycleOwner, { data ->
            showRecycleList(data)

        })

        /*with(binding.rvNote){
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = todoAdapter
        }*/


    }

    private fun showRecycleList(todo: Todo){
        /*binding.rvChat.layoutManager = LinearLayoutManager(context)
        val listChatAdapter = ChatbotAdapter()
        binding.rvChat.scrollToPosition(chat.size - 1)
        listChatAdapter.setData(chat as ArrayList<ChatBot>)
        binding.rvChat.adapter = listChatAdapter*/

        binding.rvNote.layoutManager = LinearLayoutManager(context)
        val listTodoAdapter = ToDoAdapter()
        listTodoAdapter.setData(todo)
        binding.rvNote.adapter = listTodoAdapter
    }
}