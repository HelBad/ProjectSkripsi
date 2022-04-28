package com.example.projectskripsi.modules.detail.presentation

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.view.View
import android.widget.*
import com.example.projectskripsi.R
import com.example.projectskripsi.modules.checkout.domain.entities.Keranjang
import com.example.projectskripsi.modules.detail.presentation.viewmodel.DetailViewModel
import com.example.projectskripsi.utils.Rupiah
import com.google.firebase.database.*
import com.squareup.picasso.Picasso
import org.koin.android.viewmodel.ext.android.viewModel
import java.text.DecimalFormat
import java.text.NumberFormat

class ActivityDetailUser : AppCompatActivity() {
    private val detailViewModel: DetailViewModel by viewModel()

    lateinit var namaDetail: TextView
    lateinit var imgDetail: ImageView
    lateinit var lemakDetail: TextView
    lateinit var proteinDetail: TextView
    lateinit var kaloriDetail: TextView
    lateinit var karbohidratDetail: TextView
    lateinit var deskripsiDetail: TextView
    lateinit var hargaDetail: TextView
    lateinit var jumlahDetail: EditText
    lateinit var kurangDetail: ImageView
    lateinit var tambahDetail: ImageView
    lateinit var btnPesan: Button
    lateinit var btnHapus: Button

    lateinit var databaseRef: DatabaseReference
    lateinit var sp: SharedPreferences
    var countJumlah = 0
    var id_keranjang = ""
    var idMenu = ""
    var harga_menu = 0
    var statusKeranjang = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        namaDetail = findViewById(R.id.namaDetail)
        imgDetail = findViewById(R.id.imgDetail)
        lemakDetail = findViewById(R.id.lemakDetail)
        proteinDetail = findViewById(R.id.proteinDetail)
        kaloriDetail = findViewById(R.id.kaloriDetail)
        karbohidratDetail = findViewById(R.id.karbohidratDetail)
        deskripsiDetail = findViewById(R.id.deskripsiDetail)
        hargaDetail = findViewById(R.id.hargaDetail)
        jumlahDetail = findViewById(R.id.jumlahDetail)
        kurangDetail = findViewById(R.id.kurangDetail)
        tambahDetail = findViewById(R.id.tambahDetail)
        btnPesan = findViewById(R.id.btnPesan)
        btnHapus = findViewById(R.id.btnHapus)

        sp = applicationContext.getSharedPreferences("User", Context.MODE_PRIVATE)
        databaseRef = FirebaseDatabase.getInstance().getReference("keranjang")
            .child("ready").child(sp.getString("id_user", "").toString())
        loadData()
    }

    //Load Data Pesanan
    private fun loadData() {
        detailViewModel.getDetailMenu(intent.getStringExtra("id_menu").toString())
            .observe(this@ActivityDetailUser) { res ->
                if (res.data != null) {
                    val detail = res.data

                    idMenu = detail.idMenu.toString()
                    namaDetail.text = detail.namaMenu
                    lemakDetail.text = detail.lemak
                    proteinDetail.text = detail.protein
                    kaloriDetail.text = detail.kalori
                    karbohidratDetail.text = detail.karbohidrat
                    deskripsiDetail.text = detail.deskripsi
                    harga_menu = detail.harga?.toInt()!!
                    if (detail.harga != null) {
                        hargaDetail.text = Rupiah.format(detail.harga!!.toInt())
                    }
                    Picasso.get().load(detail.gambar).into(imgDetail)

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
    }

    //Save Data Pesanan
    private fun buatPesanan() {
        val id_user = sp.getString("id_user", "").toString().trim()
        val total = (harga_menu * jumlahDetail.text.toString().toInt()).toString()

        if(statusKeranjang == "ready") {
            val jumlah = jumlahDetail.text.toString()
            databaseRef.child(id_keranjang).child("jumlah").setValue(jumlah)
            databaseRef.child(id_keranjang).child("total").setValue(total)
        } else {
            id_keranjang  = databaseRef.push().key.toString()
            val addData = Keranjang(id_keranjang, id_user, idMenu, jumlahDetail.text.toString(), total)
            databaseRef.child(id_keranjang).setValue(addData)
        }
    }

    //Cek Data Pesanan
    private fun cekData() {
        databaseRef.orderByChild("id_menu").equalTo(idMenu).addValueEventListener( object : ValueEventListener {
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

    //Set Jumlah Pesanan
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