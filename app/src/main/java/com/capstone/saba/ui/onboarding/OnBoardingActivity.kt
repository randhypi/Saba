package com.capstone.saba.ui.onboarding

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.viewpager2.widget.ViewPager2
import com.capstone.saba.MainActivity
import com.capstone.saba.MyApplication
import com.capstone.saba.R
import com.capstone.saba.databinding.ActivityOnBoardingBinding
import com.capstone.saba.ui.home.HomeFragment
import com.capstone.saba.ui.login.LoginFragment
import com.capstone.saba.vm.ViewModelFactory
import javax.inject.Inject

class OnBoardingActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOnBoardingBinding
    private lateinit var adapter: ViewPagerAdapter

    @Inject
    lateinit var factory: ViewModelFactory

    private val onBoardingViewModel: OnBoardingViewModel by viewModels {factory}



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (this.application as MyApplication).appComponent.inject(this)
        binding = ActivityOnBoardingBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        supportActionBar?.hide()
        adapter = ViewPagerAdapter()
        val viewPager: ViewPager2 = findViewById(R.id.view_pager_onboarding)


        onBoardingViewModel.getOnBoarding().observe(this, { data ->
            adapter.setData(data)
        })


        binding.viewPagerOnboarding.adapter = adapter
        binding.viewPagerOnboarding.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                btn(position)
                setIndicator(position)
                super.onPageSelected(position)
            }
        })

        binding.imgBtnImage.setOnClickListener {
            when (viewPager.currentItem) {
                0, 1 -> {
                    viewPager.currentItem += 1
                }
                2 ->{
                    val sharedPreferences = getSharedPreferences("onboarding", MODE_PRIVATE)
                    val editorShared = sharedPreferences.edit()
                    editorShared.putBoolean("value",true)
                    editorShared.apply()

                    val moveIntent = Intent(this@OnBoardingActivity, MainActivity::class.java)
                    startActivity(moveIntent)
                    finish()
                }
            }
        }


    }


    fun btn(position: Int) {
        when (position) {
            0, 1 -> {
                binding.imgBtnImage.setImageResource(R.drawable.ic_button_next)
            }
            2 -> {
                binding.imgBtnImage.setImageResource(R.drawable.ic_button_finish_onboarding)
            }
        }
    }

    fun setIndicator(position: Int) {
        when (position) {
            0 -> {
                binding.indicator1.setImageResource(R.drawable.ic_indicator_on)
                binding.indicator2.setImageResource(R.drawable.ic_indicator_off)
                binding.indicator3.setImageResource(R.drawable.ic_indicator_off)


            }
            1 -> {
                binding.indicator1.setImageResource(R.drawable.ic_indicator_off)
                binding.indicator2.setImageResource(R.drawable.ic_indicator_on)
                binding.indicator3.setImageResource(R.drawable.ic_indicator_off)
            }
            2 -> {
                binding.indicator1.setImageResource(R.drawable.ic_indicator_off)
                binding.indicator2.setImageResource(R.drawable.ic_indicator_off)
                binding.indicator3.setImageResource(R.drawable.ic_indicator_on)
            }
        }
    }


}