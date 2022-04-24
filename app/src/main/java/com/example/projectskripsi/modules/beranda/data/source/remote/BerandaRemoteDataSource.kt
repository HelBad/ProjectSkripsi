package com.example.projectskripsi.modules.beranda.data.source.remote

import android.util.Log
import com.example.projectskripsi.core.Response
import com.example.projectskripsi.modules.beranda.data.models.Menu
import com.example.projectskripsi.modules.beranda.data.models.Penyakit
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.subjects.PublishSubject

class BerandaRemoteDataSource {
    val firebase = FirebaseDatabase.getInstance()

    fun getMenu(): Flowable<Response<ArrayList<Menu>>> {
        val response = PublishSubject.create<Response<ArrayList<Menu>>>()

        firebase.getReference("menu")
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(p0: DataSnapshot) {
                    if (p0.exists()) {
                        val list = arrayListOf<Menu>()
                        for (snap in p0.children) {
                            val menu = snap.getValue(Menu::class.java)
                            if (menu != null) {
                                list.add(menu)
                            }
                        }

                        response.onNext(Response.Success(list))
                    }
                    else {
                        response.onNext(Response.Empty)
                    }
                }
                override fun onCancelled(p0: DatabaseError) {
                    response.onNext(Response.Error(p0.message))
                    Log.e("BerandaRemoteDataSource", p0.message)
                }
            })

        return response.toFlowable(BackpressureStrategy.BUFFER)
    }

    fun getPenyakit(): Flowable<Response<ArrayList<Penyakit>>> {
        val response = PublishSubject.create<Response<ArrayList<Penyakit>>>()

        firebase.getReference("penyakit")
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(p0: DataSnapshot) {
                    if (p0.exists()) {
                        val list = arrayListOf<Penyakit>()
                        for (snap in p0.children) {
                            val menu = snap.getValue(Penyakit::class.java)
                            if (menu != null) {
                                list.add(menu)
                            }
                        }

                        response.onNext(Response.Success(list))
                    }
                    else {
                        response.onNext(Response.Empty)
                    }
                }
                override fun onCancelled(p0: DatabaseError) {
                    response.onNext(Response.Error(p0.message))
                    Log.e("BerandaRemoteDataSource", p0.message)
                }
            })

        return response.toFlowable(BackpressureStrategy.BUFFER)
    }
}