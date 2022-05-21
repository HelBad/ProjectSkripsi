package com.example.projectskripsi.features.pesanan.presentation.fragment

import android.content.Intent
import android.content.res.ColorStateList
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.projectskripsi.R
import com.example.projectskripsi.features.riwayat.presentation.ActivityRiwayatUser
import com.example.projectskripsi.features.pesanan.presentation.adapter.PesananAdapter
import com.example.projectskripsi.features.pesanan.presentation.viewmodel.PesananViewModel
import com.example.projectskripsi.features.riwayat.presentation.ActivityRiwayatAdmin
import org.koin.android.viewmodel.ext.android.viewModel

class FragmentPesananAdmin : Fragment() {
    private val pesananViewModel: PesananViewModel by viewModel()

    private lateinit var mLayoutManager: LinearLayoutManager
    private lateinit var mRecyclerView: RecyclerView
    private lateinit var btnDiproses: Button
    private lateinit var btnSelesai: Button
    private lateinit var btnDibatalkan: Button

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
    }

    override fun onStart() {
        super.onStart()
        loadData()
    }

    //Kategori Data Pesanan
    private fun loadData() {
        btnDiproses.setOnClickListener {
            listData("diproses")
            btnDiproses.backgroundTintList =
                ColorStateList.valueOf(resources.getColor(R.color.colorAccent))
            btnSelesai.backgroundTintList =
                ColorStateList.valueOf(resources.getColor(R.color.colorPrimary))
            btnDibatalkan.backgroundTintList =
                ColorStateList.valueOf(resources.getColor(R.color.colorPrimary))
        }
        btnSelesai.setOnClickListener {
            listData("selesai")
            btnDiproses.backgroundTintList =
                ColorStateList.valueOf(resources.getColor(R.color.colorPrimary))
            btnSelesai.backgroundTintList =
                ColorStateList.valueOf(resources.getColor(R.color.colorAccent))
            btnDibatalkan.backgroundTintList =
                ColorStateList.valueOf(resources.getColor(R.color.colorPrimary))
        }
        btnDibatalkan.setOnClickListener {
            listData("dibatalkan")
            btnDiproses.backgroundTintList =
                ColorStateList.valueOf(resources.getColor(R.color.colorPrimary))
            btnSelesai.backgroundTintList =
                ColorStateList.valueOf(resources.getColor(R.color.colorPrimary))
            btnDibatalkan.backgroundTintList =
                ColorStateList.valueOf(resources.getColor(R.color.colorAccent))
        }

        listData("diproses")
        btnDiproses.backgroundTintList =
            ColorStateList.valueOf(resources.getColor(R.color.colorAccent))
        btnSelesai.backgroundTintList =
            ColorStateList.valueOf(resources.getColor(R.color.colorPrimary))
        btnDibatalkan.backgroundTintList =
            ColorStateList.valueOf(resources.getColor(R.color.colorPrimary))
    }

    //List Pesanan
    private fun listData(status: String) {
        pesananViewModel.getPesanan(status, null)
            .observe(viewLifecycleOwner) { res ->
                if (res.data != null) {
                    val adapter = PesananAdapter(res.data)

                    adapter.onItemClick = { pesanan ->
                        val intent = Intent(context, ActivityRiwayatAdmin::class.java)
                        intent.putExtra("id_pesanan", pesanan.id_pesanan)
                        intent.putExtra("status", pesanan.status)
                        startActivity(intent)
                    }

                    mRecyclerView.adapter = adapter
                }
            }
    }
}