package com.example.projectskripsi.features.riwayat.presentation

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.DialogInterface
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
import com.example.projectskripsi.features.auth.domain.entities.User
import com.example.projectskripsi.features.pesanan.domain.entities.Keranjang
import com.example.projectskripsi.features.pesanan.domain.entities.Pesanan
import com.example.projectskripsi.features.riwayat.presentation.adapter.RiwayatAdapter
import com.example.projectskripsi.features.riwayat.presentation.viewmodel.RiwayatViewModel
import com.example.projectskripsi.utils.Rupiah
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
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
    var id_user = ""
    var id_keranjang = ""
    var subtotal = 0
    var ongkir = 0
    var total_bayar = 0

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
                lokasiRiwayat.text = pesanan?.lokasi
                waktuRiwayat.text = pesanan?.waktu
                if(pesanan?.catatan == "") {
                    keteranganRiwayat.text = "-"
                } else {
                    keteranganRiwayat.text = pesanan?.catatan
                }
                subtotal = pesanan?.subtotal?.toInt()!!
                ongkir = pesanan.ongkir?.toInt()!!
                total_bayar = pesanan.total_bayar?.toInt()!!
                subtotalRiwayat.text = Rupiah.format(subtotal)
                ongkirRiwayat.text = Rupiah.format(ongkir)
                totalRiwayat.text = Rupiah.format(total_bayar)
                if(pesanan.keterangan == "") {
                    laporanRiwayat.text = "-"
                } else {
                    laporanRiwayat.text = pesanan.keterangan
                }

                FirebaseDatabase.getInstance().getReference("user").orderByKey()
                    .equalTo(pesanan.id_user).addListenerForSingleValueEvent(object:
                        ValueEventListener {
                        override fun onDataChange(datasnapshot: DataSnapshot) {
                            for (snapshot2 in datasnapshot.children) {
                                user = snapshot2.getValue(User::class.java)
                                val nama = user?.nama
                                val telp = user?.telp
                                identitasRiwayat.text = "$nama ($telp)"
                            }
                        }
                        override fun onCancelled(databaseError: DatabaseError) {}
                    })

                FirebaseDatabase.getInstance().getReference("keranjang").child("kosong")
                    .child(pesanan.id_user + " | " + pesanan.id_keranjang).orderByKey()
                    .equalTo(pesanan.id_keranjang).addListenerForSingleValueEvent(object:
                        ValueEventListener {
                        override fun onDataChange(datasnapshot: DataSnapshot) {
                            for (snapshot3 in datasnapshot.children) {
                                val keranjang = snapshot3.getValue(Keranjang::class.java)
                                id_keranjang = keranjang?.idKeranjang.toString()
                                id_user = keranjang?.idUser.toString()
                                listKeranjang()
                            }
                        }
                        override fun onCancelled(databaseError: DatabaseError) {}
                    })

                btnBatal.setOnClickListener {
                    alertDialog.setMessage("Apakah anda yakin membatalkan pesanan ini ?").setCancelable(false)
                        .setPositiveButton("YA", object: DialogInterface.OnClickListener {
                            override fun onClick(dialog: DialogInterface, id:Int) {
                                if(validate()) {
                                    pesananBatal()
                                    finish()
                                }
                            }
                        })
                        .setNegativeButton("TIDAK", object: DialogInterface.OnClickListener {
                            override fun onClick(dialog: DialogInterface, id:Int) {
                                dialog.cancel()
                            }
                        }).create().show()
                }

                btnSelesai.setOnClickListener {
                    alertDialog.setMessage("Apakah anda yakin mengakhiri pesanan ini ?").setCancelable(false)
                        .setPositiveButton("YA", object: DialogInterface.OnClickListener {
                            override fun onClick(dialog: DialogInterface, id:Int) {
                                pesananSelesai()
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
    }

    private fun loadUser() {

    }

    //List Keranjang
    private fun listKeranjang() {
        user?.idUser?.let { idUser ->
            riwayatViewModel.getKeranjang(id_keranjang, idUser).observe(this@ActivityRiwayatAdmin) {
                if (it is Resource.Success && it.data != null) {
                    val adapter = RiwayatAdapter(it.data)
                    mRecyclerView.adapter = adapter
                }
            }
        }
    }

    //Validasi Pesanan
    private fun validate(): Boolean {
        if(editLaporanRiwayat.text.toString() == "") {
            Toast.makeText(this, "Tambahkan alasan dibatalkan ", Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }

    //Set Status Pesanan
    private fun pesananBatal() {
        val addData = Pesanan(intent.getStringExtra("id_pesanan").toString(), id_user, id_keranjang,
            keteranganRiwayat.text.toString(), waktuRiwayat.text.toString(), lokasiRiwayat.text.toString(),
            subtotal.toString(), ongkir.toString(), total_bayar.toString(), "dibatalkan",
            editLaporanRiwayat.text.toString())

        FirebaseDatabase.getInstance().getReference("pesanan").child("dibatalkan")
            .child(intent.getStringExtra("id_pesanan").toString()).setValue(addData)
        FirebaseDatabase.getInstance().getReference("pesanan").child("diproses")
            .child(intent.getStringExtra("id_pesanan").toString()).removeValue()
    }

    private fun pesananSelesai() {
        val addData = Pesanan(intent.getStringExtra("id_pesanan").toString(), id_user, id_keranjang,
            keteranganRiwayat.text.toString(), waktuRiwayat.text.toString(), lokasiRiwayat.text.toString(),
            subtotal.toString(), ongkir.toString(), total_bayar.toString(), "selesai",
            editLaporanRiwayat.text.toString())

        FirebaseDatabase.getInstance().getReference("pesanan").child("selesai")
            .child(intent.getStringExtra("id_pesanan").toString()).setValue(addData)
        FirebaseDatabase.getInstance().getReference("pesanan").child("diproses")
            .child(intent.getStringExtra("id_pesanan").toString()).removeValue()
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