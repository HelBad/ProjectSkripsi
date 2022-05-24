package com.example.projectskripsi.features.riwayat.presentation

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.projectskripsi.R
import com.example.projectskripsi.core.Resource
import com.example.projectskripsi.features.riwayat.domain.entities.Keranjang
import com.example.projectskripsi.features.riwayat.domain.entities.Pesanan
import com.example.projectskripsi.features.riwayat.domain.entities.User
import com.example.projectskripsi.features.riwayat.presentation.adapter.RiwayatAdapter
import com.example.projectskripsi.features.riwayat.presentation.viewmodel.RiwayatViewModel
import com.example.projectskripsi.utils.Rupiah
import org.koin.android.viewmodel.ext.android.viewModel

class ActivityRiwayatUser : AppCompatActivity() {
    private val riwayatViewModel: RiwayatViewModel by viewModel()

    private lateinit var mLayoutManager: LinearLayoutManager
    private lateinit var mRecyclerView: RecyclerView
    private lateinit var identitasRiwayat: TextView
    private lateinit var lokasiRiwayat: TextView
    private lateinit var waktuRiwayat: TextView
    private lateinit var keteranganRiwayat: TextView
    private lateinit var subtotalRiwayat: TextView
    private lateinit var ongkirRiwayat: TextView
    private lateinit var totalRiwayat: TextView
    private lateinit var layoutLaporanRiwayat: LinearLayout
    private lateinit var laporanRiwayat: TextView

    var idUser = ""
    var idKeranjang = ""

    private var user: User? = null
    private var pesanan: Pesanan? = null

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

        loadData()
    }

    //Load Data Pesanan
    private fun loadData() {
        riwayatViewModel.getUser().observe(this@ActivityRiwayatUser) {
            if (it is Resource.Success) {
                user = it.data
                getDetailPesanan()
            }
        }
    }

    private fun getDetailPesanan() {
        riwayatViewModel.getDetailPesanan(
            intent.getStringExtra("status").toString(),
            intent.getStringExtra("id_pesanan").toString()
        ).observe(this@ActivityRiwayatUser) {
            if (it is Resource.Success) {
                pesanan = it.data
                lokasiRiwayat.text = pesanan?.lokasi
                waktuRiwayat.text = pesanan?.waktu
                if (pesanan?.catatan == "") {
                    keteranganRiwayat.text = "-"
                } else {
                    keteranganRiwayat.text = pesanan?.catatan
                }
                subtotalRiwayat.text =
                    pesanan?.subtotal?.toInt()?.let { it1 -> Rupiah.format(it1) }
                ongkirRiwayat.text =
                    pesanan?.ongkir?.toInt()?.let { it1 -> Rupiah.format(it1) }
                totalRiwayat.text =
                    pesanan?.total_bayar?.toInt()?.let { it1 -> Rupiah.format(it1) }
                if (pesanan?.keterangan == "") {
                    laporanRiwayat.text = "-"
                } else {
                    laporanRiwayat.text = pesanan?.keterangan
                }

                val nama = user?.nama
                val telp = user?.telp
                identitasRiwayat.text = "$nama ($telp)"
                getKeranjang()
            }
        }
    }

    private fun getKeranjang() {
        if (pesanan != null) {
            riwayatViewModel.getDetailKeranjang(
                pesanan?.id_keranjang.toString(),
                pesanan?.id_user.toString()
            ).observe(this@ActivityRiwayatUser) {
                if (it is Resource.Success) {
                    val keranjang = it.data
                    idKeranjang = keranjang?.idKeranjang.toString()
                    idUser = keranjang?.idUser.toString()
                    listKeranjang()
                }
            }
        }
    }

    //List Keranjang
    private fun listKeranjang() {
        val list = arrayListOf<Keranjang>()

        riwayatViewModel.getKeranjang(idKeranjang, idUser).observe(this@ActivityRiwayatUser) {
            if (it is Resource.Success && it.data != null) {
                it.data.forEach { keranjang ->
                    keranjang.idMenu?.let { idMenu ->
                        riwayatViewModel.getMenu(idMenu).observe(this@ActivityRiwayatUser) { it1 ->
                            if (it1 is Resource.Success) {
                                keranjang.namaMenu = it1.data?.namaMenu
                                list.add(keranjang)
                                val adapter = RiwayatAdapter(list)
                                mRecyclerView.adapter = adapter
                            }
                        }
                    }
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        if (intent.getStringExtra("status").toString() == "dibatalkan") {
            layoutLaporanRiwayat.visibility = View.VISIBLE
        } else {
            layoutLaporanRiwayat.visibility = View.GONE
        }
    }
}