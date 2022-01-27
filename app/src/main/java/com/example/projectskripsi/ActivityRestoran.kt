package com.example.projectskripsi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import androidx.viewpager2.widget.ViewPager2
import com.example.projectskripsi.adapter.SliderAdapter
import com.example.projectskripsi.adapter.SliderItem

class ActivityRestoran : AppCompatActivity() {
    lateinit var viewPager2: ViewPager2
    private val sliderHandler = Handler()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_restoran)

        viewPager2 = findViewById(R.id.gambarTentang)
        val sliderItems: MutableList<SliderItem> = ArrayList()
        sliderItems.add(SliderItem(R.drawable.sampel))
        sliderItems.add(SliderItem(R.drawable.sampel))
        sliderItems.add(SliderItem(R.drawable.sampel))
        viewPager2.adapter = SliderAdapter(sliderItems, viewPager2)
        viewPager2.registerOnPageChangeCallback(object: ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                sliderHandler.removeCallbacks(sliderRunnable)
                sliderHandler.postDelayed(sliderRunnable, 3000)
            }
        })
    }

    private val sliderRunnable = Runnable {
        viewPager2.currentItem = viewPager2.currentItem + 1
    }
}