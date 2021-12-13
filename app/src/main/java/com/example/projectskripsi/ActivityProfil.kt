package com.example.projectskripsi

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AlertDialog

class ActivityProfil : AppCompatActivity() {
    lateinit var backProfil: ImageView
    lateinit var btnKeluar:Button
    lateinit var btnSimpan:Button
    lateinit var alertDialog: AlertDialog.Builder

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profil)

        backProfil = findViewById(R.id.backProfil)
        btnKeluar = findViewById(R.id.btnKeluar)
        btnSimpan = findViewById(R.id.btnSimpan)
        alertDialog = AlertDialog.Builder(this)

        backProfil.setOnClickListener {
            val intent = Intent(this, ActivityUtama::class.java)
            startActivity(intent)
            finish()
        }
        btnKeluar.setOnClickListener {
            val intent = Intent(this, ActivityLogin::class.java)
            alertDialog.setTitle("Keluar Akun")
            alertDialog.setMessage("Apakah anda ingin keluar dari akun ini ?")
                .setCancelable(false)
                .setPositiveButton("YA", object: DialogInterface.OnClickListener {
                    override fun onClick(dialog: DialogInterface, id:Int) {
                        startActivity(intent)
                        finish()
                    }
                })
                .setNegativeButton("TIDAK", object: DialogInterface.OnClickListener {
                    override fun onClick(dialog: DialogInterface, id:Int) {
                        dialog.cancel()
                    }
                }).create().show()
        }
        btnSimpan.setOnClickListener {
            val intent = Intent(this, ActivityUtama::class.java)
            alertDialog.setMessage("Apakah ingin menyimpan perubahan data ?")
                .setCancelable(false)
                .setPositiveButton("YA", object: DialogInterface.OnClickListener {
                    override fun onClick(dialog: DialogInterface, id:Int) {
                        startActivity(intent)
                        finish()
                    }
                })
                .setNegativeButton("TIDAK", object: DialogInterface.OnClickListener {
                    override fun onClick(dialog: DialogInterface, id:Int) {
                        dialog.cancel()
                    }
                }).create().show()
        }
    }
}