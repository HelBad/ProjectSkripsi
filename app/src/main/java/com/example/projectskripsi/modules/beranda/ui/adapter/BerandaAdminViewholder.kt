package com.example.projectskripsi.modules.beranda.ui.adapter

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.projectskripsi.R
import com.example.projectskripsi.modules.beranda.domain.entities.Menu
import com.example.projectskripsi.utils.Rupiah
import com.squareup.picasso.Picasso
import java.text.DecimalFormat
import java.text.NumberFormat

class BerandaAdminViewholder(itemView: View): RecyclerView.ViewHolder(itemView) {
    private var mView: View = itemView
    private var mClickListener: ClickListener? = null
    var menu = Menu()

    init {
        itemView.setOnClickListener { view -> mClickListener!!.onItemClick(view, adapterPosition) }
        itemView.setOnLongClickListener { view ->
            mClickListener!!.onItemLongClick(view, adapterPosition)
            true
        }
    }

    fun setDetails(menu: Menu) {
        this.menu = menu
        val namaListmenu = mView.findViewById(R.id.namaListmenuAdmin) as TextView
        val hargaListmenu = mView.findViewById(R.id.hargaListmenuAdmin) as TextView
        val deskripsiListmenu = mView.findViewById(R.id.deskripsiListmenuAdmin) as TextView
        val imgListmenu = mView.findViewById(R.id.imgListmenuAdmin) as ImageView

        namaListmenu.text = menu.namaMenu
        hargaListmenu.text = menu.harga?.toInt()?.let { Rupiah.format(it) }
        deskripsiListmenu.text = menu.deskripsi
        Picasso.get().load(menu.gambar).into(imgListmenu)
    }

    interface ClickListener {
        fun onItemClick(view: View, position:Int)
        fun onItemLongClick(view: View, position:Int)
    }

    fun setOnClickListener(clickListener: ClickListener) {
        mClickListener = clickListener
    }
}