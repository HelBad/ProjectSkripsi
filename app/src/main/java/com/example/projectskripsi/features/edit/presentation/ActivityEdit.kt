package com.example.projectskripsi.features.edit.presentation

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.projectskripsi.R
import com.example.projectskripsi.core.Resource
import com.example.projectskripsi.features.beranda.presentation.ActivityUtamaAdmin
import com.example.projectskripsi.features.edit.presentation.viewmodel.EditViewModel
import org.koin.android.viewmodel.ext.android.viewModel
import kotlin.math.absoluteValue

class ActivityEdit : AppCompatActivity() {
    private val editViewModel: EditViewModel by viewModel()
    private lateinit var namaMenu: EditText
    private lateinit var deskripsiMenu: EditText
    private lateinit var hargaMenu: EditText
    private lateinit var gambarMenu: TextView
    private lateinit var lemakMenu: EditText
    private lateinit var proteinMenu: EditText
    private lateinit var kaloriMenu: EditText
    private lateinit var karbohidratMenu: EditText
    private lateinit var btnSimpan: Button

    private lateinit var alertDialog: AlertDialog.Builder
    private lateinit var uri: Uri
    private var url: Uri? = null
    private var statusMenu = ""
    private var id_menu = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.admin_activity_edit)

        namaMenu = findViewById(R.id.namaMenu)
        deskripsiMenu = findViewById(R.id.deskripsiMenu)
        hargaMenu = findViewById(R.id.hargaMenu)
        gambarMenu = findViewById(R.id.gambarMenu)
        lemakMenu = findViewById(R.id.lemakMenu)
        proteinMenu = findViewById(R.id.proteinMenu)
        kaloriMenu = findViewById(R.id.kaloriMenu)
        karbohidratMenu = findViewById(R.id.karbohidratMenu)
        btnSimpan = findViewById(R.id.btnSimpan)

        alertDialog = AlertDialog.Builder(this)
        loadMenu()

        gambarMenu.setOnClickListener {
            val intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.type = "image/*"
            startActivityForResult(intent, 0)
        }

        btnSimpan.setOnClickListener {
            alertDialog.setMessage("Apakah anda ingin menyimpan data menu ?")
                .setCancelable(false)
                .setPositiveButton("YA") { _, _ ->
                    if (validate()) {
                        tambahData()
                        rekomendasi()
                        val intent = Intent(this@ActivityEdit, ActivityUtamaAdmin::class.java)
                        startActivity(intent)
                        finish()
                    }
                }
                .setNegativeButton("TIDAK") { dialog, _ -> dialog.cancel() }.create().show()
        }
    }

    //Set Gambar Menu
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {
            if (requestCode == 0) {
                uri = data!!.data!!
                id_menu = intent.getStringExtra("id_menu").toString()
                editViewModel.uploadGambar(id_menu, uri).observe(this@ActivityEdit) {
                    if (it is Resource.Success && it.data != null) {
                        url = it.data
                        gambarMenu.text = url.toString()
                        Log.d(url.toString(), "karbo")
                    } else if (it is Resource.Error) {
                        Toast.makeText(this@ActivityEdit, it.message, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    //Load Data Menu
    private fun loadMenu() {
        editViewModel.getDetailMenu(intent.getStringExtra("id_menu").toString())
            .observe(this@ActivityEdit) {
                if (it is Resource.Success && it.data != null) {
                    val menu = it.data
                    id_menu = menu.id_menu.toString()
                    namaMenu.text =
                        Editable.Factory.getInstance().newEditable(menu.nama_menu)
                    deskripsiMenu.text =
                        Editable.Factory.getInstance().newEditable(menu.deskripsi)
                    lemakMenu.text =
                        Editable.Factory.getInstance().newEditable(menu.lemak)
                    proteinMenu.text =
                        Editable.Factory.getInstance().newEditable(menu.protein)
                    kaloriMenu.text =
                        Editable.Factory.getInstance().newEditable(menu.kalori)
                    karbohidratMenu.text =
                        Editable.Factory.getInstance().newEditable(menu.karbohidrat)
                    hargaMenu.text =
                        Editable.Factory.getInstance().newEditable(menu.harga)
                    gambarMenu.text = menu.gambar
                    statusMenu = "ada"
                }
            }
    }

    //Validasi Data Menu
    private fun validate(): Boolean {
        if (namaMenu.text.toString() == "") {
            Toast.makeText(this, "Nama menu masih kosong", Toast.LENGTH_SHORT).show()
            return false
        }
        if (deskripsiMenu.text.toString() == "") {
            Toast.makeText(this, "Deskripsi masih kosong", Toast.LENGTH_SHORT).show()
            return false
        }
        if (hargaMenu.text.toString() == "") {
            Toast.makeText(this, "Harga masih kosong", Toast.LENGTH_SHORT).show()
            return false
        }
        if (gambarMenu.text.toString() == "") {
            Toast.makeText(this, "Gambar masih kosong", Toast.LENGTH_SHORT).show()
            return false
        }
        if (lemakMenu.text.toString() == "") {
            Toast.makeText(this, "Jumlah lemak kosong", Toast.LENGTH_SHORT).show()
            return false
        }
        if (proteinMenu.text.toString() == "") {
            Toast.makeText(this, "Jumlah protein kosong", Toast.LENGTH_SHORT).show()
            return false
        }
        if (kaloriMenu.text.toString() == "") {
            Toast.makeText(this, "Jumlah kalori kosong", Toast.LENGTH_SHORT).show()
            return false
        }
        if (karbohidratMenu.text.toString() == "") {
            Toast.makeText(this, "Jumlah karbohidrat kosong", Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }

    //Simpan Data Menu
    private fun tambahData() {
        if (statusMenu == "ada") {
            id_menu = intent.getStringExtra("id_menu").toString()
            editViewModel.updateMenu(
                id_menu,
                namaMenu.text.toString(),
                deskripsiMenu.text.toString(),
                lemakMenu.text.toString(),
                proteinMenu.text.toString(),
                kaloriMenu.text.toString(),
                karbohidratMenu.text.toString(),
                hargaMenu.text.toString(),
                gambarMenu.text.toString()
            )
        } else {
            editViewModel.buatMenu(
                namaMenu.text.toString(),
                deskripsiMenu.text.toString(),
                lemakMenu.text.toString(),
                proteinMenu.text.toString(),
                kaloriMenu.text.toString(),
                karbohidratMenu.text.toString(),
                hargaMenu.text.toString(),
                gambarMenu.text.toString()
            ).observe(this@ActivityEdit) {
                if (it is Resource.Success && it.data != null) {
                    id_menu = it.data
                }
            }
        }
    }

    //AI Rekomendasi Menu
    private fun rekomendasi() {
        val jumlahData = 8
        val lemakSehat = 1
        val proteinSehat = 1
        val kaloriSehat = 1
        val karbohidratSehat = 1
        var lemakK1 = 0
        var proteinK1 = 0
        var kaloriK1 = 0
        var karbohidratK1 = 0
        var lemakK2 = 0
        var proteinK2 = 0
        var kaloriK2 = 0
        var karbohidratK2 = 0
        var lemakK3 = 0
        var proteinK3 = 0
        var kaloriK3 = 0
        var karbohidratK3 = 0
        var lemakK4 = 0
        var proteinK4 = 0
        var kaloriK4 = 0
        var karbohidratK4 = 0
        var lemakK5 = 0
        var proteinK5 = 0
        var kaloriK5 = 0
        var karbohidratK5 = 0

        //Kandungan Lemak
        when {
            lemakMenu.text.toString().toDouble() < 10.0 -> {
                lemakK1 = 1
                lemakK2 = 1
                lemakK3 = 0
                lemakK4 = 1
                lemakK5 = 1
            }
            lemakMenu.text.toString().toDouble() in 10.0..25.0 -> {
                lemakK1 = 1
                lemakK2 = 1
                lemakK3 = 1
                lemakK4 = 1
                lemakK5 = 1
            }
            lemakMenu.text.toString().toDouble() > 25.0 -> {
                lemakK1 = 1
                lemakK2 = 0
                lemakK3 = 1
                lemakK4 = 0
                lemakK5 = 0
            }
        }

        //Kandungan Protein
        when {
            proteinMenu.text.toString().toDouble() < 8.8 -> {
                proteinK1 = 1
                proteinK2 = 0
                proteinK3 = 0
                proteinK4 = 1
                proteinK5 = 1
            }
            proteinMenu.text.toString().toDouble() in 8.8..22.0 -> {
                proteinK1 = 1
                proteinK2 = 1
                proteinK3 = 1
                proteinK4 = 1
                proteinK5 = 1
            }
            proteinMenu.text.toString().toDouble() > 22.0 -> {
                proteinK1 = 0
                proteinK2 = 1
                proteinK3 = 1
                proteinK4 = 0
                proteinK5 = 0
            }
        }

        //Kandungan Kalori
        when {
            kaloriMenu.text.toString().toDouble() < 300.0 -> {
                kaloriK1 = 0
                kaloriK2 = 0
                kaloriK3 = 1
                kaloriK4 = 0
                kaloriK5 = 0
            }
            kaloriMenu.text.toString().toDouble() in 300.0..750.0 -> {
                kaloriK1 = 1
                kaloriK2 = 1
                kaloriK3 = 1
                kaloriK4 = 1
                kaloriK5 = 1
            }
            kaloriMenu.text.toString().toDouble() > 750.0 -> {
                kaloriK1 = 0
                kaloriK2 = 0
                kaloriK3 = 1
                kaloriK4 = 0
                kaloriK5 = 0
            }
        }

        //Kandungan Karbohidrat
        when {
            karbohidratMenu.text.toString().toDouble() < 30.0 -> {
                karbohidratK1 = 0
                karbohidratK2 = 0
                karbohidratK3 = 1
                karbohidratK4 = 0
                karbohidratK5 = 0
            }
            karbohidratMenu.text.toString().toDouble() in 30.0..75.0 -> {
                karbohidratK1 = 1
                karbohidratK2 = 1
                karbohidratK3 = 1
                karbohidratK4 = 1
                karbohidratK5 = 1
            }
            karbohidratMenu.text.toString().toDouble() > 75.0 -> {
                karbohidratK1 = 0
                karbohidratK2 = 0
                karbohidratK3 = 1
                karbohidratK4 = 0
                karbohidratK5 = 0
            }
        }

        //Slope One Algorithm untuk Sehat
        val devSehat = (((lemakSehat - lemakSehat) + (proteinSehat - proteinSehat) + (kaloriSehat - kaloriSehat)
                + (karbohidratSehat - karbohidratSehat)).absoluteValue).toDouble() / jumlahData.toDouble()
        val pSehat = devSehat + (lemakSehat + proteinSehat + kaloriSehat + karbohidratSehat).toDouble()

        //Slope One Algorithm untuk Diabetes
        val devK1 = (((lemakK1 - lemakSehat) + (proteinK1 - proteinSehat) + (kaloriK1 - kaloriSehat)
                + (karbohidratK1 - karbohidratSehat)).absoluteValue).toDouble() / jumlahData.toDouble()
        val pK1 = devK1 + (lemakK1 + proteinK1 + kaloriK1 + karbohidratK1).toDouble()

        //Slope One Algorithm untuk Jantung
        val devK2 = (((lemakK2 - lemakSehat) + (proteinK2 - proteinSehat) + (kaloriK2 - kaloriSehat)
                + (karbohidratK2 - karbohidratSehat)).absoluteValue).toDouble() / jumlahData.toDouble()
        val pK2 = devK2 + (lemakK2 + proteinK2 + kaloriK2 + karbohidratK2).toDouble()

        //Slope One Algorithm untuk Kelelahan
        val devK3 = (((lemakK3 - lemakSehat) + (proteinK3 - proteinSehat) + (kaloriK3 - kaloriSehat)
                + (karbohidratK3 - karbohidratSehat)).absoluteValue).toDouble() / jumlahData.toDouble()
        val pK3 = devK3 + (lemakK3 + proteinK3 + kaloriK3 + karbohidratK3).toDouble()

        //Slope One Algorithm untuk Obesitas
        val devK4 = (((lemakK4 - lemakSehat) + (proteinK4 - proteinSehat) + (kaloriK4 - kaloriSehat)
                + (karbohidratK4 - karbohidratSehat)).absoluteValue).toDouble() / jumlahData.toDouble()
        val pK4 = devK4 + (lemakK4 + proteinK4 + kaloriK4 + karbohidratK4).toDouble()

        //Slope One Algorithm untuk Sembelit
        val devK5 = (((lemakK5 - lemakSehat) + (proteinK5 - proteinSehat) + (kaloriK5 - kaloriSehat)
                + (karbohidratK5 - karbohidratSehat)).absoluteValue).toDouble() / jumlahData.toDouble()
        val pK5 = devK5 + (lemakK5 + proteinK5 + kaloriK5 + karbohidratK5).toDouble()

        if (statusMenu == "ada") {
            editViewModel.getDetailPenyakit(
                intent.getStringExtra("id_menu").toString()
            ).observe(this@ActivityEdit) {
                if (it is Resource.Success && it.data != null) {
                    val penyakit = it.data
                    penyakit.id_penyakit?.let { idPenyakit ->
                        editViewModel.updatePenyakit(
                            idPenyakit, id_menu, pSehat.toString(), pK1.toString(), pK2.toString(),
                            pK3.toString(), pK4.toString(), pK5.toString()
                        )
                    }
                }
            }
        } else {
            editViewModel.buatPenyakit(
                id_menu, pSehat.toString(), pK1.toString(), pK2.toString(), pK3.toString(),
                pK4.toString(), pK5.toString()
            )
        }
    }
}