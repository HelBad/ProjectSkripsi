package com.example.projectskripsi.admin.fragment

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.projectskripsi.R
import com.example.projectskripsi.adapter.ViewholderBerandaAdmin
import com.example.projectskripsi.admin.ActivityEdit
import com.example.projectskripsi.model.Menu
import com.example.projectskripsi.admin.ActivityDetail
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.google.firebase.database.FirebaseDatabase

class FragmentBeranda : Fragment() {
    lateinit var mLayoutManager: LinearLayoutManager
    lateinit var mRecyclerView: RecyclerView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.admin_fragment_beranda, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setHasOptionsMenu(true)
        val actionBar = requireActivity().findViewById(R.id.toolbarBeranda) as Toolbar
        (activity as AppCompatActivity).setSupportActionBar(actionBar)

        mLayoutManager = LinearLayoutManager(requireActivity())
        mRecyclerView = requireView().findViewById(R.id.recyclerBeranda)
        mRecyclerView.setHasFixedSize(true)
        mRecyclerView.layoutManager = mLayoutManager
    }

    override fun onCreateOptionsMenu(menu: android.view.Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)

        val inflater = requireActivity().menuInflater
        inflater.inflate(R.menu.bar_beranda_admin, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == R.id.tambahMenu) {
            val intent = Intent(activity, ActivityEdit::class.java)
            startActivity(intent)
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onStart() {
        super.onStart()
        val query = FirebaseDatabase.getInstance().getReference("menu")
        val firebaseRecyclerAdapter = object: FirebaseRecyclerAdapter<Menu, ViewholderBerandaAdmin>(
            Menu::class.java,
            R.layout.menu_admin_listmenu,
            ViewholderBerandaAdmin::class.java,
            query
        ) {
            override fun populateViewHolder(viewHolder: ViewholderBerandaAdmin, model: Menu, position:Int) {
                viewHolder.setDetails(model)
            }
            override fun onCreateViewHolder(parent:ViewGroup, viewType:Int): ViewholderBerandaAdmin {
                val viewHolder = super.onCreateViewHolder(parent, viewType)
                viewHolder.setOnClickListener(object: ViewholderBerandaAdmin.ClickListener {
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