package com.example.projectskripsi.features.beranda.presentation.fragment

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.projectskripsi.R
import com.example.projectskripsi.features.beranda.presentation.adapter.BerandaAdminAdapter
import com.example.projectskripsi.features.beranda.presentation.viewmodel.BerandaViewModel
import com.example.projectskripsi.features.edit.presentation.ActivityEdit
import com.example.projectskripsi.features.detail.presentation.ActivityDetailAdmin
import org.koin.android.viewmodel.ext.android.viewModel

class FragmentBerandaAdmin : Fragment() {
    private val berandaViewModel: BerandaViewModel by viewModel()

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

    //Action Bar
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        requireActivity().menuInflater.inflate(R.menu.bar_beranda_admin, menu)
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

    //List Menu
    override fun onStart() {
        super.onStart()
        berandaViewModel.getMenu().observe(viewLifecycleOwner) { res ->
            val adapter = res.data?.let {
                BerandaAdminAdapter(it)
            }

            adapter?.onItemClick = { menu ->
                val intent = Intent(context, ActivityDetailAdmin::class.java)
                intent.putExtra("id_menu", menu.idMenu)
                startActivity(intent)
            }

            mRecyclerView.adapter = adapter
        }
    }
}