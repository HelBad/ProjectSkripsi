package com.example.projectskripsi.core.ui.auth

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
import com.example.projectskripsi.R
import com.example.projectskripsi.core.Resource
import com.example.projectskripsi.core.ui.admin.ActivityUtama
import com.example.projectskripsi.core.ui.auth.viewmodel.AuthViewModel
import com.example.projectskripsi.model.User
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import org.koin.android.viewmodel.ext.android.viewModel

class ActivityLogin : AppCompatActivity() {
    private val authViewModel: AuthViewModel by viewModel()

    lateinit var emailLogin: EditText
    lateinit var passwordLogin: EditText
    lateinit var btnLogin: Button
    lateinit var textRegister: TextView
    lateinit var sp: SharedPreferences
    lateinit var alertDialog: AlertDialog.Builder

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        emailLogin = findViewById(R.id.emailLogin)
        passwordLogin = findViewById(R.id.passwordLogin)
        btnLogin = findViewById(R.id.btnLogin)
        textRegister = findViewById(R.id.textRegister)
        sp = getSharedPreferences("User", Context.MODE_PRIVATE)
        alertDialog = AlertDialog.Builder(this)

        btnLogin.setOnClickListener {
            if(validate()) {
                btnLogin.isClickable = false
                Toast.makeText(this@ActivityLogin, "Mohon Tunggu...", Toast.LENGTH_SHORT).show()

                authViewModel.login(
                    emailLogin.text.toString(),
                    passwordLogin.text.toString()
                ).observe(this@ActivityLogin) { res ->
                    when (res) {
                        is Resource.Loading -> {
                            btnLogin.isClickable = false
                            Toast.makeText(this@ActivityLogin, "Mohon Tunggu...", Toast.LENGTH_SHORT).show()
                        }
                        is Resource.Success -> {
                            val user = res.data
                            if (user != null && user.password == passwordLogin.text.toString()) {
                                val editor = sp.edit()
                                editor.putString("id_user", user.id_user)
                                editor.putString("nama", user.nama)
                                editor.putString("email", user.email)
                                editor.putString("password", user.password)
                                editor.putString("tgl_lahir", user.tgl_lahir)
                                editor.putString("gender", user.gender)
                                editor.putString("alamat", user.alamat)
                                editor.putString("telp", user.telp)
                                editor.putString("level", user.level)
                                editor.apply()

                                if (user.level == "Pengguna") {
                                    btnLogin.isClickable = false
                                    val intent = Intent(this@ActivityLogin, com.example.projectskripsi.core.ui.pengguna.ActivityUtama::class.java)
                                    startActivity(intent)
                                    finish()
                                } else if (user.level == "Admin") {
                                    btnLogin.isClickable = false
                                    val intent = Intent(this@ActivityLogin, ActivityUtama::class.java)
                                    startActivity(intent)
                                    finish()
                                }
                            }
                        }
                        is Resource.Error -> {
                            btnLogin.isClickable = true
                            Toast.makeText(this@ActivityLogin, "Terjadi kesalahan saat login", Toast.LENGTH_LONG).show()
                        }
                    }
                }
            }
        }

        textRegister.setOnClickListener {
            val intent = Intent(this@ActivityLogin, ActivityRegister::class.java)
            startActivity(intent)
        }
    }

    //Validasi User
    private fun validate(): Boolean {
        if(emailLogin.text.toString() == "") {
            Toast.makeText(this, "Email kosong", Toast.LENGTH_SHORT).show()
            return false
        }
        if(passwordLogin.text.toString() == "") {
            Toast.makeText(this, "Password kosong", Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }

    //Login User
    private fun login() {
        btnLogin.isClickable = false
        Toast.makeText(this@ActivityLogin, "Mohon Tunggu...", Toast.LENGTH_SHORT).show()
        FirebaseDatabase.getInstance().getReference("user").orderByChild("email")
            .equalTo(emailLogin.text.toString()).addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onCancelled(p0: DatabaseError) {}
                override fun onDataChange(p0: DataSnapshot) {
                    if (p0.exists()) {
                        for (h in p0.children) {
                            val us = h.getValue(User::class.java)
                            if(us!!.password == passwordLogin.text.toString()) {
                                val editor = sp.edit()
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
                                    val intent = Intent(this@ActivityLogin,
                                        com.example.projectskripsi.core.ui.pengguna.ActivityUtama::class.java)
                                    startActivity(intent)
                                    finish()
                                } else if(us.level == "Admin") {
                                    btnLogin.isClickable = false
                                    val intent = Intent(this@ActivityLogin,
                                        ActivityUtama::class.java)
                                    startActivity(intent)
                                    finish()
                                }
                            } else {
                                btnLogin.isClickable = true
                                Toast.makeText(this@ActivityLogin, "Password salah", Toast.LENGTH_SHORT).show()
                            }
                        }
                    } else {
                        btnLogin.isClickable = true
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