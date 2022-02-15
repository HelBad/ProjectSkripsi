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
    lateinit var uri: Uri
    var url: Uri? = null
    var statusMenu = ""
    var id_menu = ""

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

    private fun tambahData() {
        if(statusMenu == "ada") {
            id_menu = intent.getStringExtra("id_menu").toString()
            databaseReference.child(id_menu).child("nama_menu").setValue(namaMenu.text.toString())
            databaseReference.child(id_menu).child("deskripsi").setValue(deskripsiMenu.text.toString())
            databaseReference.child(id_menu).child("lemak").setValue(lemakMenu.text.toString())
            databaseReference.child(id_menu).child("protein").setValue(proteinMenu.text.toString())
            databaseReference.child(id_menu).child("kalori").setValue(kaloriMenu.text.toString())
            databaseReference.child(id_menu).child("karbohidrat").setValue(karbohidratMenu.text.toString())
            databaseReference.child(id_menu).child("harga").setValue(hargaMenu.text.toString())
            databaseReference.child(id_menu).child("gambar").setValue(gambarMenu.text.toString())
        } else {
            val addData = Menu(id_menu, namaMenu.text.toString(), deskripsiMenu.text.toString(),
                lemakMenu.text.toString(), proteinMenu.text.toString(), kaloriMenu.text.toString(),
                karbohidratMenu.text.toString(), hargaMenu.text.toString(), gambarMenu.text.toString())
            databaseReference.child(id_menu).setValue(addData)
        }
    }
}