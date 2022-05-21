package com.example.projectskripsi.features.menu.presentation

import android.os.Bundle
import android.text.Editable
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.projectskripsi.R
import com.example.projectskripsi.core.Resource
import com.example.projectskripsi.features.menu.domain.entities.User
import com.example.projectskripsi.features.menu.presentation.viewmodel.MenuViewModel
import com.example.projectskripsi.utils.Rupiah
import com.squareup.picasso.Picasso
import org.koin.android.viewmodel.ext.android.viewModel

class ActivityMenuUser : AppCompatActivity() {
    private val menuViewModel: MenuViewModel by viewModel()

    private lateinit var namaDetail: TextView
    private lateinit var imgDetail: ImageView
    private lateinit var lemakDetail: TextView
    private lateinit var proteinDetail: TextView
    private lateinit var kaloriDetail: TextView
    private lateinit var karbohidratDetail: TextView
    private lateinit var deskripsiDetail: TextView
    private lateinit var hargaDetail: TextView
    private lateinit var jumlahDetail: EditText
    private lateinit var kurangDetail: ImageView
    private lateinit var tambahDetail: ImageView
    private lateinit var btnPesan: Button
    private lateinit var btnHapus: Button

    private var user: User? = null

    private var countJumlah = 0
    private var idKeranjang = ""
    private var idMenu = ""
    private var hargaMenu = 0
    private var statusKeranjang = ""

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
        menuViewModel.getUser().observe(this@ActivityMenuUser) {
            if (it != null) {
                user = it.data
            }
        }

        menuViewModel.getDetailMenu(intent.getStringExtra("id_menu").toString())
            .observe(this@ActivityMenuUser) { res ->
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
                        user?.idUser?.let { it1 ->
                            menuViewModel.hapusPesanan(idKeranjang, it1)
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

        if (statusKeranjang == "ready") {
            user?.idUser?.let {
                menuViewModel.updatePesanan(
                    idKeranjang,
                    jumlah,
                    total,
                    it
                )
            }
        } else {
            user?.idUser?.let {
                menuViewModel.buatPesanan(it, idMenu, jumlah, total)
                    .observe(this@ActivityMenuUser) { res ->
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
            menuViewModel.getDetailKeranjang(idMenu, it).observe(this@ActivityMenuUser) { res ->
                if (res is Resource.Success) {
                    val keranjang = res.data
                    if (keranjang?.idKeranjang != null) {
                        jumlahDetail.text =
                            Editable.Factory.getInstance().newEditable(keranjang.jumlah)
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
                if (statusKeranjang == "ready") {
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
                if (statusKeranjang == "ready") {
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