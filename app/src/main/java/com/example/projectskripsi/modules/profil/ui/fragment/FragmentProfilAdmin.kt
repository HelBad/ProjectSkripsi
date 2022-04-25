package com.example.projectskripsi.modules.profil.ui.fragment

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AlertDialog
import com.example.projectskripsi.modules.auth.ui.ActivityLogin
import com.example.projectskripsi.R
import com.example.projectskripsi.modules.auth.domain.entities.User
import com.google.firebase.database.FirebaseDatabase

class FragmentProfilAdmin : Fragment() {
    lateinit var SP: SharedPreferences
    lateinit var alertDialog: AlertDialog.Builder
    lateinit var profilEmail: EditText
    lateinit var profilPassword: EditText
    lateinit var btnSimpan: Button
    lateinit var btnKeluar: Button

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.admin_fragment_profil, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        profilEmail = requireActivity().findViewById(R.id.profilEmail)
        profilPassword = requireActivity().findViewById(R.id.profilPassword)
        btnSimpan = requireActivity().findViewById(R.id.btnSimpan)
        btnKeluar = requireActivity().findViewById(R.id.btnKeluar)

        alertDialog = AlertDialog.Builder(requireActivity())
        SP = requireActivity().applicationContext.getSharedPreferences("User", Context.MODE_PRIVATE)
        profilEmail.setText(SP.getString("email", ""))
        profilPassword.setText(SP.getString("password", ""))

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

    //Validasi Data User
    private fun validate(): Boolean {
        if(profilEmail.text.toString() == "") {
            Toast.makeText(activity, "Email masih kosong", Toast.LENGTH_SHORT).show()
            return false
        }
        if(profilPassword.text.toString() == "") {
            Toast.makeText(activity, "Password masih kosong", Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }

    //Simpan Perubahan Data User
    private fun saveData() {
        val id_user = SP.getString("id_user", "").toString()
        val updateData = User(id_user, SP.getString("nama", "").toString(), profilEmail.text.toString(),
            profilPassword.text.toString(), SP.getString("tgl_lahir", "").toString(),
            SP.getString("gender", "").toString(), SP.getString("alamat", "").toString(),
            SP.getString("telp", "").toString(), SP.getString("level", "").toString())
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