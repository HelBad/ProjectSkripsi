package com.example.projectskripsi.features.beranda.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.projectskripsi.R
import com.example.projectskripsi.features.beranda.domain.entities.Menu
import com.example.projectskripsi.utils.Rupiah
import com.squareup.picasso.Picasso

class BerandaAdminAdapter(
    private val listMenu: ArrayList<Menu>,
) : RecyclerView.Adapter<BerandaAdminAdapter.BerandaViewHolder>() {
    var onItemClick: ((Menu) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BerandaViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.menu_admin_listmenu, parent, false)
        return BerandaViewHolder(view)
    }

    override fun onBindViewHolder(holder: BerandaViewHolder, position: Int) {
        val menu = listMenu[position]
        holder.setDetails(menu)
    }

    override fun getItemCount(): Int = listMenu.size

    inner class BerandaViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private var mView: View = itemView
        private var mClickListener: ClickListener? = null
        var menu = Menu()

        init {
            itemView.setOnClickListener { onItemClick?.invoke(listMenu[adapterPosition]) }
        }

        fun setDetails(menu: Menu) {
            this.menu = menu
            val namaListmenu = mView.findViewById(R.id.namaListmenuAdmin) as TextView
            val hargaListmenu = mView.findViewById(R.id.hargaListmenuAdmin) as TextView
            val deskripsiListmenu = mView.findViewById(R.id.deskripsiListmenuAdmin) as TextView
            val imgListmenu = mView.findViewById(R.id.imgListmenuAdmin) as ImageView

            namaListmenu.text = menu.nama_menu
            hargaListmenu.text = menu.harga?.toInt()?.let { Rupiah.format(it) }
            deskripsiListmenu.text = menu.deskripsi
            Picasso.get().load(menu.gambar).into(imgListmenu)
        }

        fun setOnClickListener(clickListener: ClickListener) {
            mClickListener = clickListener
        }
    }

    interface ClickListener {
        fun onItemClick(view: View, position:Int)
    }
}