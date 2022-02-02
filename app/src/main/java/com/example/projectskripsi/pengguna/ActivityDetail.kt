package com.example.projectskripsi.pengguna

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.view.View
import android.widget.*
import com.example.projectskripsi.R
import com.example.projectskripsi.model.Keranjang
import com.example.projectskripsi.model.Menu
import com.google.firebase.database.*
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
    lateinit var jumlahDetail: EditText
    lateinit var kurangDetail: ImageView
    lateinit var tambahDetail: ImageView
    lateinit var btnPesan: Button
    lateinit var btnHapus: Button

    lateinit var databaseDetail: DatabaseReference
    lateinit var databaseRef: DatabaseReference
    lateinit var SP: SharedPreferences
    var formatNumber: NumberFormat = DecimalFormat("#,###")
    var countJumlah = 0
    var id_keranjang = ""
    var id_menu = ""
    var harga_menu = 0
    var statusKeranjang = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        namaDetail = findViewById(R.id.namaDetail)
        imgDetail = findViewById(R.id.imgDetail)
        mineralDetail = findViewById(R.id.mineralDetail)
        proteinDetail = findViewById(R.id.proteinDetail)
        lemakDetail = findViewById(R.id.lemakDetail)
        karbohidratDetail = findViewById(R.id.karbohidratDetail)
        deskripsiDetail = findViewById(R.id.deskripsiDetail)
        hargaDetail = findViewById(R.id.hargaDetail)
        jumlahDetail = findViewById(R.id.jumlahDetail)
        kurangDetail = findViewById(R.id.kurangDetail)
        tambahDetail = findViewById(R.id.tambahDetail)
        btnPesan = findViewById(R.id.btnPesan)
        btnHapus = findViewById(R.id.btnHapus)

        SP = applicationContext.getSharedPreferences("User", Context.MODE_PRIVATE)
        databaseRef = FirebaseDatabase.getInstance().getReference("keranjang")
            .child("ready").child(SP.getString("id_user", "").toString())
        loadData()
    }

    private fun loadData() {
        databaseDetail = FirebaseDatabase.getInstance().getReference("menu")
        val query = databaseDetail.orderByKey().equalTo(intent.getStringExtra("id_menu").toString())
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
                    harga_menu = allocation.harga.toInt()
                    hargaDetail.text = "Rp. " + formatNumber.format(allocation.harga.toInt()) + ",00"
                    Picasso.get().load(allocation.gambar).into(imgDetail)

                    cekData()
                    setJumlah()
                    btnPesan.setOnClickListener {
                        buatPesanan()
                        finish()
                    }
                    btnHapus.setOnClickListener {
                        databaseRef.child(id_keranjang).removeValue()
                        finish()
                    }
                }
            }
            override fun onCancelled(databaseError: DatabaseError) {}
        })
    }

    private fun buatPesanan() {
        val id_user = SP.getString("id_user", "").toString().trim()
        val total = (harga_menu * jumlahDetail.text.toString().toInt()).toString()

        if(statusKeranjang == "ready") {
            val jumlah = jumlahDetail.text.toString()
            databaseRef.child(id_keranjang).child("jumlah").setValue(jumlah)
            databaseRef.child(id_keranjang).child("total").setValue(total)
        } else {
            id_keranjang  = databaseRef.push().key.toString()
            val addData = Keranjang(id_keranjang, id_user, id_menu, jumlahDetail.text.toString(), total)
            databaseRef.child(id_keranjang).setValue(addData)
        }
    }

    private fun cekData() {
        databaseRef.orderByChild("id_menu").equalTo(id_menu).addValueEventListener( object : ValueEventListener {
            override fun onDataChange(datasnapshot: DataSnapshot) {
                if(datasnapshot.exists()) {
                    for (snapshot1 in datasnapshot.children) {
                        val allocation = snapshot1.getValue(Keranjang::class.java)
                        if(allocation!!.id_keranjang.isNotEmpty()) {
                            jumlahDetail.text = Editable.Factory.getInstance().newEditable(allocation.jumlah)
                            id_keranjang = allocation.id_keranjang
                            statusKeranjang = "ready"
                        }
                    }
                }
            }
            override fun onCancelled(databaseError: DatabaseError) { }
        })
    }

    private fun setJumlah() {
        btnPesan.visibility = View.INVISIBLE
        btnHapus.visibility = View.GONE
        tambahDetail.setOnClickListener {
            countJumlah = jumlahDetail.text.toString().toInt()
            countJumlah++
            jumlahDetail.setText(countJumlah.toString())
            btnPesan.visibility = View.VISIBLE
            btnHapus.visibility = View.GONE
        }
        kurangDetail.setOnClickListener {
            if (jumlahDetail.text.toString() == "0") {
                if(statusKeranjang == "ready") {
                    btnHapus.visibility = View.VISIBLE
                    btnPesan.visibility = View.GONE
                } else {
                    btnPesan.visibility = View.INVISIBLE
                    btnHapus.visibility = View.GONE
                }
            } else if (jumlahDetail.text.toString() == "1") {
                countJumlah = jumlahDetail.text.toString().toInt()
                countJumlah--
                jumlahDetail.setText(countJumlah.toString())
                if(statusKeranjang == "ready") {
                    btnHapus.visibility = View.VISIBLE
                    btnPesan.visibility = View.GONE
                } else {
                    btnPesan.visibility = View.INVISIBLE
                    btnHapus.visibility = View.GONE
                }
            } else {
                countJumlah = jumlahDetail.text.toString().toInt()
                countJumlah--
                jumlahDetail.setText(countJumlah.toString())
                btnPesan.visibility = View.VISIBLE
                btnHapus.visibility = View.GONE
            }
        }
    }
}