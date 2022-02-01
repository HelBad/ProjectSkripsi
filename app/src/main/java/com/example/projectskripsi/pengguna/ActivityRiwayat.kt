package com.example.projectskripsi.pengguna

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.projectskripsi.R
import com.example.projectskripsi.adapter.ViewholderCheckout
import com.example.projectskripsi.model.Keranjang
import com.example.projectskripsi.model.Pesanan
import com.example.projectskripsi.model.User
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.google.firebase.database.*
import java.text.DecimalFormat
import java.text.NumberFormat

class ActivityRiwayat : AppCompatActivity() {
    lateinit var SP: SharedPreferences
    lateinit var mLayoutManager: LinearLayoutManager
    lateinit var mRecyclerView: RecyclerView
    lateinit var identitasRiwayat: TextView
    lateinit var lokasiRiwayat: TextView
    lateinit var waktuRiwayat: TextView
    lateinit var keteranganRiwayat: TextView
    lateinit var subtotalRiwayat: TextView
    lateinit var ongkirRiwayat: TextView
    lateinit var totalRiwayat: TextView
    lateinit var layoutLaporanRiwayat: LinearLayout
    lateinit var laporanRiwayat: TextView

    var formatter: NumberFormat = DecimalFormat("#,###")
    var id_user = ""
    var id_keranjang = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_riwayat)

        identitasRiwayat = findViewById(R.id.identitasRiwayat)
        lokasiRiwayat = findViewById(R.id.lokasiRiwayat)
        waktuRiwayat = findViewById(R.id.waktuRiwayat)
        keteranganRiwayat = findViewById(R.id.keteranganRiwayat)
        subtotalRiwayat = findViewById(R.id.subtotalRiwayat)
        ongkirRiwayat = findViewById(R.id.ongkirRiwayat)
        totalRiwayat = findViewById(R.id.totalRiwayat)
        layoutLaporanRiwayat = findViewById(R.id.layoutLaporanRiwayat)
        laporanRiwayat = findViewById(R.id.laporanRiwayat)

        mLayoutManager = LinearLayoutManager(this)
        mRecyclerView = findViewById(R.id.recyclerRiwayat)
        mRecyclerView.setHasFixedSize(true)
        mRecyclerView.layoutManager = mLayoutManager

        SP = applicationContext.getSharedPreferences("User", Context.MODE_PRIVATE)
        loadData()
    }

    private fun loadData() {
        FirebaseDatabase.getInstance().getReference("pesanan").child(intent.getStringExtra("status").toString())
            .orderByKey().equalTo(intent.getStringExtra("id_pesanan").toString())
            .addListenerForSingleValueEvent(object: ValueEventListener {
                override fun onDataChange(datasnapshot: DataSnapshot) {
                    for (snapshot1 in datasnapshot.children) {
                        val pesanan = snapshot1.getValue(Pesanan::class.java)
                        lokasiRiwayat.text = pesanan!!.lokasi
                        waktuRiwayat.text = pesanan.waktu
                        if(pesanan.catatan == "") {
                            keteranganRiwayat.text = "-"
                        } else {
                            keteranganRiwayat.text = pesanan.catatan
                        }
                        subtotalRiwayat.text = "Rp. " + formatter.format(pesanan.subtotal.toInt()) + ",00"
                        ongkirRiwayat.text = "Rp. " + formatter.format(pesanan.ongkir.toInt()) + ",00"
                        totalRiwayat.text = "Rp. " + formatter.format(pesanan.total_bayar.toInt()) + ",00"
                        if(pesanan.keterangan == "") {
                            laporanRiwayat.text = "-"
                        } else {
                            laporanRiwayat.text = pesanan.keterangan
                        }

                        val nama = SP.getString("nama", "")
                        val telp = SP.getString("telp", "")
                        identitasRiwayat.text = "$nama ($telp)"

                        FirebaseDatabase.getInstance().getReference("keranjang").child("kosong")
                            .child(pesanan.id_user + " | " + pesanan.id_keranjang).orderByKey()
                            .equalTo(pesanan.id_keranjang).addListenerForSingleValueEvent(object: ValueEventListener {
                                override fun onDataChange(datasnapshot: DataSnapshot) {
                                    for (snapshot3 in datasnapshot.children) {
                                        val keranjang = snapshot3.getValue(Keranjang::class.java)
                                        id_keranjang = keranjang!!.id_keranjang
                                        id_user = keranjang.id_user
                                        listKeranjang()
                                    }
                                }
                                override fun onCancelled(databaseError: DatabaseError) {}
                            })
                    }
                }
                override fun onCancelled(databaseError: DatabaseError) {}
        })
    }

    private fun listKeranjang() {
        val query = FirebaseDatabase.getInstance().getReference("keranjang")
            .child("kosong").child("$id_user | $id_keranjang")
        val firebaseRecyclerAdapter = object: FirebaseRecyclerAdapter<Keranjang, ViewholderCheckout>(
            Keranjang::class.java,
            R.layout.menu_checkout,
            ViewholderCheckout::class.java,
            query
        ) {
            override fun populateViewHolder(viewHolder: ViewholderCheckout, model: Keranjang, position:Int) {
                viewHolder.setDetails(model)
            }
            override fun onCreateViewHolder(parent: ViewGroup, viewType:Int): ViewholderCheckout {
                val viewHolder = super.onCreateViewHolder(parent, viewType)
                viewHolder.setOnClickListener(object: ViewholderCheckout.ClickListener {
                    override fun onItemClick(view: View, position:Int) {}
                    override fun onItemLongClick(view: View, position:Int) {}
                })
                return viewHolder
            }
        }
        mRecyclerView.adapter = firebaseRecyclerAdapter
    }

    override fun onStart() {
        super.onStart()
        if(intent.getStringExtra("status").toString() == "dibatalkan") {
            layoutLaporanRiwayat.visibility = View.VISIBLE
        } else {
            layoutLaporanRiwayat.visibility = View.GONE
        }
    }
}