package com.capstone.saba.ui.notebook

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.capstone.saba.ui.notebook.fragment.note.NoteFragment
import com.capstone.saba.ui.notebook.fragment.ToDoFragment

class SectionsPagerAdapter(private val fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {

    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        var fragment: Fragment? = null
        when(position) {
            0 -> fragment = ToDoFragment()
            1 -> fragment = NoteFragment()
        }
        return fragment as Fragment
    }
}