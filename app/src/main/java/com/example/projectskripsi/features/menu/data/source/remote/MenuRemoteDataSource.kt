package com.example.projectskripsi.features.menu.data.source.remote

import android.util.Log
import com.example.projectskripsi.core.Response
import com.example.projectskripsi.features.checkout.domain.entities.Keranjang
import com.example.projectskripsi.features.menu.data.responses.KeranjangResponse
import com.example.projectskripsi.features.menu.data.responses.MenuResponse
import com.example.projectskripsi.utils.Converter
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.subjects.PublishSubject

class MenuRemoteDataSource {
    val firebase = FirebaseDatabase.getInstance()

    fun getDetailMenu(id: String): Flowable<Response<MenuResponse?>> {
        val response = PublishSubject.create<Response<MenuResponse?>>()

        firebase.getReference("menu").orderByKey().equalTo(id)
            .addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (snap in snapshot.children) {
                        val menu = Converter.toObject(snap, MenuResponse::class.java)
                        response.onNext(Response.Success(menu))
                    }
                }
                else {
                    response.onNext(Response.Empty)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                response.onNext(Response.Error(error.message))
                Log.e("DetailRemoteDataSource", error.message)
            }
        })

        return response.toFlowable(BackpressureStrategy.BUFFER)
    }

    fun hapusMenu(idKeranjang: String, idUser: String): Flowable<Response<String?>> {
        val response = PublishSubject.create<Response<String?>>()

        firebase.getReference("keranjang")
            .child("ready")
            .child(idUser)
            .child(idKeranjang)
            .removeValue()
            .addOnCompleteListener {
                response.onNext(Response.Success("success"))
            }
            .addOnCanceledListener {
                response.onNext(Response.Error("error"))
            }
            .addOnFailureListener {
                response.onNext(Response.Error(it.message.toString()))
            }

        return response.toFlowable(BackpressureStrategy.BUFFER)
    }

    fun getDetailKeranjang(idMenu: String, idUser: String): Flowable<Response<KeranjangResponse?>> {
        val response = PublishSubject.create<Response<KeranjangResponse?>>()

        firebase.getReference("keranjang")
            .child("ready")
            .child(idUser)
            .orderByChild("id_menu")
            .equalTo(idMenu)
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
                    Log.e("DetailRemoteDataSource", error.message)
                }
            }
        )

        return response.toFlowable(BackpressureStrategy.BUFFER)
    }

    fun hapusPesanan(idKeranjang: String, idUser: String): Flowable<Response<String?>> {
        val response = PublishSubject.create<Response<String?>>()

        firebase.getReference("keranjang")
            .child("ready")
            .child(idUser)
            .child(idKeranjang)
            .removeValue()
            .addOnCompleteListener {
                response.onNext(Response.Success("success"))
            }
            .addOnCanceledListener {
                response.onNext(Response.Error("error"))
            }
            .addOnFailureListener {
                response.onNext(Response.Error(it.message.toString()))
            }

        return response.toFlowable(BackpressureStrategy.BUFFER)
    }

    fun buatPesanan(idUser: String, idMenu: String, jumlah: String, total: String): Flowable<Response<String?>> {
        val response = PublishSubject.create<Response<String?>>()

        val ref = firebase.getReference("keranjang")
            .child("ready")
            .child(idUser)

        val id = ref.push().key.toString()
        val data = Keranjang(id, idUser, idMenu, jumlah, total)
        ref.child(id).setValue(data).addOnCompleteListener {
            Log.d("detail", id)
            response.onNext(Response.Success(id))
        }
        .addOnCanceledListener {
            response.onNext(Response.Error("error"))
        }
        .addOnFailureListener {
            response.onNext(Response.Error(it.message.toString()))
        }

        return response.toFlowable(BackpressureStrategy.BUFFER)
    }

    fun updatePesanan(idKeranjang: String, jumlah: String, total: String, idUser: String): Flowable<Response<String?>> {
        val response = PublishSubject.create<Response<String?>>()

        val map = HashMap<String, Any>()
        map["total"] = total
        map["jumlah"] = jumlah

        firebase.getReference("keranjang")
            .child("ready")
            .child(idUser)
            .child(idKeranjang)
            .updateChildren(map)
            .addOnCompleteListener {
                Log.d("detail", idUser)
                response.onNext(Response.Success("success"))
            }
            .addOnCanceledListener {
                response.onNext(Response.Error("error"))
            }
            .addOnFailureListener {
                response.onNext(Response.Error(it.message.toString()))
            }

        return response.toFlowable(BackpressureStrategy.BUFFER)
    }
}