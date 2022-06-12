package com.example.projectskripsi.admin

import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.projectskripsi.R
import com.example.projectskripsi.model.Menu
import com.example.projectskripsi.model.Penyakit
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlin.math.absoluteValue

class ActivityEdit : AppCompatActivity() {
    lateinit var namaMenu: EditText
    lateinit var deskripsiMenu: EditText
    lateinit var hargaMenu: EditText
    lateinit var gambarMenu: TextView
    lateinit var lemakMenu: EditText
    lateinit var proteinMenu: EditText
    lateinit var kaloriMenu: EditText
    lateinit var karbohidratMenu: EditText
    lateinit var btnSimpan: Button

    lateinit var alertDialog: AlertDialog.Builder
    lateinit var databaseReference: DatabaseReference
    lateinit var storageReference: StorageReference
    lateinit var databaseAi: DatabaseReference
    lateinit var uri: Uri
    var url: Uri? = null
    var statusMenu = ""
    var id_menu = ""
    var id_penyakit = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.admin_activity_edit)

        namaMenu = findViewById(R.id.namaMenu)
        deskripsiMenu = findViewById(R.id.deskripsiMenu)
        hargaMenu = findViewById(R.id.hargaMenu)
        gambarMenu = findViewById(R.id.gambarMenu)
        lemakMenu = findViewById(R.id.lemakMenu)
        proteinMenu = findViewById(R.id.proteinMenu)
        kaloriMenu = findViewById(R.id.kaloriMenu)
        karbohidratMenu = findViewById(R.id.karbohidratMenu)
        btnSimpan = findViewById(R.id.btnSimpan)

        alertDialog = AlertDialog.Builder(this)
        databaseReference = FirebaseDatabase.getInstance().getReference("menu")
        storageReference = FirebaseStorage.getInstance().getReference("menu")
        databaseAi = FirebaseDatabase.getInstance().getReference("penyakit")
        loadMenu()

        gambarMenu.setOnClickListener {
            val intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.type = "image/*"
            startActivityForResult(intent, 0)
        }

        btnSimpan.setOnClickListener {
            alertDialog.setMessage("Apakah anda ingin menyimpan data menu ?")
                .setCancelable(false)
                .setPositiveButton("YA", object: DialogInterface.OnClickListener {
                    override fun onClick(dialog: DialogInterface, id:Int) {
                        if(validate()) {
                            tambahData()
                            rekomendasi()
                            val intent = Intent(this@ActivityEdit, ActivityUtama::class.java)
                            startActivity(intent)
                            finish()
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

    //Set Gambar Menu
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode == RESULT_OK) {
            if(requestCode == 0) {
                if(statusMenu == "ada") {
                    id_menu = intent.getStringExtra("id_menu").toString()
                } else {
                    id_menu = databaseReference.push().key.toString()
                }
                uri = data!!.data!!
                var mStorage = storageReference.child(id_menu)
                try {
                    mStorage.putFile(uri).addOnFailureListener {}.addOnSuccessListener {
                        mStorage.downloadUrl.addOnCompleteListener { taskSnapshot ->
                            url = taskSnapshot.result
                            gambarMenu.text = url.toString()
                        }}
                } catch(ex:Exception) {
                    Toast.makeText(this, ex.toString(), Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    //Load Data Menu
    private fun loadMenu() {
        databaseReference.orderByChild("id_menu").equalTo(intent.getStringExtra("id_menu"))
            .addValueEventListener(object: ValueEventListener {
                override fun onDataChange(datasnapshot: DataSnapshot) {
                    for (snapshot1 in datasnapshot.children) {
                        val allocation = snapshot1.getValue(Menu::class.java)
                        namaMenu.text = Editable.Factory.getInstance().newEditable(allocation!!.nama_menu)
                        deskripsiMenu.text = Editable.Factory.getInstance().newEditable(allocation.deskripsi)
                        lemakMenu.text = Editable.Factory.getInstance().newEditable(allocation.lemak)
                        proteinMenu.text = Editable.Factory.getInstance().newEditable(allocation.protein)
                        kaloriMenu.text = Editable.Factory.getInstance().newEditable(allocation.kalori)
                        karbohidratMenu.text = Editable.Factory.getInstance().newEditable(allocation.karbohidrat)
                        hargaMenu.text = Editable.Factory.getInstance().newEditable(allocation.harga)
                        gambarMenu.text = allocation.gambar
                        statusMenu = "ada"
                    }
                }
                override fun onCancelled(databaseError: DatabaseError) {}
            })
    }

    //Validasi Data Menu
    private fun validate(): Boolean {
        if(namaMenu.text.toString() == "") {
            Toast.makeText(this, "Nama menu masih kosong", Toast.LENGTH_SHORT).show()
            return false
        }
        if(deskripsiMenu.text.toString() == "") {
            Toast.makeText(this, "Deskripsi masih kosong", Toast.LENGTH_SHORT).show()
            return false
        }
        if(hargaMenu.text.toString() == "") {
            Toast.makeText(this, "Harga masih kosong", Toast.LENGTH_SHORT).show()
            return false
        }
        if(gambarMenu.text.toString() == "") {
            Toast.makeText(this, "Gambar masih kosong", Toast.LENGTH_SHORT).show()
            return false
        }
        if(lemakMenu.text.toString() == "") {
            Toast.makeText(this, "Jumlah lemak kosong", Toast.LENGTH_SHORT).show()
            return false
        }
        if(proteinMenu.text.toString() == "") {
            Toast.makeText(this, "Jumlah protein kosong", Toast.LENGTH_SHORT).show()
            return false
        }
        if(kaloriMenu.text.toString() == "") {
            Toast.makeText(this, "Jumlah kalori kosong", Toast.LENGTH_SHORT).show()
            return false
        }
        if(karbohidratMenu.text.toString() == "") {
            Toast.makeText(this, "Jumlah karbohidrat kosong", Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }

    //Simpan Data Menu
    private fun tambahData() {
        if(statusMenu == "ada") {
            id_menu = intent.getStringExtra("id_menu").toString()
            databaseReference.child(id_menu).child("id_menu").setValue(id_menu)
            databaseReference.child(id_menu).child("nama_menu").setValue(namaMenu.text.toString())
            databaseReference.child(id_menu).child("deskripsi").setValue(deskripsiMenu.text.toString())
            databaseReference.child(id_menu).child("lemak").setValue(lemakMenu.text.toString())
            databaseReference.child(id_menu).child("protein").setValue(proteinMenu.text.toString())
            databaseReference.child(id_menu).child("kalori").setValue(kaloriMenu.text.toString())
            databaseReference.child(id_menu).child("karbohidrat").setValue(karbohidratMenu.text.toString())
            databaseReference.child(id_menu).child("harga").setValue(hargaMenu.text.toString())
            databaseReference.child(id_menu).child("gambar").setValue(gambarMenu.text.toString())
        } else {
            id_menu  = databaseReference.push().key.toString()
            val addData = Menu(id_menu, namaMenu.text.toString(), deskripsiMenu.text.toString(),
                lemakMenu.text.toString(), proteinMenu.text.toString(), kaloriMenu.text.toString(),
                karbohidratMenu.text.toString(), hargaMenu.text.toString(), gambarMenu.text.toString())
            databaseReference.child(id_menu).setValue(addData)
        }
    }

    //AI Rekomendasi Menu
    private fun rekomendasi() {
        val jumlahData = 8
        val lemakSehat = 1
        val proteinSehat = 1
        val kaloriSehat = 1
        val karbohidratSehat = 1
        var lemakK1 = 0
        var proteinK1 = 0
        var kaloriK1 = 0
        var karbohidratK1 = 0
        var lemakK2 = 0
        var proteinK2 = 0
        var kaloriK2 = 0
        var karbohidratK2 = 0
        var lemakK3 = 0
        var proteinK3 = 0
        var kaloriK3 = 0
        var karbohidratK3 = 0
        var lemakK4 = 0
        var proteinK4 = 0
        var kaloriK4 = 0
        var karbohidratK4 = 0
        var lemakK5 = 0
        var proteinK5 = 0
        var kaloriK5 = 0
        var karbohidratK5 = 0

        //Kandungan Lemak
        when {
            lemakMenu.text.toString().toDouble() < 10.0 -> {
                lemakK1 = 1
                lemakK2 = 1
                lemakK3 = 0
                lemakK4 = 1
                lemakK5 = 1
            }
            lemakMenu.text.toString().toDouble() in 10.0..25.0 -> {
                lemakK1 = 1
                lemakK2 = 1
                lemakK3 = 1
                lemakK4 = 1
                lemakK5 = 1
            }
            lemakMenu.text.toString().toDouble() > 25.0 -> {
                lemakK1 = 1
                lemakK2 = 0
                lemakK3 = 1
                lemakK4 = 0
                lemakK5 = 0
            }
        }

        //Kandungan Protein
        when {
            proteinMenu.text.toString().toDouble() < 8.8 -> {
                proteinK1 = 1
                proteinK2 = 0
                proteinK3 = 0
                proteinK4 = 1
                proteinK5 = 1
            }
            proteinMenu.text.toString().toDouble() in 8.8..22.0 -> {
                proteinK1 = 1
                proteinK2 = 1
                proteinK3 = 1
                proteinK4 = 1
                proteinK5 = 1
            }
            proteinMenu.text.toString().toDouble() > 22.0 -> {
                proteinK1 = 0
                proteinK2 = 1
                proteinK3 = 1
                proteinK4 = 0
                proteinK5 = 0
            }
        }

        //Kandungan Kalori
        when {
            kaloriMenu.text.toString().toDouble() < 300.0 -> {
                kaloriK1 = 0
                kaloriK2 = 0
                kaloriK3 = 1
                kaloriK4 = 0
                kaloriK5 = 0
            }
            kaloriMenu.text.toString().toDouble() in 300.0..750.0 -> {
                kaloriK1 = 1
                kaloriK2 = 1
                kaloriK3 = 1
                kaloriK4 = 1
                kaloriK5 = 1
            }
            kaloriMenu.text.toString().toDouble() > 750.0 -> {
                kaloriK1 = 0
                kaloriK2 = 0
                kaloriK3 = 1
                kaloriK4 = 0
                kaloriK5 = 0
            }
        }

        //Kandungan Karbohidrat
        when {
            karbohidratMenu.text.toString().toDouble() < 30.0 -> {
                karbohidratK1 = 0
                karbohidratK2 = 0
                karbohidratK3 = 1
                karbohidratK4 = 0
                karbohidratK5 = 0
            }
            karbohidratMenu.text.toString().toDouble() in 30.0..75.0 -> {
                karbohidratK1 = 1
                karbohidratK2 = 1
                karbohidratK3 = 1
                karbohidratK4 = 1
                karbohidratK5 = 1
            }
            karbohidratMenu.text.toString().toDouble() > 75.0 -> {
                karbohidratK1 = 0
                karbohidratK2 = 0
                karbohidratK3 = 1
                karbohidratK4 = 0
                karbohidratK5 = 0
            }
        }

        //Slope One Algorithm untuk Sehat
        val devSehat = (((lemakSehat - lemakSehat) + (proteinSehat - proteinSehat) + (kaloriSehat - kaloriSehat)
                + (karbohidratSehat - karbohidratSehat)).absoluteValue).toDouble() / jumlahData.toDouble()
        val pSehat = devSehat + (lemakSehat + proteinSehat + kaloriSehat + karbohidratSehat).toDouble()

        //Slope One Algorithm untuk Diabetes
        val devK1 = (((lemakK1 - lemakSehat) + (proteinK1 - proteinSehat) + (kaloriK1 - kaloriSehat)
                + (karbohidratK1 - karbohidratSehat)).absoluteValue).toDouble() / jumlahData.toDouble()
        val pK1 = devK1 + (lemakK1 + proteinK1 + kaloriK1 + karbohidratK1).toDouble()

        //Slope One Algorithm untuk Jantung
        val devK2 = (((lemakK2 - lemakSehat) + (proteinK2 - proteinSehat) + (kaloriK2 - kaloriSehat)
                + (karbohidratK2 - karbohidratSehat)).absoluteValue).toDouble() / jumlahData.toDouble()
        val pK2 = devK2 + (lemakK2 + proteinK2 + kaloriK2 + karbohidratK2).toDouble()

        //Slope One Algorithm untuk Kelelahan
        val devK3 = (((lemakK3 - lemakSehat) + (proteinK3 - proteinSehat) + (kaloriK3 - kaloriSehat)
                + (karbohidratK3 - karbohidratSehat)).absoluteValue).toDouble() / jumlahData.toDouble()
        val pK3 = devK3 + (lemakK3 + proteinK3 + kaloriK3 + karbohidratK3).toDouble()

        //Slope One Algorithm untuk Obesitas
        val devK4 = (((lemakK4 - lemakSehat) + (proteinK4 - proteinSehat) + (kaloriK4 - kaloriSehat)
                + (karbohidratK4 - karbohidratSehat)).absoluteValue).toDouble() / jumlahData.toDouble()
        val pK4 = devK4 + (lemakK4 + proteinK4 + kaloriK4 + karbohidratK4).toDouble()

        //Slope One Algorithm untuk Sembelit
        val devK5 = (((lemakK5 - lemakSehat) + (proteinK5 - proteinSehat) + (kaloriK5 - kaloriSehat)
                + (karbohidratK5 - karbohidratSehat)).absoluteValue).toDouble() / jumlahData.toDouble()
        val pK5 = devK5 + (lemakK5 + proteinK5 + kaloriK5 + karbohidratK5).toDouble()

        //Tambah Data
        if(statusMenu == "ada") {
            databaseAi.orderByChild("id_menu").equalTo(intent.getStringExtra("id_menu"))
                .addListenerForSingleValueEvent(object: ValueEventListener {
                    override fun onDataChange(datasnapshot: DataSnapshot) {
                        for (snapshot1 in datasnapshot.children) {
                            val allocation = snapshot1.getValue(Penyakit::class.java)
                            id_penyakit = allocation!!.id_penyakit
                            databaseAi.child(id_penyakit).child("id_penyakit").setValue(id_penyakit)
                            databaseAi.child(id_penyakit).child("id_menu").setValue(id_menu)
                            databaseAi.child(id_penyakit).child("sehat").setValue(pSehat.toString())
                            databaseAi.child(id_penyakit).child("diabetes").setValue(pK1.toString())
                            databaseAi.child(id_penyakit).child("jantung").setValue(pK2.toString())
                            databaseAi.child(id_penyakit).child("kelelahan").setValue(pK3.toString())
                            databaseAi.child(id_penyakit).child("obesitas").setValue(pK4.toString())
                            databaseAi.child(id_penyakit).child("sembelit").setValue(pK5.toString())
                        }
                    }
                    override fun onCancelled(databaseError: DatabaseError) {}
                })
        } else {
            id_penyakit  = databaseAi.push().key.toString()
            val addData = Penyakit(id_penyakit, id_menu, pSehat.toString(), pK1.toString(),
                pK2.toString(), pK3.toString(), pK4.toString(), pK5.toString())
            databaseAi.child(id_penyakit).setValue(addData)
        }
    }
}