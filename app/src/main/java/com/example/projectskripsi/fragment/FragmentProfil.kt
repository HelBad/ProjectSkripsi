package com.example.projectskripsi.fragment

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import android.content.res.ColorStateList
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AlertDialog
import com.example.projectskripsi.ActivityLogin
import com.example.projectskripsi.ActivityUtama
import com.example.projectskripsi.R
import com.example.projectskripsi.model.User
import com.google.firebase.database.FirebaseDatabase
import java.text.SimpleDateFormat
import java.util.*

class FragmentProfil : Fragment() {
    lateinit var SP: SharedPreferences
    lateinit var alertDialog: AlertDialog.Builder
    lateinit var profilNama: EditText
    lateinit var profilEmail: EditText
    lateinit var profilPassword: EditText
    lateinit var profilTanggal: TextView
    lateinit var profilSpinner: Spinner
    lateinit var profilGender: TextView
    lateinit var profilAlamat: EditText
    lateinit var profilTelp: EditText
    lateinit var btnSimpan: Button
    lateinit var btnKeluar: Button

    @SuppressLint("NewApi")
    var formateDate = SimpleDateFormat("dd MMM YYYY")
    val date = Calendar.getInstance()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_profil, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        profilNama = requireActivity().findViewById(R.id.profilNama)
        profilEmail = requireActivity().findViewById(R.id.profilEmail)
        profilPassword = requireActivity().findViewById(R.id.profilPassword)
        profilTanggal = requireActivity().findViewById(R.id.profilTanggal)
        profilSpinner = requireActivity().findViewById(R.id.profilSpinner)
        profilGender = requireActivity().findViewById(R.id.profilGender)
        profilAlamat = requireActivity().findViewById(R.id.profilAlamat)
        profilTelp = requireActivity().findViewById(R.id.profilTelp)
        btnSimpan = requireActivity().findViewById(R.id.btnSimpan)
        btnKeluar = requireActivity().findViewById(R.id.btnKeluar)
        SP = requireActivity().applicationContext.getSharedPreferences("User", Context.MODE_PRIVATE)
        alertDialog = AlertDialog.Builder(requireActivity())

        profilNama.setText(SP.getString("nama", ""))
        profilEmail.setText(SP.getString("email", ""))
        profilPassword.setText(SP.getString("password", ""))
        profilTanggal.text = SP.getString("tgl_lahir", "")
        profilGender.text = SP.getString("gender", "")
        profilAlamat.setText(SP.getString("alamat", ""))
        profilTelp.setText(SP.getString("telp", ""))

        profilTanggal.setOnClickListener {
            val date = DatePickerDialog(requireActivity(), {
                    view, year, month, dayOfMonth -> val selectedDate = Calendar.getInstance()
                selectedDate.set(Calendar.YEAR, year)
                selectedDate.set(Calendar.MONTH, month)
                selectedDate.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                profilTanggal.text = formateDate.format(selectedDate.time)
            }, date.get(Calendar.YEAR), date.get(Calendar.MONTH), date.get(Calendar.DAY_OF_MONTH))
            date.show()
        }

        val genderUser = arrayOf("Laki-laki", "Perempuan")
        profilSpinner.adapter = ArrayAdapter(requireActivity(), android.R.layout.simple_list_item_1, genderUser)
        profilSpinner.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
                profilGender.text = "Masukkan Jenis Kelamin"
            }
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                profilGender.text = genderUser[position]
            }
        }

        btnKeluar.setOnClickListener {
            alertDialog.setTitle("Keluar Akun")
            alertDialog.setMessage("Apakah anda ingin keluar dari akun ini ?").setCancelable(false)
                .setPositiveButton("YA", object: DialogInterface.OnClickListener {
                    override fun onClick(dialog: DialogInterface, id:Int) {
                        val editor = SP.edit()
                        editor.putString("id_user", "")
                        editor.putString("nama", "")
                        editor.putString("email", "")
                        editor.putString("password", "")
                        editor.putString("tgl_lahir", "")
                        editor.putString("gender", "")
                        editor.putString("alamat", "")
                        editor.putString("telp", "")
                        editor.putString("level", "")
                        editor.apply()

                        val intent = Intent(context, ActivityLogin::class.java)
                        startActivity(intent)
                        activity!!.finish()
                    }
                })
                .setNegativeButton("TIDAK", object: DialogInterface.OnClickListener {
                    override fun onClick(dialog: DialogInterface, id:Int) {
                        dialog.cancel()
                    }
                }).create().show()
        }

        btnSimpan.setOnClickListener {
            alertDialog.setMessage("Apakah ingin menyimpan perubahan data ?").setCancelable(false)
                .setPositiveButton("YA", object: DialogInterface.OnClickListener {
                    override fun onClick(dialog: DialogInterface, id:Int) {
                        if(validate()) {
                            saveData()
                        }
                    }
                })
                .setNegativeButton("TIDAK", object: DialogInterface.OnClickListener {
                    override fun onClick(dialog: DialogInterface, id:Int) {
                        dialog.cancel()
                    }
                }).create().show()
        }
    }

    private fun validate(): Boolean {
        if(profilNama.text.toString() == "") {
            Toast.makeText(activity, "Nama masih kosong", Toast.LENGTH_SHORT).show()
            return false
        }
        if(profilEmail.text.toString() == "") {
            Toast.makeText(activity, "Email masih kosong", Toast.LENGTH_SHORT).show()
            return false
        }
        if(profilPassword.text.toString() == "") {
            Toast.makeText(activity, "Password masih kosong", Toast.LENGTH_SHORT).show()
            return false
        }
        if(profilTanggal.text.toString() == "") {
            Toast.makeText(activity, "Tanggal lahir kosong", Toast.LENGTH_SHORT).show()
            return false
        }
        if(profilAlamat.text.toString() == "") {
            Toast.makeText(activity, "Alamat masih kosong", Toast.LENGTH_SHORT).show()
            return false
        }
        if(profilTelp.text.toString() == "") {
            Toast.makeText(activity, "Nomor telepon kosong", Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }

    private fun saveData() {
        val id_user = SP.getString("id_user", "").toString()
        val updateData = User(id_user, profilNama.text.toString(), profilEmail.text.toString(),
            profilPassword.text.toString(), profilTanggal.text.toString(), profilGender.text.toString(),
            profilAlamat.text.toString(), profilTelp.text.toString(),
            SP.getString("role", "").toString())
        val ref = FirebaseDatabase.getInstance().getReference("user")
        ref.child(id_user).setValue(updateData).addOnSuccessListener {
            val editor = SP.edit()
            editor.putString("nama", updateData.nama)
            editor.putString("email", updateData.email)
            editor.putString("password", updateData.password)
            editor.putString("tgl_lahir", updateData.tgl_lahir)
            editor.putString("gender", updateData.gender)
            editor.putString("alamat", updateData.alamat)
            editor.putString("telp", updateData.telp)
            editor.apply()

            Toast.makeText(activity, "Data berhasil disimpan", Toast.LENGTH_SHORT).show()
        }
    }
}