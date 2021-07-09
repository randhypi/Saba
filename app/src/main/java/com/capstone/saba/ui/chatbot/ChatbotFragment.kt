package com.capstone.saba.ui.chatbot

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.capstone.saba.MyApplication
import com.capstone.saba.databinding.FragmentChatbotBinding
import com.capstone.saba.domain.model.ChatBot
import com.capstone.saba.vm.ViewModelFactory
import com.google.android.material.bottomsheet.BottomSheetBehavior
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import javax.inject.Inject


class ChatbotFragment : Fragment() {


    private var _binding: FragmentChatbotBinding? = null
    private val binding get() = _binding!!
    lateinit var bottomSheetBehavior : BottomSheetBehavior<*>

    companion object {
        val TAG = ChatbotFragment::class.java.simpleName
    }


    @Inject
    lateinit var factory: ViewModelFactory

    private val chatbotViewModel: ChatbotViewModel by viewModels { factory }

    @SuppressLint("SetTextI18n")
    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().application as MyApplication).appComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentChatbotBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvChat.setHasFixedSize(true)


        chatbotViewModel.getChat().observe(viewLifecycleOwner, { data ->
            Log.d(TAG, "${data}")
            showRecyclerList(data)
        })



        binding.btnChatSent.setOnClickListener {
            val text = binding.teChatSent.text
            Log.d("Data",
                "Click ${Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())}")

            chatbotViewModel.sentChat(text.toString()).observe(viewLifecycleOwner, { data ->
                if (data) {
                    binding.teChatSent.text?.clear()
                } else {
                    Toast.makeText(context, "Gagal mengirim", Toast.LENGTH_SHORT).show()
                }
            })
        }
    }

    private fun showRecyclerList(chat: List<ChatBot>) {
        binding.rvChat.layoutManager = LinearLayoutManager(context)
        val listChatAdapter = ChatbotAdapter()
        binding.rvChat.scrollToPosition(chat.size - 1)
        listChatAdapter.setData(chat as ArrayList<ChatBot>)
        binding.rvChat.adapter = listChatAdapter
    }


}


