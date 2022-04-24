package com.example.projectskripsi.modules.auth.ui

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.projectskripsi.R
import com.example.projectskripsi.core.Resource
import com.example.projectskripsi.modules.beranda.ui.ActivityUtamaAdmin
import com.example.projectskripsi.modules.auth.ui.viewmodel.AuthViewModel
import com.example.projectskripsi.modules.beranda.ui.ActivityUtamaUser
import org.koin.android.viewmodel.ext.android.viewModel

class ActivityLogin : AppCompatActivity() {
    private val authViewModel: AuthViewModel by viewModel()

    private lateinit var emailLogin: EditText
    private lateinit var passwordLogin: EditText
    private lateinit var btnLogin: Button
    private lateinit var textRegister: TextView
    private lateinit var sp: SharedPreferences
    private lateinit var alertDialog: AlertDialog.Builder

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
                            if (user != null) {
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
                                    val intent = Intent(this@ActivityLogin, ActivityUtamaUser::class.java)
                                    startActivity(intent)
                                    finish()
                                } else if (user.level == "Admin") {
                                    btnLogin.isClickable = false
                                    val intent = Intent(this@ActivityLogin, ActivityUtamaAdmin::class.java)
                                    startActivity(intent)
                                    finish()
                                }
                            }
//                            else {
//                                btnLogin.isClickable = true
//                                Toast.makeText(this@ActivityLogin, "Email atau password salah", Toast.LENGTH_SHORT).show()
//                            }
                        }
                        is Resource.Error -> {
                            btnLogin.isClickable = true
                            Toast.makeText(this@ActivityLogin, "Terjadi kesalahan saat login", Toast.LENGTH_SHORT).show()
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

    override fun onBackPressed() {
        alertDialog.setTitle("Keluar Aplikasi")
        alertDialog.setMessage("Apakah anda ingin keluar aplikasi ?").setCancelable(false)
            .setPositiveButton("YA") { _, _ -> finishAffinity() }
            .setNegativeButton("TIDAK") { dialog, _ -> dialog.cancel() }.create().show()
    }
}