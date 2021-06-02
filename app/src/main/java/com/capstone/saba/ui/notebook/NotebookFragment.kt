package com.capstone.saba.ui.notebook

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.fragment.app.FragmentActivity
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.capstone.saba.R
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator


class NotebookFragment : Fragment() {

    companion object{
        @StringRes
        private val TAB_TITLES = intArrayOf(R.string.todo, R.string.note)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_notebook, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val sectionsPagerAdapter = SectionsPagerAdapter(context as FragmentActivity)
        val viewPager: ViewPager2 = view.findViewById(R.id.view_pager_notebook)
        viewPager.adapter = sectionsPagerAdapter

        val tabs: TabLayout = view.findViewById(R.id.tabs_notebook)
        TabLayoutMediator(tabs, viewPager) { tabs, position ->
            tabs.text = resources.getString(TAB_TITLES[position])
        }.attach()
    }
}