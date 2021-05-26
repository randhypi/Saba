package com.capstone.saba.ui.signup.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import com.capstone.saba.R

class DFragment : Fragment(), View.OnClickListener {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_d, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val btnNext: ImageButton = view.findViewById(R.id.img_button_next)
        btnNext.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        if (v.id == R.id.img_button_next){
            val eFragment = EFragment()
            val fragmentManager = fragmentManager
            fragmentManager?.beginTransaction()?.apply {
                replace(R.id.frame_container_signup, eFragment, EFragment::class.java.simpleName)
                addToBackStack(null)
                commit()
            }
        }
    }

}