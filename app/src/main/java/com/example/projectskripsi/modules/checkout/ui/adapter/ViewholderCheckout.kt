package com.example.projectskripsi.modules.checkout.ui.adapter

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.projectskripsi.R
import com.example.projectskripsi.modules.checkout.domain.entities.Keranjang
import com.example.projectskripsi.modules.beranda.domain.entities.Menu
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.text.DecimalFormat
import java.text.NumberFormat

class ViewholderCheckout(itemView: View): RecyclerView.ViewHolder(itemView) {
    private var mView: View = itemView
    private var mClickListener: ClickListener? = null
    var keranjang = Keranjang()
    var formatNumber: NumberFormat = DecimalFormat("#,###")

    init {
        itemView.setOnClickListener { view -> mClickListener!!.onItemClick(view, adapterPosition) }
        itemView.setOnLongClickListener { view ->
            mClickListener!!.onItemLongClick(view, adapterPosition)
            true
        }
    }

    fun setDetails(keranjang: Keranjang) {
        this.keranjang = keranjang
        val namaCheckout = mView.findViewById(R.id.namaCheckout) as TextView
        val hargaCheckout = mView.findViewById(R.id.hargaCheckout) as TextView
        val jumlahCheckout = mView.findViewById(R.id.jumlahCheckout) as TextView

        val query = FirebaseDatabase.getInstance().getReference("menu")
            .orderByChild("id_menu").equalTo(keranjang.id_menu)
        query.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(datasnapshot: DataSnapshot) {
                for (snapshot1 in datasnapshot.children) {
                    val allocation = snapshot1.getValue(Menu::class.java)
                    namaCheckout.text = allocation?.namaMenu
                    hargaCheckout.text = "Rp. " + formatNumber.format(keranjang.total.toInt()) + ",00"
                    jumlahCheckout.text = keranjang.jumlah
                }
            }
            override fun onCancelled(databaseError: DatabaseError) {}
        })
    }

    interface ClickListener {
        fun onItemClick(view: View, position:Int)
        fun onItemLongClick(view: View, position:Int)
    }

    fun setOnClickListener(clickListener: ClickListener) {
        mClickListener = clickListener
    }
}