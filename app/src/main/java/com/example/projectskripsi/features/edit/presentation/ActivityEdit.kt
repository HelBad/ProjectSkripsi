package com.example.projectskripsi.features.edit.presentation

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
import com.example.projectskripsi.features.beranda.domain.entities.Menu
import com.example.projectskripsi.features.beranda.domain.entities.Penyakit
import com.example.projectskripsi.features.beranda.presentation.ActivityUtamaAdmin
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

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
                            val intent = Intent(this@ActivityEdit, ActivityUtamaAdmin::class.java)
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
                        namaMenu.text = Editable.Factory.getInstance().newEditable(allocation?.namaMenu)
                        deskripsiMenu.text = Editable.Factory.getInstance().newEditable(allocation?.deskripsi)
                        lemakMenu.text = Editable.Factory.getInstance().newEditable(allocation?.lemak)
                        proteinMenu.text = Editable.Factory.getInstance().newEditable(allocation?.protein)
                        kaloriMenu.text = Editable.Factory.getInstance().newEditable(allocation?.kalori)
                        karbohidratMenu.text = Editable.Factory.getInstance().newEditable(allocation?.karbohidrat)
                        hargaMenu.text = Editable.Factory.getInstance().newEditable(allocation?.harga)
                        gambarMenu.text = allocation?.gambar
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
        val jumlahData = 4
        var lemak = 0
        var protein = 0
        var kalori = 0
        var karbohidrat = 0

        val lemakSehat = 0
        val proteinSehat = 0
        val kaloriSehat = 0
        val karbohidratSehat = 0
        val lemakPenyakit1 = 1
        val proteinPenyakit1 = 1
        val kaloriPenyakit1 = 1
        val karbohidratPenyakit1 = 1
        val lemakPenyakit2 = -1
        val proteinPenyakit2 = -1
        val kaloriPenyakit2 = -1
        val karbohidratPenyakit2 = -1

        when {
            lemakMenu.text.toString().toInt() < 11 -> { lemak = -1 }
            lemakMenu.text.toString().toInt() in 11..22 -> { lemak = 0 }
            lemakMenu.text.toString().toInt() > 22 -> { lemak = 1 }
        }
        when {
            proteinMenu.text.toString().toInt() < 7 -> { protein = -1 }
            proteinMenu.text.toString().toInt() in 7..20 -> { protein = 0 }
            proteinMenu.text.toString().toInt() > 20 -> { protein = 1 }
        }
        when {
            kaloriMenu.text.toString().toInt() < 350 -> { kalori = -1 }
            kaloriMenu.text.toString().toInt() in 350..650 -> { kalori = 0 }
            kaloriMenu.text.toString().toInt() > 650 -> { kalori = 1 }
        }
        when {
            karbohidratMenu.text.toString().toInt() < 35 -> { karbohidrat = -1 }
            karbohidratMenu.text.toString().toInt() in 35..65 -> { karbohidrat = 0 }
            karbohidratMenu.text.toString().toInt() > 65 -> { karbohidrat = 1 }
        }

        val sehat = (jumlahData * ((lemakSehat - lemak) + (proteinSehat - protein)
                + (kaloriSehat - kalori) + (karbohidratSehat - karbohidrat)) / jumlahData)
        val diabetes = (jumlahData * ((lemakPenyakit1 - lemak) + (proteinPenyakit1 - protein)
                + (kaloriPenyakit1 - kalori) + (karbohidratPenyakit1 - karbohidrat)) / jumlahData)
        val obesitas = (jumlahData * ((lemakPenyakit1 - lemak) + (proteinPenyakit1 - protein)
                + (kaloriPenyakit1 - kalori) + (karbohidratPenyakit1 - karbohidrat)) / jumlahData)
        val anemia = (jumlahData * ((lemakPenyakit2 - lemak) + (proteinPenyakit2 - protein)
                + (kaloriPenyakit2 - kalori) + (karbohidratPenyakit2 - karbohidrat)) / jumlahData)

        if(statusMenu == "ada") {
            databaseAi.orderByChild("id_menu").equalTo(intent.getStringExtra("id_menu"))
                .addListenerForSingleValueEvent(object: ValueEventListener {
                    override fun onDataChange(datasnapshot: DataSnapshot) {
                        for (snapshot1 in datasnapshot.children) {
                            val allocation = snapshot1.getValue(Penyakit::class.java)
                            id_penyakit = allocation?.idPenyakit.toString()
                            databaseAi.child(id_penyakit).child("id_penyakit").setValue(id_penyakit)
                            databaseAi.child(id_penyakit).child("id_menu").setValue(id_menu)
                            databaseAi.child(id_penyakit).child("sehat").setValue(sehat.toString())
                            databaseAi.child(id_penyakit).child("diabetes").setValue(diabetes.toString())
                            databaseAi.child(id_penyakit).child("obesitas").setValue(obesitas.toString())
                            databaseAi.child(id_penyakit).child("anemia").setValue(anemia.toString())
                        }
                    }
                    override fun onCancelled(databaseError: DatabaseError) {}
                })
        } else {
            id_penyakit  = databaseAi.push().key.toString()
            val addData = Penyakit(id_penyakit, id_menu, sehat.toString(), diabetes.toString(), obesitas.toString(), anemia.toString())
            databaseAi.child(id_penyakit).setValue(addData)
        }
    }
}