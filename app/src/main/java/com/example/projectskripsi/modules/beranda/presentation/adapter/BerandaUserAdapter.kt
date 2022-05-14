package com.example.projectskripsi.modules.beranda.presentation.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.projectskripsi.R
import com.example.projectskripsi.modules.beranda.domain.entities.Menu
import com.example.projectskripsi.modules.beranda.domain.entities.Penyakit
import com.example.projectskripsi.utils.Rupiah
import com.squareup.picasso.Picasso

class BerandaUserAdapter(
    private val listMenu: ArrayList<Menu>,
    private val listPenyakit: ArrayList<Penyakit>,
    private val kategori: String,
) : RecyclerView.Adapter<BerandaUserAdapter.BerandaViewHolder>() {
    var onItemClick: ((Menu) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BerandaViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.menu_listmenu, parent, false)
        return BerandaViewHolder(view)
    }

    override fun onBindViewHolder(holder: BerandaViewHolder, position: Int) {
        val menu = listMenu[position]
        holder.setDetails(menu, listPenyakit, kategori)
    }

    override fun getItemCount(): Int = listMenu.size

    inner class BerandaViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private var mView: View = itemView
        private var mClickListener: ClickListener? = null
        var menu = Menu()

        init {
            itemView.setOnClickListener { onItemClick?.invoke(listMenu[adapterPosition]) }
        }

        fun setDetails(menu: Menu, listPenyakit: ArrayList<Penyakit>, pilihKategori: String) {
            this.menu = menu
            val namaListmenu = mView.findViewById(R.id.namaListmenu) as TextView
            val hargaListmenu = mView.findViewById(R.id.hargaListmenu) as TextView
            val deskripsiListmenu = mView.findViewById(R.id.deskripsiListmenu) as TextView
            val imgListmenu = mView.findViewById(R.id.imgListmenu) as ImageView
            val kategoriListmenu = mView.findViewById(R.id.kategoriListmenu) as TextView

            namaListmenu.text = menu.namaMenu
            hargaListmenu.text = menu.harga?.toInt()?.let { Rupiah.format(it) }
            deskripsiListmenu.text = menu.deskripsi
            Picasso.get().load(menu.gambar).into(imgListmenu)

            for (list in listPenyakit) {
                if(list.idMenu == menu.idMenu) {
                    if(pilihKategori == "Sehat") {
                        if(list.sehat?.toInt() in -2..3) {
                            kategoriListmenu.text = "Kategori : Baik Dikonsumsi"
                            kategoriListmenu.setTextColor(Color.parseColor("#FF239D58"))
                        } else {
                            kategoriListmenu.text = "Kategori : Kurang Baik"
                            kategoriListmenu.setTextColor(Color.parseColor("#FFFF0000"))
                        }
                    } else if(pilihKategori == "Obesitas") {
                        if(list.obesitas?.toInt() in -2..3) {
                            kategoriListmenu.text = "Kategori : Baik Dikonsumsi"
                            kategoriListmenu.setTextColor(Color.parseColor("#FF239D58"))
                        } else {
                            kategoriListmenu.text = "Kategori : Kurang Baik"
                            kategoriListmenu.setTextColor(Color.parseColor("#FFFF0000"))
                        }
                    } else if(pilihKategori == "Diabetes") {
                        if(list.diabetes?.toInt() in -2..3) {
                            kategoriListmenu.text = "Kategori : Baik Dikonsumsi"
                            kategoriListmenu.setTextColor(Color.parseColor("#FF239D58"))
                        } else {
                            kategoriListmenu.text = "Kategori : Kurang Baik"
                            kategoriListmenu.setTextColor(Color.parseColor("#FFFF0000"))
                        }
                    } else if(pilihKategori == "Anemia") {
                        if(list.anemia?.toInt() in -2..3) {
                            kategoriListmenu.text = "Kategori : Baik Dikonsumsi"
                            kategoriListmenu.setTextColor(Color.parseColor("#FF239D58"))
                        } else {
                            kategoriListmenu.text = "Kategori : Kurang Baik"
                            kategoriListmenu.setTextColor(Color.parseColor("#FFFF0000"))
                        }
                    }
                }
            }
        }

        fun setOnClickListener(clickListener: ClickListener) {
            mClickListener = clickListener
        }
    }

    interface ClickListener {
        fun onItemClick(view: View, position:Int)
    }
}