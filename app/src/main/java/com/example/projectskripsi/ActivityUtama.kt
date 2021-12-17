package com.example.projectskripsi

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import java.lang.Math.abs

class ActivityUtama : AppCompatActivity() {
    lateinit var actionBar: Toolbar
    lateinit var alertDialog: AlertDialog.Builder
    private lateinit var viewPager2: ViewPager2
    private val sliderHandler = Handler()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_utama)

        actionBar = findViewById(R.id.toolbarUtama)
        (this as AppCompatActivity).setSupportActionBar(actionBar)
        alertDialog = AlertDialog.Builder(this)

        viewPager2 = findViewById(R.id.gambarUtama)
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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_utama, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == R.id.aksiProfil) {
            val intent = Intent(this, ActivityProfil::class.java)
            startActivity(intent)
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        alertDialog.setTitle("Keluar Aplikasi")
        alertDialog.setMessage("Apakah anda ingin keluar aplikasi ?")
            .setCancelable(false)
            .setPositiveButton("YA", object: DialogInterface.OnClickListener {
                override fun onClick(dialog: DialogInterface, id:Int) {
                    finishAffinity()
                }
            })
            .setNegativeButton("TIDAK", object: DialogInterface.OnClickListener {
                override fun onClick(dialog: DialogInterface, id:Int) {
                    dialog.cancel()
                }
            }).create().show()
    }
}