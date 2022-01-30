package com.example.projectskripsi.pengguna.fragment

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
import com.example.projectskripsi.model.Menu
import com.example.projectskripsi.adapter.ViewholderBeranda
import com.example.projectskripsi.pengguna.ActivityCheckout
import com.example.projectskripsi.pengguna.ActivityDetail
import com.example.projectskripsi.pengguna.ActivityNutrisi
import com.example.projectskripsi.pengguna.ActivityRestoran
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.google.firebase.database.FirebaseDatabase

class FragmentBeranda : Fragment() {
    lateinit var mLayoutManager: LinearLayoutManager
    lateinit var mRecyclerView: RecyclerView
    lateinit var keranjangBeranda: ImageView
    lateinit var spinnerBeranda: Spinner
    lateinit var penyakitBeranda: TextView

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

        val genderUser = arrayOf("Sehat", "Obesitas", "Diabetes", "Asam Lambung")
        spinnerBeranda.adapter = ArrayAdapter(requireActivity(), android.R.layout.simple_list_item_1, genderUser)
        spinnerBeranda.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
                penyakitBeranda.text = "Baik Untuk Penyakit"
            }
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                penyakitBeranda.text = genderUser[position]
            }
        }

        keranjangBeranda.setOnClickListener {
            val intent = Intent(view.context, ActivityCheckout::class.java)
            startActivity(intent)
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

    override fun onStart() {
        super.onStart()
        val query = FirebaseDatabase.getInstance().getReference("menu")
        val firebaseRecyclerAdapter = object: FirebaseRecyclerAdapter<Menu, ViewholderBeranda>(
            Menu::class.java,
            R.layout.menu_listmenu,
            ViewholderBeranda::class.java,
            query
        ) {
            override fun populateViewHolder(viewHolder: ViewholderBeranda, model: Menu, position:Int) {
                viewHolder.setDetails(model)
            }
            override fun onCreateViewHolder(parent:ViewGroup, viewType:Int): ViewholderBeranda {
                val viewHolder = super.onCreateViewHolder(parent, viewType)
                viewHolder.setOnClickListener(object: ViewholderBeranda.ClickListener {
                    override fun onItemClick(view:View, position:Int) {
                        val intent = Intent(view.context, ActivityDetail::class.java)
                        intent.putExtra("id_menu", viewHolder.menu.id_menu)
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