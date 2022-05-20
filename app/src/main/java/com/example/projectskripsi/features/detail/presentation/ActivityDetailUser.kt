package com.example.projectskripsi.features.detail.presentation

import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.projectskripsi.R
import com.example.projectskripsi.core.Resource
import com.example.projectskripsi.features.detail.domain.entities.User
import com.example.projectskripsi.features.detail.presentation.viewmodel.DetailViewModel
import com.example.projectskripsi.utils.Rupiah
import com.squareup.picasso.Picasso
import org.koin.android.viewmodel.ext.android.viewModel

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

    var user: User? = null

    var countJumlah = 0
    var idKeranjang = ""
    var idMenu = ""
    var hargaMenu = 0
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

        loadData()
    }

    //Load Data Pesanan
    private fun loadData() {
        detailViewModel.getUser().observe(this@ActivityDetailUser) {
            if (it != null) {
                user = it.data
            }
        }

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
                    hargaMenu = detail.harga?.toInt()!!
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
                        user?.idUser?.let {
                            it1 -> detailViewModel.hapusPesanan(idKeranjang, it1)
                        }
                        finish()
                    }
                }
            }
    }

    //Save Data Pesanan
    private fun buatPesanan() {
        val total = (hargaMenu * jumlahDetail.text.toString().toInt()).toString()
        val jumlah = jumlahDetail.text.toString()

        if(statusKeranjang == "ready") {
            user?.idUser?.let {
                detailViewModel.updatePesanan(
                    idKeranjang,
                    jumlah,
                    total,
                    it
                )
            }
        } else {
            user?.idUser?.let {
                detailViewModel.buatPesanan(
                    it,
                    idMenu,
                    jumlah,
                    total,
                ).observe(this@ActivityDetailUser) { res ->
                    if (res is Resource.Success && res.data != null) {
                        idKeranjang = res.data
                    }
                }
            }
        }
    }

    //Cek Data Pesanan
    private fun cekData() {
        user?.idUser?.let {
            detailViewModel.getDetailKeranjang(idMenu, it).observe(this@ActivityDetailUser) { res ->
                if (res is Resource.Success) {
                    val keranjang = res.data
                    Log.d("detail", keranjang.toString())
                    if(keranjang?.idKeranjang != null) {
                        jumlahDetail.text = Editable.Factory.getInstance().newEditable(keranjang.jumlah)
                        idKeranjang = keranjang.idKeranjang.toString()
                        statusKeranjang = "ready"
                    }
                }
            }
        }
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