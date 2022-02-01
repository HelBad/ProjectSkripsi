package com.example.projectskripsi.admin.fragment

import android.content.Intent
import android.content.res.ColorStateList
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.projectskripsi.R
import com.example.projectskripsi.adapter.ViewholderPesanan
import com.example.projectskripsi.model.Pesanan
import com.example.projectskripsi.admin.ActivityRiwayat
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.Query

class FragmentPesanan : Fragment() {
    lateinit var databasePesanan: DatabaseReference
    lateinit var mLayoutManager: LinearLayoutManager
    lateinit var mRecyclerView: RecyclerView
    lateinit var btnDiproses: Button
    lateinit var btnSelesai: Button
    lateinit var btnDibatalkan: Button

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.admin_fragment_pesanan, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btnDiproses = requireActivity().findViewById(R.id.btnDiproses)
        btnSelesai = requireActivity().findViewById(R.id.btnSelesai)
        btnDibatalkan = requireActivity().findViewById(R.id.btnDibatalkan)

        mLayoutManager = LinearLayoutManager(requireActivity())
        mRecyclerView = requireView().findViewById(R.id.recyclerPesanan)
        mRecyclerView.setHasFixedSize(true)
        mRecyclerView.layoutManager = mLayoutManager

        databasePesanan = FirebaseDatabase.getInstance().getReference("pesanan")
        loadData()
    }

    private fun loadData() {
        val query = databasePesanan.child("diproses").orderByChild("status").equalTo("diproses")
        listData(query)
        btnDiproses.backgroundTintList = ColorStateList.valueOf(resources.getColor(R.color.colorAccent))
        btnSelesai.backgroundTintList = ColorStateList.valueOf(resources.getColor(R.color.colorPrimary))
        btnDibatalkan.backgroundTintList = ColorStateList.valueOf(resources.getColor(R.color.colorPrimary))

        btnDiproses.setOnClickListener {
            val query = databasePesanan.child("diproses").orderByChild("status").equalTo("diproses")
            listData(query)
            btnDiproses.backgroundTintList = ColorStateList.valueOf(resources.getColor(R.color.colorAccent))
            btnSelesai.backgroundTintList = ColorStateList.valueOf(resources.getColor(R.color.colorPrimary))
            btnDibatalkan.backgroundTintList = ColorStateList.valueOf(resources.getColor(R.color.colorPrimary))
        }
        btnSelesai.setOnClickListener {
            val query = databasePesanan.child("selesai").orderByChild("status").equalTo("selesai")
            listData(query)
            btnDiproses.backgroundTintList = ColorStateList.valueOf(resources.getColor(R.color.colorPrimary))
            btnSelesai.backgroundTintList = ColorStateList.valueOf(resources.getColor(R.color.colorAccent))
            btnDibatalkan.backgroundTintList = ColorStateList.valueOf(resources.getColor(R.color.colorPrimary))
        }
        btnDibatalkan.setOnClickListener {
            val query = databasePesanan.child("dibatalkan").orderByChild("status").equalTo("dibatalkan")
            listData(query)
            btnDiproses.backgroundTintList = ColorStateList.valueOf(resources.getColor(R.color.colorPrimary))
            btnSelesai.backgroundTintList = ColorStateList.valueOf(resources.getColor(R.color.colorPrimary))
            btnDibatalkan.backgroundTintList = ColorStateList.valueOf(resources.getColor(R.color.colorAccent))
        }
    }

    private fun listData(query: Query){
        val firebaseRecyclerAdapter = object: FirebaseRecyclerAdapter<Pesanan, ViewholderPesanan>(
            Pesanan::class.java,
            R.layout.menu_pesanan,
            ViewholderPesanan::class.java,
            query
        ) {
            override fun populateViewHolder(viewHolder: ViewholderPesanan, model: Pesanan, position:Int) {
                viewHolder.setDetails(model)
            }
            override fun onCreateViewHolder(parent:ViewGroup, viewType:Int): ViewholderPesanan {
                val viewHolder = super.onCreateViewHolder(parent, viewType)
                viewHolder.setOnClickListener(object: ViewholderPesanan.ClickListener {
                    override fun onItemClick(view:View, position:Int) {
                        val intent = Intent(view.context, ActivityRiwayat::class.java)
                        intent.putExtra("id_pesanan", viewHolder.pesanan.id_pesanan)
                        intent.putExtra("status", viewHolder.pesanan.status)
                        startActivity(intent)
                    }
                    override fun onItemLongClick(view:View, position:Int) {}
                })
                return viewHolder
            }
        }
        mRecyclerView.adapter = firebaseRecyclerAdapter
    }
}