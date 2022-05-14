package com.example.projectskripsi.modules.auth.presentation

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.projectskripsi.R
import com.example.projectskripsi.core.Resource
import com.example.projectskripsi.modules.auth.presentation.viewmodel.LoginViewModel
import com.example.projectskripsi.modules.beranda.presentation.ActivityUtamaAdmin
import com.example.projectskripsi.modules.beranda.presentation.ActivityUtamaUser
import org.koin.android.viewmodel.ext.android.viewModel

class ActivityLogin : AppCompatActivity() {
    private val viewModel: LoginViewModel by viewModel()

    private lateinit var emailLogin: EditText
    private lateinit var passwordLogin: EditText
    private lateinit var btnLogin: Button
    private lateinit var textRegister: TextView
    private lateinit var alertDialog: AlertDialog.Builder

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        emailLogin = findViewById(R.id.emailLogin)
        passwordLogin = findViewById(R.id.passwordLogin)
        btnLogin = findViewById(R.id.btnLogin)
        textRegister = findViewById(R.id.textRegister)
        alertDialog = AlertDialog.Builder(this)

        btnLogin.setOnClickListener {
            if(validate()) {
                btnLogin.isClickable = false
                Toast.makeText(this@ActivityLogin, "Mohon Tunggu...", Toast.LENGTH_SHORT).show()

                viewModel.login(
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
                                viewModel.saveUser(
                                    user.idUser,
                                    user.nama,
                                    user.email,
                                    user.password,
                                    user.tglLahir,
                                    user.gender,
                                    user.alamat,
                                    user.telp,
                                    user.level
                                )

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
                            else {
                                btnLogin.isClickable = true
                                Toast.makeText(this@ActivityLogin, "Email atau password salah", Toast.LENGTH_SHORT).show()
                            }
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