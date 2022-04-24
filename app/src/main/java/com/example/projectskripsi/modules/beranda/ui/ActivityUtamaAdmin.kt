package com.example.projectskripsi.modules.beranda.ui

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.example.projectskripsi.R
import com.example.projectskripsi.modules.beranda.ui.fragment.FragmentBerandaAdmin
import com.example.projectskripsi.modules.pesanan.ui.fragment.FragmentPesananAdmin
import com.example.projectskripsi.modules.profil.ui.fragment.FragmentProfilAdmin
import com.google.android.material.bottomnavigation.BottomNavigationView

class ActivityUtamaAdmin : AppCompatActivity() {
    lateinit var alertDialog: AlertDialog.Builder
    lateinit var bottomNav: BottomNavigationView

    //Pilih Menu Navigasi
    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when(item.itemId) {
            R.id.beranda -> {
                replaceFragment(FragmentBerandaAdmin())
                return@OnNavigationItemSelectedListener true
            }
            R.id.pesanan -> {
                replaceFragment(FragmentPesananAdmin())
                return@OnNavigationItemSelectedListener true
            }
            R.id.profil -> {
                replaceFragment(FragmentProfilAdmin())
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.admin_activity_utama)

        bottomNav = findViewById(R.id.bottomNav)
        alertDialog = AlertDialog.Builder(this)
        replaceFragment(FragmentBerandaAdmin())
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