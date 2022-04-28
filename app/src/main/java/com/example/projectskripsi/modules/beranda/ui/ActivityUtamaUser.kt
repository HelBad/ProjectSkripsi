package com.example.projectskripsi.modules.beranda.ui

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.example.projectskripsi.R
import com.example.projectskripsi.modules.beranda.ui.fragment.FragmentBerandaUser
import com.example.projectskripsi.modules.pesanan.presentation.fragment.FragmentPesananUser
import com.example.projectskripsi.modules.profil.presentation.fragment.FragmentProfilUser
import com.google.android.material.bottomnavigation.BottomNavigationView

class ActivityUtamaUser : AppCompatActivity() {
    lateinit var alertDialog: AlertDialog.Builder
    lateinit var bottomNav: BottomNavigationView

    //Pilih Menu Navigasi
    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when(item.itemId) {
            R.id.beranda -> {
                replaceFragment(FragmentBerandaUser())
                return@OnNavigationItemSelectedListener true
            }
            R.id.pesanan -> {
                replaceFragment(FragmentPesananUser())
                return@OnNavigationItemSelectedListener true
            }
            R.id.profil -> {
                replaceFragment(FragmentProfilUser())
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_utama)

        bottomNav = findViewById(R.id.bottomNav)
        alertDialog = AlertDialog.Builder(this)
        if (intent.getStringExtra("pesanan").toString() == "true") {
            replaceFragment(FragmentPesananUser())
            bottomNav.selectedItemId = R.id.pesanan
        } else {
            replaceFragment(FragmentBerandaUser())
        }
        bottomNav.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
    }

    //Replace Fragment Menu Navigasi
    private fun replaceFragment(fragment: Fragment) {
        val fragmentTransition = supportFragmentManager.beginTransaction()
        fragmentTransition.replace(R.id.fragmentContainer, fragment)
        fragmentTransition.commit()
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