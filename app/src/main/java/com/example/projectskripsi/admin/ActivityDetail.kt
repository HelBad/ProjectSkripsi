package com.example.projectskripsi.admin

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import com.example.projectskripsi.R
import com.example.projectskripsi.model.Menu
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.squareup.picasso.Picasso
import java.text.DecimalFormat
import java.text.NumberFormat

class ActivityDetail : AppCompatActivity() {
    lateinit var namaDetail: TextView
    lateinit var imgDetail: ImageView
    lateinit var mineralDetail: TextView
    lateinit var proteinDetail: TextView
    lateinit var lemakDetail: TextView
    lateinit var karbohidratDetail: TextView
    lateinit var deskripsiDetail: TextView
    lateinit var hargaDetail: TextView
    lateinit var btnEdit: Button
    lateinit var btnHapus: Button

    lateinit var alertDialog: AlertDialog.Builder
    var formatNumber: NumberFormat = DecimalFormat("#,###")
    var id_menu = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.admin_activity_detail)

        namaDetail = findViewById(R.id.namaDetail)
        imgDetail = findViewById(R.id.imgDetail)
        mineralDetail = findViewById(R.id.mineralDetail)
        proteinDetail = findViewById(R.id.proteinDetail)
        lemakDetail = findViewById(R.id.lemakDetail)
        karbohidratDetail = findViewById(R.id.karbohidratDetail)
        deskripsiDetail = findViewById(R.id.deskripsiDetail)
        hargaDetail = findViewById(R.id.hargaDetail)
        btnEdit = findViewById(R.id.btnEdit)
        btnHapus = findViewById(R.id.btnHapus)

        alertDialog = AlertDialog.Builder(this)
        loadData()
    }

    private fun loadData() {
        val query = FirebaseDatabase.getInstance().getReference("menu").orderByKey()
            .equalTo(intent.getStringExtra("id_menu").toString())
        query.addListenerForSingleValueEvent(object: ValueEventListener {
            override fun onDataChange(datasnapshot: DataSnapshot) {
                for (snapshot1 in datasnapshot.children) {
                    val allocation = snapshot1.getValue(Menu::class.java)
                    id_menu = allocation!!.id_menu
                    namaDetail.text = allocation.nama_menu
                    mineralDetail.text = allocation.mineral
                    proteinDetail.text = allocation.protein
                    lemakDetail.text = allocation.lemak
                    karbohidratDetail.text = allocation.karbohidrat
                    deskripsiDetail.text = allocation.deskripsi
                    hargaDetail.text = "Rp. " + formatNumber.format(allocation.harga.toInt()) + ",00"
                    Picasso.get().load(allocation.gambar).into(imgDetail)

                    btnEdit.setOnClickListener {
                        val intent = Intent(this@ActivityDetail, ActivityEdit::class.java)
                        intent.putExtra("id_menu", id_menu)
                        startActivity(intent)
                    }
                    btnHapus.setOnClickListener {
                        alertDialog.setMessage("Apakah anda ingin menghapus menu ini ?")
                            .setCancelable(false)
                            .setPositiveButton("YA", object: DialogInterface.OnClickListener {
                                override fun onClick(dialog: DialogInterface, id:Int) {
                                    FirebaseDatabase.getInstance().getReference("menu")
                                        .child(intent.getStringExtra("id_menu").toString()).removeValue()
                                    FirebaseStorage.getInstance().getReference("menu")
                                        .child(intent.getStringExtra("id_menu").toString()).delete()
                                    finish()
                                }
                            })
                            .setNegativeButton("TIDAK", object: DialogInterface.OnClickListener {
                                override fun onClick(dialog: DialogInterface, id:Int) {
                                    dialog.cancel()
                                }
                            }).create().show()
                    }
                }
            }
            override fun onCancelled(databaseError: DatabaseError) {}
        })
    }
}