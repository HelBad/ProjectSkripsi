package com.example.projectskripsi.adapter

import android.graphics.Color
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.projectskripsi.R
import com.example.projectskripsi.model.Menu
import com.example.projectskripsi.model.Penyakit
import com.squareup.picasso.Picasso
import java.text.DecimalFormat
import java.text.NumberFormat

class ViewholderBeranda(itemView: View): RecyclerView.ViewHolder(itemView) {
    private var mView: View = itemView
    private var mClickListener: ClickListener? = null
    var menu = Menu()
    var formatNumber: NumberFormat = DecimalFormat("#,###")

    init {
        itemView.setOnClickListener { view -> mClickListener!!.onItemClick(view, adapterPosition) }
        itemView.setOnLongClickListener { view ->
            mClickListener!!.onItemLongClick(view, adapterPosition)
            true
        }
    }

    fun setDetails(menu: Menu, listPenyakit: ArrayList<Penyakit>, pilihKategori: String) {
        this.menu = menu
        val namaListmenu = mView.findViewById(R.id.namaListmenu) as TextView
        val hargaListmenu = mView.findViewById(R.id.hargaListmenu) as TextView
        val deskripsiListmenu = mView.findViewById(R.id.deskripsiListmenu) as TextView
        val imgListmenu = mView.findViewById(R.id.imgListmenu) as ImageView
        val kategoriListmenu = mView.findViewById(R.id.kategoriListmenu) as TextView

        namaListmenu.text = menu.nama_menu
        hargaListmenu.text = "Rp. " + formatNumber.format(menu.harga.toInt()) + ",00"
        deskripsiListmenu.text = menu.deskripsi
        Picasso.get().load(menu.gambar).into(imgListmenu)

        for (list in listPenyakit) {
            if(list.id_menu == menu.id_menu) {
                if(pilihKategori == "Sehat") {
                    if(list.sehat.toDouble() <= 1.375) {
                        kategoriListmenu.text = "Kategori : Buruk Dikonsumsi"
                        kategoriListmenu.setTextColor(Color.parseColor("#FFFF0000"))
                    } else if(list.sehat.toDouble() > 1.375 && list.sehat.toDouble() <= 3.125) {
                        kategoriListmenu.text = "Kategori : Kurang Baik"
                        kategoriListmenu.setTextColor(Color.parseColor("#FFFF6333"))
                    } else {
                        kategoriListmenu.text = "Kategori : Baik Dikonsumsi"
                        kategoriListmenu.setTextColor(Color.parseColor("#FF239D58"))
                    }
                } else if(pilihKategori == "Diabetes") {
                    if (list.diabetes.toDouble() <= 1.375) {
                        kategoriListmenu.text = "Kategori : Buruk Dikonsumsi"
                        kategoriListmenu.setTextColor(Color.parseColor("#FFFF0000"))
                    } else if (list.diabetes.toDouble() > 1.375 && list.diabetes.toDouble() <= 3.125) {
                        kategoriListmenu.text = "Kategori : Kurang Baik"
                        kategoriListmenu.setTextColor(Color.parseColor("#FFFF6333"))
                    } else {
                        kategoriListmenu.text = "Kategori : Baik Dikonsumsi"
                        kategoriListmenu.setTextColor(Color.parseColor("#FF239D58"))
                    }
                } else if(pilihKategori == "Jantung") {
                    if (list.jantung.toDouble() <= 1.375) {
                        kategoriListmenu.text = "Kategori : Buruk Dikonsumsi"
                        kategoriListmenu.setTextColor(Color.parseColor("#FFFF0000"))
                    } else if (list.jantung.toDouble() > 1.375 && list.jantung.toDouble() <= 3.125) {
                        kategoriListmenu.text = "Kategori : Kurang Baik"
                        kategoriListmenu.setTextColor(Color.parseColor("#FFFF6333"))
                    } else {
                        kategoriListmenu.text = "Kategori : Baik Dikonsumsi"
                        kategoriListmenu.setTextColor(Color.parseColor("#FF239D58"))
                    }
                } else if(pilihKategori == "Kelelahan") {
                    if (list.kelelahan.toDouble() <= 1.375) {
                        kategoriListmenu.text = "Kategori : Buruk Dikonsumsi"
                        kategoriListmenu.setTextColor(Color.parseColor("#FFFF0000"))
                    } else if (list.kelelahan.toDouble() > 1.375 && list.kelelahan.toDouble() <= 3.125) {
                        kategoriListmenu.text = "Kategori : Kurang Baik"
                        kategoriListmenu.setTextColor(Color.parseColor("#FFFF6333"))
                    } else {
                        kategoriListmenu.text = "Kategori : Baik Dikonsumsi"
                        kategoriListmenu.setTextColor(Color.parseColor("#FF239D58"))
                    }
                } else if(pilihKategori == "Obesitas") {
                    if (list.obesitas.toDouble() <= 1.375) {
                        kategoriListmenu.text = "Kategori : Buruk Dikonsumsi"
                        kategoriListmenu.setTextColor(Color.parseColor("#FFFF0000"))
                    } else if (list.obesitas.toDouble() > 1.375 && list.obesitas.toDouble() <= 3.125) {
                        kategoriListmenu.text = "Kategori : Kurang Baik"
                        kategoriListmenu.setTextColor(Color.parseColor("#FFFF6333"))
                    } else {
                        kategoriListmenu.text = "Kategori : Baik Dikonsumsi"
                        kategoriListmenu.setTextColor(Color.parseColor("#FF239D58"))
                    }
                } else if(pilihKategori == "Sembelit") {
                    if (list.sembelit.toDouble() <= 1.375) {
                        kategoriListmenu.text = "Kategori : Buruk Dikonsumsi"
                        kategoriListmenu.setTextColor(Color.parseColor("#FFFF0000"))
                    } else if (list.sembelit.toDouble() > 1.375 && list.sembelit.toDouble() <= 3.125) {
                        kategoriListmenu.text = "Kategori : Kurang Baik"
                        kategoriListmenu.setTextColor(Color.parseColor("#FFFF6333"))
                    } else {
                        kategoriListmenu.text = "Kategori : Baik Dikonsumsi"
                        kategoriListmenu.setTextColor(Color.parseColor("#FF239D58"))
                    }
                }
            }
        }
    }

    interface ClickListener {
        fun onItemClick(view: View, position:Int)
        fun onItemLongClick(view: View, position:Int)
    }

    fun setOnClickListener(clickListener: ClickListener) {
        mClickListener = clickListener
    }
}