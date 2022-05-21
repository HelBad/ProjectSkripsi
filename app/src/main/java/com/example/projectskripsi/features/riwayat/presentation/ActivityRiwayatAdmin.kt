package com.example.projectskripsi.features.riwayat.presentation

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.projectskripsi.R
import com.example.projectskripsi.core.Resource
import com.example.projectskripsi.features.riwayat.domain.entities.User
import com.example.projectskripsi.features.riwayat.presentation.adapter.RiwayatAdapter
import com.example.projectskripsi.features.riwayat.presentation.viewmodel.RiwayatViewModel
import com.example.projectskripsi.utils.Rupiah
import org.koin.android.viewmodel.ext.android.viewModel

class ActivityRiwayatAdmin : AppCompatActivity() {
    private val riwayatViewModel: RiwayatViewModel by viewModel()

    private lateinit var alertDialog: AlertDialog.Builder
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
    private lateinit var editLaporanRiwayat: EditText
    private lateinit var btnLayoutRiwayat: CardView
    private lateinit var btnBatal: Button
    private lateinit var btnSelesai: Button
    private lateinit var imgCopyLokasi: ImageView

    var user: User? = null
    var idUser = ""
    var idKeranjang = ""
    var subtotal = 0
    var ongkir = 0
    var totalBayar = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.admin_activity_riwayat)

        identitasRiwayat = findViewById(R.id.identitasRiwayat)
        lokasiRiwayat = findViewById(R.id.lokasiRiwayat)
        waktuRiwayat = findViewById(R.id.waktuRiwayat)
        keteranganRiwayat = findViewById(R.id.keteranganRiwayat)
        subtotalRiwayat = findViewById(R.id.subtotalRiwayat)
        ongkirRiwayat = findViewById(R.id.ongkirRiwayat)
        totalRiwayat = findViewById(R.id.totalRiwayat)
        layoutLaporanRiwayat = findViewById(R.id.layoutLaporanRiwayat)
        laporanRiwayat = findViewById(R.id.laporanRiwayat)
        editLaporanRiwayat = findViewById(R.id.editLaporanRiwayat)
        btnLayoutRiwayat = findViewById(R.id.btnLayoutRiwayat)
        btnBatal = findViewById(R.id.btnBatal)
        btnSelesai = findViewById(R.id.btnSelesai)
        imgCopyLokasi = findViewById(R.id.imgCopyLokasi)

        mLayoutManager = LinearLayoutManager(this)
        mRecyclerView = findViewById(R.id.recyclerRiwayat)
        mRecyclerView.setHasFixedSize(true)
        mRecyclerView.layoutManager = mLayoutManager

        alertDialog = AlertDialog.Builder(this)
        loadData()

        imgCopyLokasi.setOnClickListener {
            copyTextToClipboard()
        }
    }

    //Salin Teks Lokasi
    private fun copyTextToClipboard() {
        val textToCopy = lokasiRiwayat.text
        val clipboardManager = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clipData = ClipData.newPlainText("text", textToCopy)
        clipboardManager.setPrimaryClip(clipData)
        Toast.makeText(this, "Teks berhasil disalin", Toast.LENGTH_SHORT).show()
    }

    //Load Data Pesanan
    private fun loadData() {
        riwayatViewModel.getDetailPesanan(
            intent.getStringExtra("status").toString(),
            intent.getStringExtra("id_pesanan").toString()
        ).observe(this@ActivityRiwayatAdmin) {
            if (it is Resource.Success) {
                val pesanan = it.data
                if (pesanan != null) {
                    lokasiRiwayat.text = pesanan.lokasi
                    waktuRiwayat.text = pesanan.waktu
                    if (pesanan.catatan == "") {
                        keteranganRiwayat.text = "-"
                    } else {
                        keteranganRiwayat.text = pesanan.catatan
                    }
                    subtotal = pesanan.subtotal?.toInt()!!
                    ongkir = pesanan.ongkir?.toInt()!!
                    totalBayar = pesanan.total_bayar?.toInt()!!
                    subtotalRiwayat.text = Rupiah.format(subtotal)
                    ongkirRiwayat.text = Rupiah.format(ongkir)
                    totalRiwayat.text = Rupiah.format(totalBayar)
                    if (pesanan.keterangan == "") {
                        laporanRiwayat.text = "-"
                    } else {
                        laporanRiwayat.text = pesanan.keterangan
                    }

                    pesanan.id_user?.let { idUser ->
                        riwayatViewModel.getUser(idUser).observe(this@ActivityRiwayatAdmin) { r1 ->
                            if (r1 is Resource.Success) {
                                user = r1.data
                                val nama = user?.nama
                                val telp = user?.telp
                                identitasRiwayat.text = "$nama ($telp)"
                            }
                        }
                    }

                    riwayatViewModel.getDetailKeranjang(
                        pesanan.id_keranjang.toString(),
                        pesanan.id_user.toString()
                    ).observe(this@ActivityRiwayatAdmin) { r2 ->
                        if (r2 is Resource.Success) {
                            val keranjang = r2.data
                            idKeranjang = keranjang?.idKeranjang.toString()
                            idUser = keranjang?.idUser.toString()
                            listKeranjang()
                        }
                    }

                    btnBatal.setOnClickListener {
                        alertDialog.setMessage("Apakah anda yakin membatalkan pesanan ini ?")
                            .setCancelable(false)
                            .setPositiveButton("YA") { _, _ ->
                                if (validate()) {
                                    pesananBatal()
                                }
                            }
                            .setNegativeButton("TIDAK") { dialog, _ -> dialog.cancel() }.create()
                            .show()
                    }

                    btnSelesai.setOnClickListener {
                        alertDialog.setMessage("Apakah anda yakin mengakhiri pesanan ini ?")
                            .setCancelable(false)
                            .setPositiveButton("YA") { _, _ ->
                                pesananSelesai()
                            }
                            .setNegativeButton("TIDAK") { dialog, _ -> dialog.cancel() }.create()
                            .show()
                    }
                }
            }
        }
    }

    //List Keranjang
    private fun listKeranjang() {
        user?.idUser?.let { idUser ->
            riwayatViewModel.getKeranjang(idKeranjang, idUser).observe(this@ActivityRiwayatAdmin) {
                if (it is Resource.Success && it.data != null) {
                    val adapter = RiwayatAdapter(it.data)
                    mRecyclerView.adapter = adapter
                }
            }
        }
    }

    //Validasi Pesanan
    private fun validate(): Boolean {
        if (editLaporanRiwayat.text.toString() == "") {
            Toast.makeText(this, "Tambahkan alasan dibatalkan ", Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }

    //Set Status Pesanan
    private fun pesananBatal() {
        riwayatViewModel.updatePesanan(
            intent.getStringExtra("id_pesanan").toString(),
            idUser,
            idKeranjang,
            keteranganRiwayat.text.toString(),
            waktuRiwayat.text.toString(),
            lokasiRiwayat.text.toString(),
            subtotal.toString(),
            ongkir.toString(),
            totalBayar.toString(),
            "dibatalkan",
            editLaporanRiwayat.text.toString()
        ).observe(this@ActivityRiwayatAdmin) {
            if (it is Resource.Success) {
                Toast.makeText(this, "Pesanan berhasil dibatalkan", Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }

    private fun pesananSelesai() {
        riwayatViewModel.updatePesanan(
            intent.getStringExtra("id_pesanan").toString(),
            idUser,
            idKeranjang,
            keteranganRiwayat.text.toString(),
            waktuRiwayat.text.toString(),
            lokasiRiwayat.text.toString(),
            subtotal.toString(),
            ongkir.toString(),
            totalBayar.toString(),
            "selesai",
            editLaporanRiwayat.text.toString()
        ).observe(this@ActivityRiwayatAdmin) {
            if (it is Resource.Success) {
                Toast.makeText(this, "Pesanan berhasil diselesaikan", Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }

    override fun onStart() {
        super.onStart()
        when {
            intent.getStringExtra("status").toString() == "diproses" -> {
                editLaporanRiwayat.visibility = View.VISIBLE
                laporanRiwayat.visibility = View.GONE
                btnLayoutRiwayat.visibility = View.VISIBLE
            }
            intent.getStringExtra("status").toString() == "selesai" -> {
                layoutLaporanRiwayat.visibility = View.GONE
                btnLayoutRiwayat.visibility = View.GONE
            }
            intent.getStringExtra("status").toString() == "dibatalkan" -> {
                editLaporanRiwayat.visibility = View.GONE
                laporanRiwayat.visibility = View.VISIBLE
                btnLayoutRiwayat.visibility = View.GONE
            }
        }
    }
}