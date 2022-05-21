package com.example.projectskripsi.features.riwayat.data.source.remote

import android.util.Log
import com.example.projectskripsi.core.Response
import com.example.projectskripsi.features.riwayat.data.responses.KeranjangResponse
import com.example.projectskripsi.features.riwayat.data.responses.PesananResponse
import com.example.projectskripsi.utils.Converter
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.subjects.PublishSubject

class RiwayatRemoteDataSource {
    val firebase = FirebaseDatabase.getInstance()

    fun getDetailPesanan(status: String, idPesanan: String?): Flowable<Response<PesananResponse?>> {
        val response = PublishSubject.create<Response<PesananResponse?>>()

        firebase.getReference("pesanan").child(status).orderByKey()
            .equalTo(idPesanan).addValueEventListener(object : ValueEventListener {
                override fun onDataChange(p0: DataSnapshot) {
                    if (p0.exists()) {
                        for (snap in p0.children) {
                            val pesanan = Converter.toObject(snap, PesananResponse::class.java)
                            response.onNext(Response.Success(pesanan))
                        }

                    } else {
                        response.onNext(Response.Empty)
                    }
                }

                override fun onCancelled(p0: DatabaseError) {
                    response.onNext(Response.Error(p0.message))
                    Log.e("PesananRemoteDataSource", p0.message)
                }
            })

        return response.toFlowable(BackpressureStrategy.BUFFER)
    }

    fun getKeranjang(idKeranjang: String, idUser: String): Flowable<Response<ArrayList<KeranjangResponse>>> {
        val response = PublishSubject.create<Response<ArrayList<KeranjangResponse>>>()

        firebase.getReference("keranjang")
            .child("kosong").child("$idUser | $idKeranjang")
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        val list = arrayListOf<KeranjangResponse>()
                        for (snap in snapshot.children) {
                            val keranjang = Converter.toObject(snap, KeranjangResponse::class.java)
                            if (keranjang != null) list.add(keranjang)
                        }

                        response.onNext(Response.Success(list))
                    }
                    else {
                        response.onNext(Response.Empty)
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    response.onNext(Response.Error(error.message))
                    Log.e("PesananRemoteDataSource", error.message)
                }
            }
            )

        return response.toFlowable(BackpressureStrategy.BUFFER)
    }

    fun getDetailKeranjang(idKeranjang: String, idUser: String): Flowable<Response<KeranjangResponse?>> {
        val response = PublishSubject.create<Response<KeranjangResponse?>>()

        firebase.getReference("keranjang").child("kosong")
            .child("$idUser | $idKeranjang").orderByKey()
            .equalTo(idKeranjang)
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        for (snap in snapshot.children) {
                            val menu = Converter.toObject(snap, KeranjangResponse::class.java)
                            response.onNext(Response.Success(menu))
                        }
                    }
                    else {
                        response.onNext(Response.Empty)
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    response.onNext(Response.Error(error.message))
                    Log.e("PesananRemoteDataSource", error.message)
                }
            }
            )

        return response.toFlowable(BackpressureStrategy.BUFFER)
    }
}