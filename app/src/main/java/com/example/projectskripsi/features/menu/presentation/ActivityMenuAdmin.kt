package com.example.projectskripsi.features.menu.presentation

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.projectskripsi.R
import com.example.projectskripsi.features.beranda.domain.entities.Penyakit
import com.example.projectskripsi.features.edit.presentation.ActivityEdit
import com.example.projectskripsi.features.menu.presentation.viewmodel.MenuViewModel
import com.example.projectskripsi.utils.Rupiah
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import com.squareup.picasso.Picasso
import org.koin.android.viewmodel.ext.android.viewModel

class ActivityMenuAdmin : AppCompatActivity() {
    private val menuViewModel: MenuViewModel by viewModel()

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
        menuViewModel.getDetailMenu(intent.getStringExtra("id_menu").toString())
            .observe(this@ActivityMenuAdmin) { res ->
                if (res.data != null) {
                    val allocation = res.data
                    id_menu = allocation.idMenu.toString()
                    namaDetail.text = allocation.namaMenu
                    lemakDetail.text = allocation.lemak
                    proteinDetail.text = allocation.protein
                    kaloriDetail.text = allocation.kalori
                    karbohidratDetail.text = allocation.karbohidrat
                    deskripsiDetail.text = allocation.deskripsi
                    hargaDetail.text = allocation.harga?.toInt()?.let { Rupiah.format(it) }
                    Picasso.get().load(allocation.gambar).into(imgDetail)

                    btnEdit.setOnClickListener {
                        val intent = Intent(this@ActivityMenuAdmin, ActivityEdit::class.java)
                        intent.putExtra("id_menu", id_menu)
                        startActivity(intent)
                    }

                    btnHapus.setOnClickListener {
                        alertDialog.setMessage("Apakah anda ingin menghapus menu ini ?")
                            .setCancelable(false)
                            .setPositiveButton("YA") { _, _ ->
                                FirebaseDatabase.getInstance().getReference("menu")
                                    .child(intent.getStringExtra("id_menu").toString())
                                    .removeValue()
                                FirebaseStorage.getInstance().getReference("menu")
                                    .child(intent.getStringExtra("id_menu").toString()).delete()
                                FirebaseDatabase.getInstance().getReference("penyakit")
                                    .orderByChild("id_menu")
                                    .equalTo(intent.getStringExtra("id_menu"))
                                    .addListenerForSingleValueEvent(object : ValueEventListener {
                                        override fun onDataChange(datasnapshot: DataSnapshot) {
                                            for (snapshot1 in datasnapshot.children) {
                                                val allocation =
                                                    snapshot1.getValue(Penyakit::class.java)
                                                val id_penyakit = allocation?.idPenyakit
                                                if (id_penyakit != null) {
                                                    FirebaseDatabase.getInstance()
                                                        .getReference("penyakit")
                                                        .child(id_penyakit).removeValue()
                                                }

                                            }
                                        }

                                        override fun onCancelled(databaseError: DatabaseError) {}
                                    })
                                finish()
                            }
                            .setNegativeButton("TIDAK") { dialog, _ -> dialog.cancel() }.create()
                            .show()
                    }
                }
            }
    }
}