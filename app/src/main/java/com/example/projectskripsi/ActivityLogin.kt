package com.example.projectskripsi

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.projectskripsi.model.User
import com.example.projectskripsi.pengguna.ActivityUtama
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class ActivityLogin : AppCompatActivity() {
    lateinit var emailLogin: EditText
    lateinit var passwordLogin: EditText
    lateinit var btnLogin: Button
    lateinit var textRegister: TextView
    lateinit var SP: SharedPreferences
    lateinit var alertDialog: AlertDialog.Builder

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        emailLogin = findViewById(R.id.emailLogin)
        passwordLogin = findViewById(R.id.passwordLogin)
        btnLogin = findViewById(R.id.btnLogin)
        textRegister = findViewById(R.id.textRegister)
        SP = getSharedPreferences("User", Context.MODE_PRIVATE)
        alertDialog = AlertDialog.Builder(this)

        btnLogin.setOnClickListener {
            if(validate()) {
                login()
            }
        }
        textRegister.setOnClickListener {
            val intent = Intent(this, ActivityRegister::class.java)
            startActivity(intent)
        }
    }

    private fun validate(): Boolean {
        if(emailLogin.text.toString() == "") {
            Toast.makeText(this, "Email kosong", Toast.LENGTH_SHORT).show()
            return false
        }
        if(emailLogin.text.toString() == "") {
            Toast.makeText(this, "Password kosong", Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }

    private fun login() {
        FirebaseDatabase.getInstance().getReference("user").orderByChild("email")
            .equalTo(emailLogin.text.toString()).addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onCancelled(p0: DatabaseError) {}
                override fun onDataChange(p0: DataSnapshot) {
                    if (p0.exists()) {
                        for (h in p0.children) {
                            val us = h.getValue(User::class.java)
                            if(us!!.password == passwordLogin.text.toString()) {
                                val editor = SP.edit()
                                editor.putString("id_user", us.id_user)
                                editor.putString("nama", us.nama)
                                editor.putString("email", us.email)
                                editor.putString("password", us.password)
                                editor.putString("tgl_lahir", us.tgl_lahir)
                                editor.putString("gender", us.gender)
                                editor.putString("alamat", us.alamat)
                                editor.putString("telp", us.telp)
                                editor.putString("level", us.level)
                                editor.apply()

                                if(us.level == "Pengguna") {
                                    btnLogin.isClickable = false
                                    Toast.makeText(this@ActivityLogin, "Login sukses", Toast.LENGTH_SHORT).show()
                                    val intent = Intent(this@ActivityLogin, ActivityUtama::class.java)
                                    startActivity(intent)
                                    finish()
                                } else {
                                    Toast.makeText(this@ActivityLogin, "Email salah", Toast.LENGTH_SHORT).show()
                                }
                            } else {
                                Toast.makeText(this@ActivityLogin, "Password salah", Toast.LENGTH_SHORT).show()
                            }
                        }
                    } else {
                        Toast.makeText(this@ActivityLogin, "Email salah", Toast.LENGTH_SHORT).show()
                    }
                }
            })
    }

    override fun onBackPressed() {
        alertDialog.setTitle("Keluar Aplikasi")
        alertDialog.setMessage("Apakah anda ingin keluar aplikasi ?").setCancelable(false)
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