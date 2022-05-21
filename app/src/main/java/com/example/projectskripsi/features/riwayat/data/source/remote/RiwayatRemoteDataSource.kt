package com.example.projectskripsi.features.riwayat.data.source.remote

import android.util.Log
import com.example.projectskripsi.core.Response
import com.example.projectskripsi.features.riwayat.data.responses.KeranjangResponse
import com.example.projectskripsi.features.riwayat.data.responses.MenuResponse
import com.example.projectskripsi.features.riwayat.data.responses.PesananResponse
import com.example.projectskripsi.features.riwayat.data.responses.UserResponse
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

    fun getUser(id: String): Flowable<Response<UserResponse?>> {
        val response = PublishSubject.create<Response<UserResponse?>>()

        firebase.getReference("user").orderByKey().equalTo(id)
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(p0: DataSnapshot) {
                    if (p0.exists()) {
                        for (snap in p0.children) {
                            val user = Converter.toObject(snap, UserResponse::class.java)
                            response.onNext(Response.Success(user))
                        }

                    } else {
                        response.onNext(Response.Empty)
                    }
                }

                override fun onCancelled(p0: DatabaseError) {
                    response.onNext(Response.Error(p0.message))
                    Log.e("RiwayatRemoteDataSource", p0.message)
                }
            })

        return response.toFlowable(BackpressureStrategy.BUFFER)
    }

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
                    Log.e("RiwayatRemoteDataSource", p0.message)
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
                    Log.e("RiwayatRemoteDataSource", error.message)
                }
            })

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
                            val keranjang = Converter.toObject(snap, KeranjangResponse::class.java)
                            response.onNext(Response.Success(keranjang))
                        }
                    }
                    else {
                        response.onNext(Response.Empty)
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    response.onNext(Response.Error(error.message))
                    Log.e("RiwayatRemoteDataSource", error.message)
                }
            })

        return response.toFlowable(BackpressureStrategy.BUFFER)
    }

    fun getMenu(idMenu: String): Flowable<Response<MenuResponse?>> {
        val response = PublishSubject.create<Response<MenuResponse?>>()

        firebase.getReference("menu").orderByChild("id_menu")
            .equalTo(idMenu).addValueEventListener(object : ValueEventListener {
                override fun onDataChange(p0: DataSnapshot) {
                    if (p0.exists()) {
                        for (snap in p0.children) {
                            val menu = Converter.toObject(snap, MenuResponse::class.java)
                            response.onNext(Response.Success(menu))
                        }

                    } else {
                        response.onNext(Response.Empty)
                    }
                }

                override fun onCancelled(p0: DatabaseError) {
                    response.onNext(Response.Error(p0.message))
                    Log.e("RiwayatRemoteDataSource", p0.message)
                }
            })

        return response.toFlowable(BackpressureStrategy.BUFFER)
    }

    fun updatePesanan(
        id: String,
        idUser: String,
        idKeranjang: String,
        catatan: String,
        waktu: String,
        lokasi: String,
        subtotal: String,
        ongkir: String,
        totalBayar: String,
        status: String,
        keterangan: String,
    ): Flowable<Response<String?>> {
        val response = PublishSubject.create<Response<String?>>()

        val data = PesananResponse(
            id,
            idUser,
            idKeranjang,
            catatan,
            waktu,
            lokasi,
            subtotal,
            ongkir,
            totalBayar,
            status,
            keterangan
        )
        firebase.getReference("pesanan").child(status).child(id)
            .setValue(data)
            .addOnCompleteListener {
                firebase.getReference("pesanan").child("diproses").child(id)
                    .removeValue()
                    .addOnCompleteListener {
                        response.onNext(Response.Success(id))
                    }.addOnCanceledListener {
                        response.onNext(Response.Error("error"))
                    }.addOnFailureListener {
                        response.onNext(Response.Error(it.message.toString()))
                    }
            }.addOnCanceledListener {
                response.onNext(Response.Error("error"))
            }.addOnFailureListener {
                response.onNext(Response.Error(it.message.toString()))
            }

        return response.toFlowable(BackpressureStrategy.BUFFER)
    }
}