package com.example.projectskripsi.features.pesanan.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.projectskripsi.R
import com.example.projectskripsi.features.pesanan.domain.entities.Pesanan
import com.example.projectskripsi.utils.Rupiah

class PesananAdapter(
    private val listPesanan: ArrayList<Pesanan>,
) : RecyclerView.Adapter<PesananAdapter.PesananViewHolder>() {
    var onItemClick: ((Pesanan) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PesananViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.menu_pesanan, parent, false)
        return PesananViewHolder(view)
    }

    override fun onBindViewHolder(holder: PesananViewHolder, position: Int) {
        holder.setDetails(listPesanan[position])
    }

    override fun getItemCount(): Int = listPesanan.size

    inner class PesananViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private var mView: View = itemView
        private var mClickListener: ClickListener? = null
        var pesanan = Pesanan()

        init {
            itemView.setOnClickListener { onItemClick?.invoke(listPesanan[adapterPosition]) }
        }

        fun setDetails(pesanan: Pesanan) {
            this.pesanan = pesanan
            val alamatPesanan = mView.findViewById(R.id.alamatPesanan) as TextView
            val waktuPesanan = mView.findViewById(R.id.waktuPesanan) as TextView
            val bayarPesanan = mView.findViewById(R.id.bayarPesanan) as TextView

            alamatPesanan.text = pesanan.lokasi
            waktuPesanan.text = pesanan.waktu
            bayarPesanan.text = pesanan.total_bayar?.toInt()?.let { Rupiah.format(it) }
        }

        fun setOnClickListener(clickListener: ClickListener) {
            mClickListener = clickListener
        }
    }

    interface ClickListener {
        fun onItemClick(view: View, position:Int)
    }
}