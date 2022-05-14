package com.example.projectskripsi.modules.auth.presentation

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.projectskripsi.R
import com.example.projectskripsi.core.Resource
import com.example.projectskripsi.modules.auth.presentation.viewmodel.RegisterViewModel
import com.example.projectskripsi.modules.beranda.presentation.ActivityUtamaUser
import com.example.projectskripsi.utils.Tanggal
import org.koin.android.viewmodel.ext.android.viewModel
import java.util.*

class ActivityRegister : AppCompatActivity() {
    private val viewModel: RegisterViewModel by viewModel()

    private lateinit var namaRegister: EditText
    private lateinit var emailRegister: EditText
    private lateinit var passwordRegister: EditText
    private lateinit var konfirmasiRegister: EditText
    private lateinit var tanggalRegister: TextView
    private lateinit var spinnerRegister: Spinner
    private lateinit var genderRegister: TextView
    private lateinit var alamatRegister: EditText
    private lateinit var telpRegister: EditText
    private lateinit var btnRegister: Button
    private lateinit var textLogin: TextView
    private lateinit var alertDialog: AlertDialog.Builder

    @SuppressLint("NewApi")
    private val date: Calendar = Calendar.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        namaRegister = findViewById(R.id.namaRegister)
        emailRegister = findViewById(R.id.emailRegister)
        passwordRegister = findViewById(R.id.passwordRegister)
        konfirmasiRegister = findViewById(R.id.konfirmasiRegister)
        tanggalRegister = findViewById(R.id.tanggalRegister)
        spinnerRegister = findViewById(R.id.spinnerRegister)
        genderRegister = findViewById(R.id.genderRegister)
        alamatRegister = findViewById(R.id.alamatRegister)
        telpRegister = findViewById(R.id.telpRegister)
        btnRegister = findViewById(R.id.btnRegister)
        textLogin = findViewById(R.id.textLogin)
        alertDialog = AlertDialog.Builder(this)

        tanggalRegister.setOnClickListener {
            setTanggal()
        }
        jenisKelamin()

        btnRegister.setOnClickListener {
            alertDialog.setMessage("Apakah data yang dimasukkan sudah benar ?")
                .setCancelable(false)
                .setPositiveButton("YA") { _, _ ->
                    if (validate()) register()
                }
                .setNegativeButton("TIDAK") { dialog, _ -> dialog.cancel() }.create().show()
        }

        textLogin.setOnClickListener {
            finish()
        }
    }

    //Set Tanggal
    private fun setTanggal() {
        val date = DatePickerDialog(this, { _, year, month, dayOfMonth -> val selectedDate = Calendar.getInstance()
            selectedDate.set(Calendar.YEAR, year)
            selectedDate.set(Calendar.MONTH, month)
            selectedDate.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            tanggalRegister.text = Tanggal.format(selectedDate.time)
        }, date.get(Calendar.YEAR), date.get(Calendar.MONTH), date.get(Calendar.DAY_OF_MONTH))
        date.show()
    }

    //Pilih Jenis Kelamin
    private fun jenisKelamin() {
        val genderUser = arrayOf("Laki-laki", "Perempuan")
        spinnerRegister.adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, genderUser)
        spinnerRegister.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
                genderRegister.text = "Masukkan Jenis Kelamin"
            }
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                genderRegister.text = genderUser[position]
            }
        }
    }

    //Validasi Data User
    private fun validate(): Boolean {
        if(namaRegister.text.toString() == "") {
            Toast.makeText(this, "Nama masih kosong", Toast.LENGTH_SHORT).show()
            return false
        }
        if(emailRegister.text.toString() == "") {
            Toast.makeText(this, "Email masih kosong", Toast.LENGTH_SHORT).show()
            return false
        }
        if(passwordRegister.text.toString() == "") {
            Toast.makeText(this, "Password masih kosong", Toast.LENGTH_SHORT).show()
            return false
        }
        if(konfirmasiRegister.text.toString() != passwordRegister.text.toString()) {
            Toast.makeText(this, "Password tidak cocok", Toast.LENGTH_SHORT).show()
            return false
        }
        if(tanggalRegister.text.toString() == "") {
            Toast.makeText(this, "Tanggal lahir kosong", Toast.LENGTH_SHORT).show()
            return false
        }
        if(alamatRegister.text.toString() == "") {
            Toast.makeText(this, "Alamat masih kosong", Toast.LENGTH_SHORT).show()
            return false
        }
        if(telpRegister.text.toString() == "") {
            Toast.makeText(this, "Nomor telepon kosong", Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }

    //Add Data User
    private fun register() {
        btnRegister.isClickable = false
        Toast.makeText(this@ActivityRegister, "Mohon Tunggu...", Toast.LENGTH_SHORT).show()

        viewModel.register(
            namaRegister.text.toString(),
            emailRegister.text.toString(),
            passwordRegister.text.toString(),
            tanggalRegister.text.toString(),
            genderRegister.text.toString(),
            alamatRegister.text.toString(),
            telpRegister.text.toString()
        ).observe(this@ActivityRegister) { res ->
            when (res) {
                is Resource.Loading -> {
                    btnRegister.isClickable = false
                    Toast.makeText(this@ActivityRegister, "Mohon Tunggu...", Toast.LENGTH_SHORT).show()
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
                        val intent = Intent(this@ActivityRegister, ActivityUtamaUser::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        startActivity(intent)

                    }
                    else {
                        btnRegister.isClickable = true
                        Toast.makeText(this@ActivityRegister, "Email atau password salah", Toast.LENGTH_SHORT).show()
                    }
                }
                is Resource.Error -> {
                    btnRegister.isClickable = true
                    Toast.makeText(this, "Gagal Register", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}