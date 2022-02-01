package com.example.projectskripsi.admin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.example.projectskripsi.R
import com.example.projectskripsi.model.Menu
import com.google.firebase.database.*
import com.squareup.picasso.Picasso
import java.text.DecimalFormat
import java.text.NumberFormat

class ActivityDetail : AppCompatActivity() {
    lateinit var namaDetail: TextView
    lateinit var imgDetail: ImageView
    lateinit var lemakDetail: TextView
    lateinit var proteinDetail: TextView
    lateinit var karbohidratDetail: TextView
    lateinit var seratDetail: TextView
    lateinit var kaloriDetail: TextView
    lateinit var kolesterolDetail: TextView
    lateinit var deskripsiDetail: TextView
    lateinit var hargaDetail: TextView
    lateinit var btnEdit: Button
    lateinit var btnHapus: Button

    var formatNumber: NumberFormat = DecimalFormat("#,###")
    var id_menu = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.admin_activity_detail)

        namaDetail = findViewById(R.id.namaDetail)
        imgDetail = findViewById(R.id.imgDetail)
        lemakDetail = findViewById(R.id.lemakDetail)
        proteinDetail = findViewById(R.id.proteinDetail)
        karbohidratDetail = findViewById(R.id.karbohidratDetail)
        seratDetail = findViewById(R.id.seratDetail)
        kaloriDetail = findViewById(R.id.kaloriDetail)
        kolesterolDetail = findViewById(R.id.kolesterolDetail)
        deskripsiDetail = findViewById(R.id.deskripsiDetail)
        hargaDetail = findViewById(R.id.hargaDetail)
        btnEdit = findViewById(R.id.btnEdit)
        btnHapus = findViewById(R.id.btnHapus)

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
                    lemakDetail.text = allocation.lemak_menu
                    proteinDetail.text = allocation.protein_menu
                    karbohidratDetail.text = allocation.karbohidrat_menu
                    seratDetail.text = allocation.serat_menu
                    kaloriDetail.text = allocation.kalori_menu
                    kolesterolDetail.text = allocation.kolesterol_menu
                    deskripsiDetail.text = allocation.deskripsi
                    hargaDetail.text = "Rp. " + formatNumber.format(allocation.harga.toInt()) + ",00"
                    Picasso.get().load(allocation.gambar).into(imgDetail)

                    btnEdit.setOnClickListener {
                        val intent = Intent(this@ActivityDetail, ActivityEdit::class.java)
                        startActivity(intent)
                    }
                    btnHapus.setOnClickListener {
//                        FirebaseDatabase.getInstance().getReference("menu")
//                            .child(intent.getStringExtra("id_menu").toString()).removeValue()
                    }
                }
            }
            override fun onCancelled(databaseError: DatabaseError) {}
        })
    }
}