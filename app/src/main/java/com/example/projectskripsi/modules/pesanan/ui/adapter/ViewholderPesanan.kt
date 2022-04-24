package com.example.projectskripsi.modules.pesanan.ui.adapter

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.projectskripsi.R
import com.example.projectskripsi.modules.pesanan.data.models.Pesanan
import java.text.DecimalFormat
import java.text.NumberFormat

class ViewholderPesanan(itemView: View): RecyclerView.ViewHolder(itemView) {
    private var mView: View = itemView
    private var mClickListener: ClickListener? = null
    var pesanan = Pesanan()
    var formatNumber: NumberFormat = DecimalFormat("#,###")

    init {
        itemView.setOnClickListener { view -> mClickListener!!.onItemClick(view, adapterPosition) }
        itemView.setOnLongClickListener { view ->
            mClickListener!!.onItemLongClick(view, adapterPosition)
            true
        }
    }

    fun setDetails(pesanan: Pesanan) {
        this.pesanan = pesanan
        val alamatPesanan = mView.findViewById(R.id.alamatPesanan) as TextView
        val waktuPesanan = mView.findViewById(R.id.waktuPesanan) as TextView
        val bayarPesanan = mView.findViewById(R.id.bayarPesanan) as TextView

        alamatPesanan.text = pesanan.lokasi
        waktuPesanan.text = pesanan.waktu
        bayarPesanan.text = "Rp. " + formatNumber.format(pesanan.total_bayar.toInt()) + ",00"
    }

    interface ClickListener {
        fun onItemClick(view: View, position:Int)
        fun onItemLongClick(view: View, position:Int)
    }

    fun setOnClickListener(clickListener: ClickListener) {
        mClickListener = clickListener
    }
}