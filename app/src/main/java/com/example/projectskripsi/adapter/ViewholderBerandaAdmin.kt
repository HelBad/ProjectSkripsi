package com.example.projectskripsi.adapter

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.projectskripsi.R
import com.example.projectskripsi.model.Menu
import com.squareup.picasso.Picasso
import java.text.DecimalFormat
import java.text.NumberFormat

class ViewholderBerandaAdmin(itemView: View): RecyclerView.ViewHolder(itemView) {
    internal var mView: View = itemView
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

    fun setDetails(menu: Menu) {
        this.menu = menu
        val namaListmenu = mView.findViewById(R.id.namaListmenuAdmin) as TextView
        val hargaListmenu = mView.findViewById(R.id.hargaListmenuAdmin) as TextView
        val deskripsiListmenu = mView.findViewById(R.id.deskripsiListmenuAdmin) as TextView
        val imgListmenu = mView.findViewById(R.id.imgListmenuAdmin) as ImageView

        namaListmenu.text = menu.nama_menu
        hargaListmenu.text = "Rp. " + formatNumber.format(menu.harga.toInt()) + ",00"
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