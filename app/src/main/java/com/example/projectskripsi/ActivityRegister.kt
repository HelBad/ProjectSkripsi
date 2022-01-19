package com.example.projectskripsi

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Context
import android.content.DialogInterface
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import com.example.projectskripsi.model.User
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.text.SimpleDateFormat
import java.util.*

class ActivityRegister : AppCompatActivity() {
    lateinit var namaRegister: EditText
    lateinit var emailRegister: EditText
    lateinit var passwordRegister: EditText
    lateinit var konfirmasiRegister: EditText
    lateinit var tanggalRegister: TextView
    lateinit var spinnerRegister: Spinner
    lateinit var genderRegister: TextView
    lateinit var alamatRegister: EditText
    lateinit var telpRegister: EditText
    lateinit var btnRegister: Button
    lateinit var textLogin: TextView
    lateinit var SP: SharedPreferences
    lateinit var alertDialog: AlertDialog.Builder

    @SuppressLint("NewApi")
    var formateDate = SimpleDateFormat("dd MMM YYYY")
    val date = Calendar.getInstance()

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
        SP = getSharedPreferences("User", Context.MODE_PRIVATE)
        alertDialog = AlertDialog.Builder(this)

        tanggalRegister.setOnClickListener {
            val date = DatePickerDialog(this, {
                    view, year, month, dayOfMonth -> val selectedDate = Calendar.getInstance()
                selectedDate.set(Calendar.YEAR, year)
                selectedDate.set(Calendar.MONTH, month)
                selectedDate.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                tanggalRegister.text = formateDate.format(selectedDate.time)
            }, date.get(Calendar.YEAR), date.get(Calendar.MONTH), date.get(Calendar.DAY_OF_MONTH))
            date.show()
        }

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

        btnRegister.setOnClickListener {
            alertDialog.setMessage("Apakah data yang dimasukkan sudah benar ?")
                .setCancelable(false)
                .setPositiveButton("YA", object: DialogInterface.OnClickListener {
                    override fun onClick(dialog: DialogInterface, id:Int) {
                        if(validate()){
                            FirebaseDatabase.getInstance().getReference("user").orderByChild("email")
                                .equalTo(emailRegister.text.toString())
                                .addListenerForSingleValueEvent( object : ValueEventListener {
                                    override fun onDataChange(p0: DataSnapshot) {
                                        if(p0.exists()) {
                                            Toast.makeText(this@ActivityRegister, "Email sudah terdaftar",
                                                Toast.LENGTH_SHORT).show()
                                        } else {
                                            register()
                                        }
                                    }
                                    override fun onCancelled(p0: DatabaseError) { }
                                })
                        }
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

    private fun register() {
        btnRegister.isClickable = false
        Toast.makeText(this, "Mohon Tunggu...", Toast.LENGTH_SHORT).show()

        val ref = FirebaseDatabase.getInstance().getReference("user")
        val id_user  = ref.push().key.toString()
        val addData = User(id_user, namaRegister.text.toString(), emailRegister.text.toString(),
            passwordRegister.text.toString(), tanggalRegister.text.toString(), genderRegister.text.toString(),
            alamatRegister.text.toString(), telpRegister.text.toString(), "Pengguna")

        ref.child(id_user).setValue(addData).addOnCompleteListener {
            val editor = SP.edit()
            editor.putString("id_user", id_user)
            editor.putString("nama", namaRegister.text.toString())
            editor.putString("email", emailRegister.text.toString())
            editor.putString("password", passwordRegister.text.toString())
            editor.putString("tgl_lahir", tanggalRegister.text.toString())
            editor.putString("gender", genderRegister.text.toString())
            editor.putString("alamat", alamatRegister.text.toString())
            editor.putString("telp", telpRegister.text.toString())
            editor.putString("level", addData.level)
            editor.apply()
            finish()
        }.addOnFailureListener {
            btnRegister.isClickable = true
            Toast.makeText(this, "Gagal Register", Toast.LENGTH_SHORT).show()
        }
    }
}