package com.example.projectskripsi

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager

class ActivityLoading : AppCompatActivity() {
    lateinit var SP: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loading)

        window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
        SP = getSharedPreferences("User", Context.MODE_PRIVATE)
        val backgrond = object : Thread(){
            override fun run() {
                try {
                    sleep(2500)
                    when {
                        SP.getString("level", "") == "Pengguna" -> {
                            var intent = Intent(applicationContext, com.example.projectskripsi.pengguna.ActivityUtama::class.java)
                            startActivity(intent)
                            finish()
                        }
                        SP.getString("level", "") == "Admin" -> {
                            var intent = Intent(applicationContext, com.example.projectskripsi.admin.ActivityUtama::class.java)
                            startActivity(intent)
                            finish()
                        }
                        SP.getString("level", "") == "" -> {
                            var intent = Intent(applicationContext, ActivityLogin::class.java)
                            startActivity(intent)
                            finish()
                        }
                    }
                } catch (e: Exception){
                    e.printStackTrace()
                }
            }
        }
        backgrond.start()
    }
}