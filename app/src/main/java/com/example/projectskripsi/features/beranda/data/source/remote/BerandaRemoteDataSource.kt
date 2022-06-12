package com.example.projectskripsi.features.beranda.data.source.remote

import android.util.Log
import com.example.projectskripsi.core.Response
import com.example.projectskripsi.features.beranda.data.responses.MenuResponse
import com.example.projectskripsi.features.beranda.data.responses.PenyakitResponse
import com.example.projectskripsi.utils.Converter
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.subjects.PublishSubject

class BerandaRemoteDataSource {
    val firebase = FirebaseDatabase.getInstance()

    fun getMenu(): Flowable<Response<ArrayList<MenuResponse>>> {
        val response = PublishSubject.create<Response<ArrayList<MenuResponse>>>()

        firebase.getReference("menu")
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(p0: DataSnapshot) {
                    if (p0.exists()) {
                        val list = arrayListOf<MenuResponse>()
                        for (snap in p0.children) {
                            val menu = Converter.toObject(snap, MenuResponse::class.java)
                            if (menu != null) list.add(menu)
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

    fun getPenyakit(): Flowable<Response<ArrayList<PenyakitResponse>>> {
        val response = PublishSubject.create<Response<ArrayList<PenyakitResponse>>>()

        firebase.getReference("penyakit").addValueEventListener(object : ValueEventListener {
                override fun onDataChange(p0: DataSnapshot) {
                    if (p0.exists()) {
                        val list = arrayListOf<PenyakitResponse>()
                        for (snap in p0.children) {
                            val penyakit = Converter.toObject(snap, PenyakitResponse::class.java)
                            if (penyakit != null) list.add(penyakit)
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