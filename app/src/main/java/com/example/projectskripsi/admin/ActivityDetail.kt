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
import com.example.projectskripsi.model.Penyakit
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.squareup.picasso.Picasso
import java.text.DecimalFormat
import java.text.NumberFormat

class ActivityDetail : AppCompatActivity() {
    lateinit var namaDetail: TextView
    lateinit var imgDetail: ImageView
    lateinit var lemakDetail: TextView
    lateinit var proteinDetail: TextView
    lateinit var kaloriDetail: TextView
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
        lemakDetail = findViewById(R.id.lemakDetail)
        proteinDetail = findViewById(R.id.proteinDetail)
        kaloriDetail = findViewById(R.id.kaloriDetail)
        karbohidratDetail = findViewById(R.id.karbohidratDetail)
        deskripsiDetail = findViewById(R.id.deskripsiDetail)
        hargaDetail = findViewById(R.id.hargaDetail)
        btnEdit = findViewById(R.id.btnEdit)
        btnHapus = findViewById(R.id.btnHapus)

        alertDialog = AlertDialog.Builder(this)
        loadData()
    }

    //Load Data Menu
    private fun loadData() {
        val query = FirebaseDatabase.getInstance().getReference("menu").orderByKey()
            .equalTo(intent.getStringExtra("id_menu").toString())
        query.addListenerForSingleValueEvent(object: ValueEventListener {
            override fun onDataChange(datasnapshot: DataSnapshot) {
                for (snapshot1 in datasnapshot.children) {
                    val allocation = snapshot1.getValue(Menu::class.java)
                    id_menu = allocation!!.id_menu
                    namaDetail.text = allocation.nama_menu
                    lemakDetail.text = allocation.lemak
                    proteinDetail.text = allocation.protein
                    kaloriDetail.text = allocation.kalori
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
                                    FirebaseDatabase.getInstance().getReference("penyakit")
                                        .orderByChild("id_menu").equalTo(intent.getStringExtra("id_menu"))
                                        .addListenerForSingleValueEvent(object: ValueEventListener {
                                            override fun onDataChange(datasnapshot: DataSnapshot) {
                                                for (snapshot1 in datasnapshot.children) {
                                                    val allocation = snapshot1.getValue(Penyakit::class.java)
                                                    val id_penyakit = allocation!!.id_penyakit
                                                    FirebaseDatabase.getInstance().getReference("penyakit")
                                                        .child(id_penyakit).removeValue()

                                                }
                                            }
                                            override fun onCancelled(databaseError: DatabaseError) {}
                                        })
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