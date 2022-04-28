package com.example.projectskripsi.modules.beranda.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.projectskripsi.*
import com.example.projectskripsi.modules.beranda.domain.entities.Penyakit
import com.example.projectskripsi.modules.beranda.ui.viewmodel.BerandaViewModel
import com.example.projectskripsi.modules.checkout.presentation.ActivityCheckout
import com.example.projectskripsi.modules.detail.presentation.ActivityDetailUser
import com.example.projectskripsi.modules.beranda.ui.ActivityNutrisi
import com.example.projectskripsi.modules.beranda.ui.ActivityRestoran
import com.example.projectskripsi.modules.beranda.ui.adapter.BerandaAdapter
import org.koin.android.viewmodel.ext.android.viewModel

class FragmentBerandaUser : Fragment() {
    private val berandaViewModel: BerandaViewModel by viewModel()

    lateinit var mLayoutManager: LinearLayoutManager
    lateinit var mRecyclerView: RecyclerView
    lateinit var keranjangBeranda: ImageView
    lateinit var spinnerBeranda: Spinner
    lateinit var penyakitBeranda: TextView
    var listPenyakit = arrayListOf<Penyakit>()
    val rekomendasiPenyakit = arrayOf("Sehat", "Obesitas", "Diabetes", "Anemia")

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_beranda, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setHasOptionsMenu(true)
        val actionBar = requireActivity().findViewById(R.id.toolbarBeranda) as Toolbar
        (activity as AppCompatActivity).setSupportActionBar(actionBar)

        keranjangBeranda = requireActivity().findViewById(R.id.keranjangBeranda)
        spinnerBeranda = requireActivity().findViewById(R.id.spinnerBeranda)
        penyakitBeranda = requireActivity().findViewById(R.id.penyakitBeranda)

        mLayoutManager = LinearLayoutManager(requireActivity())
        mRecyclerView = requireView().findViewById(R.id.recyclerBeranda)
        mRecyclerView.setHasFixedSize(true)
        mRecyclerView.layoutManager = mLayoutManager

        pilihPenyakit()
        keranjangBeranda.setOnClickListener {
            val intent = Intent(view.context, ActivityCheckout::class.java)
            startActivity(intent)
        }
    }

    override fun onStart() {
        super.onStart()
        berandaViewModel.getPenyakit().observe(viewLifecycleOwner) { res ->
            if (res.data != null) {
                listPenyakit.addAll(res.data)
            }
        }

        berandaViewModel.getMenu().observe(viewLifecycleOwner) { res ->
            val adapter = res.data?.let {
                BerandaAdapter(it, listPenyakit, rekomendasiPenyakit[0])
            }

            adapter?.onItemClick = { menu ->
                val intent = Intent(context, ActivityDetailUser::class.java)
                intent.putExtra("id_menu", menu.idMenu)
                startActivity(intent)
            }

            mRecyclerView.adapter = adapter
        }
    }


    //Pilih Penyakit
    fun pilihPenyakit() {
        spinnerBeranda.adapter = ArrayAdapter(requireActivity(), android.R.layout.simple_list_item_1, rekomendasiPenyakit)
        spinnerBeranda.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {}
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                penyakitBeranda.text = rekomendasiPenyakit[position]
                berandaViewModel.getMenu().observe(viewLifecycleOwner) { res ->
                    val adapter = res.data?.let {
                        BerandaAdapter(it, listPenyakit, rekomendasiPenyakit[position])
                    }

                    adapter?.onItemClick = { menu ->
                        val intent = Intent(context, ActivityDetailUser::class.java)
                        intent.putExtra("id_menu", menu.idMenu)
                        startActivity(intent)
                    }

                    mRecyclerView.adapter = adapter
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: android.view.Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)

        val inflater = requireActivity().menuInflater
        inflater.inflate(R.menu.bar_beranda, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == R.id.restoran) {
            val intent = Intent(activity, ActivityRestoran::class.java)
            startActivity(intent)
            return true
        } else if (id == R.id.nutrisi) {
            val intent = Intent(activity, ActivityNutrisi::class.java)
            startActivity(intent)
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}