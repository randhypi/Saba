package com.capstone.saba.ui.onboarding

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.capstone.saba.R
import com.capstone.saba.databinding.ActivityMainBinding
import com.capstone.saba.databinding.ItemOnboardingBinding

class ViewPagerAdapter(): RecyclerView.Adapter<ViewPagerAdapter.PagerViewHolder>() {


    private val mData = ArrayList<OnBoardingModel>()

    fun setData(items: List<OnBoardingModel>) {
        mData.clear()
        mData.addAll(items)
        notifyDataSetChanged()
    }



    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): PagerViewHolder {
        val binding =  LayoutInflater.from(viewGroup.context).inflate(R.layout.item_onboarding, viewGroup, false)
        return PagerViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PagerViewHolder, position: Int) {
            holder.bind(mData[position])
    }

    override fun getItemCount(): Int = mData.size


    inner  class PagerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemOnboardingBinding.bind(itemView)

        fun bind(item: OnBoardingModel){

            Glide.with(itemView.context)
                .load(item.image)
                .into(binding.image)

            binding.tvOnboarding.text = item.deskripsi


        }


    }
}