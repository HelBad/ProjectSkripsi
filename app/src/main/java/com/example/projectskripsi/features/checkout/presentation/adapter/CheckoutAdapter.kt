package com.example.projectskripsi.features.checkout.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.projectskripsi.R
import com.example.projectskripsi.features.checkout.domain.entities.Keranjang
import com.example.projectskripsi.utils.Rupiah

class CheckoutAdapter(
    private val listKeranjang: ArrayList<Keranjang>,
) : RecyclerView.Adapter<CheckoutAdapter.CheckoutViewHolder>() {
    var onItemClick: ((Keranjang) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CheckoutViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.menu_checkout, parent, false)
        return CheckoutViewHolder(view)
    }

    override fun onBindViewHolder(holder: CheckoutViewHolder, position: Int) {
        holder.setDetails(listKeranjang[position])
    }

    override fun getItemCount(): Int = listKeranjang.size

    inner class CheckoutViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private var mView: View = itemView
        private var mClickListener: ClickListener? = null
        var keranjang = Keranjang()

        init {
            itemView.setOnClickListener { onItemClick?.invoke(listKeranjang[adapterPosition]) }
        }

        fun setDetails(keranjang: Keranjang) {
            this.keranjang = keranjang
            val namaCheckout = mView.findViewById(R.id.namaCheckout) as TextView
            val hargaCheckout = mView.findViewById(R.id.hargaCheckout) as TextView
            val jumlahCheckout = mView.findViewById(R.id.jumlahCheckout) as TextView

            namaCheckout.text = keranjang.namaMenu
            hargaCheckout.text = keranjang.total?.toInt()?.let { Rupiah.format(it) }
            jumlahCheckout.text = keranjang.jumlah
        }

        fun setOnClickListener(clickListener: ClickListener) {
            mClickListener = clickListener
        }
    }

    interface ClickListener {
        fun onItemClick(view: View, position:Int)
    }
}