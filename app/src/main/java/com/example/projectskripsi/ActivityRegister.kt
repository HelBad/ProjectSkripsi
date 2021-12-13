package com.example.projectskripsi

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AlertDialog

class ActivityRegister : AppCompatActivity() {
    lateinit var btnRegister: Button
    lateinit var textLogin: TextView
    lateinit var alertDialog: AlertDialog.Builder

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        btnRegister = findViewById(R.id.btnRegister)
        textLogin = findViewById(R.id.textLogin)
        alertDialog = AlertDialog.Builder(this)

        btnRegister.setOnClickListener {
            alertDialog.setMessage("Apakah data yang dimasukkan benar ?")
                .setCancelable(false)
                .setPositiveButton("YA", object: DialogInterface.OnClickListener {
                    override fun onClick(dialog: DialogInterface, id:Int) {
                        finish()
                    }
                })
                .setNegativeButton("TIDAK", object: DialogInterface.OnClickListener {
                    override fun onClick(dialog: DialogInterface, id:Int) {
                        dialog.cancel()
                    }
                }).create().show()
        }
        textLogin.setOnClickListener {
            finish()
        }
    }
}