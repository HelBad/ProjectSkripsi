package com.example.projectskripsi.features.checkout.data.source.remote

import android.util.Log
import com.example.projectskripsi.core.Response
import com.example.projectskripsi.features.checkout.data.responses.KeranjangResponse
import com.example.projectskripsi.features.checkout.data.responses.MenuResponse
import com.example.projectskripsi.features.checkout.data.responses.PesananResponse
import com.example.projectskripsi.utils.Converter
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.subjects.PublishSubject

class CheckoutRemoteDataSource {
    val firebase = FirebaseDatabase.getInstance()

    fun getDetailKeranjang(idUser: String): Flowable<Response<KeranjangResponse?>> {
        val response = PublishSubject.create<Response<KeranjangResponse?>>()

        firebase.getReference("keranjang").child("ready").child(idUser)
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        for (snap in snapshot.children) {
                            val keranjang = Converter.toObject(snap, KeranjangResponse::class.java)
                            response.onNext(Response.Success(keranjang))
                        }
                    } else {
                        response.onNext(Response.Empty)
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    response.onNext(Response.Error(error.message))
                    Log.e("CheckoutRemoteDataSource", error.message)
                }
            })
        return response.toFlowable(BackpressureStrategy.BUFFER)
    }

    fun buatPesanan(
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

        val ref = firebase.getReference("pesanan")
        val id = ref.push().key.toString()
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
        ref.child("diproses").child(id).setValue(data)
            .addOnCompleteListener {
                firebase.getReference("keranjang")
                    .child("ready").child(idUser)
                    .addValueEventListener(object : ValueEventListener {
                        override fun onDataChange(snapshot: DataSnapshot) {
                            if (snapshot.exists()) {
                                val listKeranjang = hashMapOf<String?, KeranjangResponse>()
                                for (snap in snapshot.children) {
                                    val keranjang =
                                        Converter.toObject(snap, KeranjangResponse::class.java)
                                    if (keranjang != null) {
                                        listKeranjang[keranjang.id_keranjang] = keranjang
                                    }
                                }
                                firebase.getReference("keranjang").child("kosong")
                                    .child("$idUser | $idKeranjang").setValue(listKeranjang)
                                    .addOnSuccessListener {
                                        firebase.getReference("keranjang")
                                            .child("ready").child(idUser)
                                            .removeValue().addOnSuccessListener {
                                                response.onNext(Response.Success(id))
                                        }
                                            .addOnFailureListener {
                                                response.onNext(Response.Error(it.message.toString()))
                                            }
                                    }
                                    .addOnFailureListener {
                                        response.onNext(Response.Error(it.message.toString()))
                                    }
                            } else {
                                response.onNext(Response.Empty)
                            }
                        }

                        override fun onCancelled(error: DatabaseError) {
                            response.onNext(Response.Error(error.message))
                        }
                    })
                response.onNext(Response.Success(id))
            }.addOnCanceledListener {
                response.onNext(Response.Error("error"))
            }.addOnFailureListener {
                response.onNext(Response.Error(it.message.toString()))
            }
        return response.toFlowable(BackpressureStrategy.BUFFER)
    }

    fun getKeranjang(idUser: String): Flowable<Response<ArrayList<KeranjangResponse>>> {
        val response = PublishSubject.create<Response<ArrayList<KeranjangResponse>>>()

        firebase.getReference("keranjang").child("ready").child(idUser)
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        val list = arrayListOf<KeranjangResponse>()
                        for (snap in snapshot.children) {
                            val keranjang = Converter.toObject(snap, KeranjangResponse::class.java)
                            if (keranjang != null) {
                                list.add(keranjang)
                            }
                        }
                        response.onNext(Response.Success(list))
                    } else {
                        response.onNext(Response.Empty)
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    response.onNext(Response.Error(error.message))
                    Log.e("CheckoutRemoteDataSource", error.message)
                }
            })
        return response.toFlowable(BackpressureStrategy.BUFFER)
    }

    fun getDetailMenu(id: String): Flowable<Response<MenuResponse?>> {
        val response = PublishSubject.create<Response<MenuResponse?>>()

        firebase.getReference("menu").orderByChild("id_menu").equalTo(id)
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        for (snap in snapshot.children) {
                            val menu = Converter.toObject(snap, MenuResponse::class.java)
                            response.onNext(Response.Success(menu))
                        }
                    } else {
                        response.onNext(Response.Empty)
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    response.onNext(Response.Error(error.message))
                    Log.e(this.javaClass.name, error.message)
                }
            })
        return response.toFlowable(BackpressureStrategy.BUFFER)
    }
}