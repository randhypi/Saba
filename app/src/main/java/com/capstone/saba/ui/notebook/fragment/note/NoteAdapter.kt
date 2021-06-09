package com.capstone.saba.ui.notebook.fragment.note

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.capstone.saba.databinding.CardNoteBinding
import com.capstone.saba.domain.model.Note
import java.util.ArrayList

class NoteAdapter() : RecyclerView.Adapter<NoteAdapter.ViewHolder>(){

    private val listNote: ArrayList<Note>()


    fun setData(items: List<Note>) {
        listNote.clear()
        listNote.addAll(items)
        notifyDataSetChanged()

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemBinding = CardNoteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listNote[position])
    }

    override fun getItemCount(): Int = listNote.size

    class ViewHolder(private val binding: CardNoteBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(note: Note){
            with(binding){
                tvTitleNote.text = note.title
                tvDescNote.text = note.deskripsi
            }
        }
    }
}