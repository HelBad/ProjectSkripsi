package com.example.projectskripsi.fragment

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.*
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.viewpager2.widget.ViewPager2
import com.example.projectskripsi.R
import com.example.projectskripsi.adapter.SliderAdapter
import com.example.projectskripsi.adapter.SliderItem

class FragmentBeranda : Fragment() {
    lateinit var actionBar: Toolbar
    lateinit var alertDialog: AlertDialog.Builder
    private lateinit var viewPager2: ViewPager2
    private val sliderHandler = Handler()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_beranda, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        actionBar = requireActivity().findViewById(R.id.toolbarUtama)
        (activity as AppCompatActivity).setSupportActionBar(actionBar)
        alertDialog = AlertDialog.Builder(requireActivity())

        viewPager2 = requireActivity().findViewById(R.id.gambarUtama)
        val sliderItems: MutableList<SliderItem> = ArrayList()
        sliderItems.add(SliderItem(R.drawable.sampel))
        sliderItems.add(SliderItem(R.drawable.sampel))
        sliderItems.add(SliderItem(R.drawable.sampel))
        viewPager2.adapter = SliderAdapter(sliderItems, viewPager2)
//        viewPager2.clipToPadding = false
//        viewPager2.clipChildren = false
//        viewPager2.offscreenPageLimit = 3
//        viewPager2.getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER
//        val compositePageTransformer = CompositePageTransformer()
//        compositePageTransformer.addTransformer(MarginPageTransformer(30))
//        compositePageTransformer.addTransformer { page, position ->
//            val r = 1 - abs(position)
//            page.scaleY = 0.85f + r * 0.25f
//        }
//        viewPager2.setPageTransformer(compositePageTransformer)
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
//    override fun onPause() {
//        super.onPause()
//        sliderHandler.postDelayed(sliderRunnable, 3000)
//    }
//
//    override fun onResume() {
//        super.onResume()
//        sliderHandler.postDelayed(sliderRunnable, 3000)
//    }
}