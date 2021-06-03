package com.capstone.saba.ui.chatbot

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.capstone.saba.R
import com.capstone.saba.databinding.ItemRowChatBinding
import com.capstone.saba.domain.model.ChatBot

class ChatbotAdapter(): RecyclerView.Adapter<ChatbotAdapter.ListViewHolder>() {

    private val mData = ArrayList<ChatBot>()


    fun setData(items: ArrayList<ChatBot>) {
        mData.clear()
        mData.addAll(items)
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ListViewHolder {
        val binding =  LayoutInflater.from(viewGroup.context).inflate(R.layout.item_row_chat, viewGroup, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(listViewHolder: ListViewHolder, position: Int) {
        listViewHolder.bind(mData[position])


    }

    override fun getItemCount(): Int = mData.size

    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        private val binding = ItemRowChatBinding.bind(itemView)
        @SuppressLint("CheckResult")
        fun bind(chat: ChatBot) {

            binding.tvChatLeft.apply {
                this.visibility = View.VISIBLE
                this.text = chat.response
            }
            Log.d("ADAPTER","$chat \n$chat")
                binding.tvChatRight.apply {
                    this.visibility = View.VISIBLE
                    this.text = chat.input
                }


        }
    }



}