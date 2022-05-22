package com.example.projectskripsi.features.menu.data.source.remote

import android.util.Log
import com.example.projectskripsi.core.Response
import com.example.projectskripsi.features.menu.data.responses.KeranjangResponse
import com.example.projectskripsi.features.menu.data.responses.MenuResponse
import com.example.projectskripsi.features.menu.data.responses.PenyakitResponse
import com.example.projectskripsi.utils.Converter
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.subjects.PublishSubject

class MenuRemoteDataSource {
    val firebase = FirebaseDatabase.getInstance()
    private val storage = FirebaseStorage.getInstance()

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
                    Log.e("DetailRemoteDataSource", error.message)
                }
            })

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
                    } else {
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

    fun hapusKeranjang(idKeranjang: String, idUser: String): Flowable<Response<String?>> {
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

    fun buatKeranjang(
        idUser: String,
        idMenu: String,
        jumlah: String,
        total: String
    ): Flowable<Response<String?>> {
        val response = PublishSubject.create<Response<String?>>()

        val ref = firebase.getReference("keranjang")
            .child("ready")
            .child(idUser)

        val id = ref.push().key.toString()
        val data = KeranjangResponse(id, idUser, idMenu, jumlah, total)
        Log.d("menu", data.toString())
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

    fun updateKeranjang(
        idKeranjang: String,
        jumlah: String,
        total: String,
        idUser: String
    ): Flowable<Response<String?>> {
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

    fun hapusMenu(idMenu: String): Flowable<Response<String?>> {
        val response = PublishSubject.create<Response<String?>>()

        firebase.getReference("menu").child(idMenu).removeValue()
            .addOnSuccessListener {
                storage.getReference("menu").child(idMenu).delete()
                    .addOnSuccessListener {
                        firebase.getReference("penyakit")
                            .orderByChild("id_menu")
                            .equalTo(idMenu)
                            .addValueEventListener(object : ValueEventListener {
                                override fun onDataChange(snapshot: DataSnapshot) {
                                    for (p0 in snapshot.children) {
                                        val penyakit = Converter.toObject(
                                            p0, PenyakitResponse::class.java
                                        )
                                        if (penyakit?.id_penyakit != null) {
                                            firebase.getReference("penyakit")
                                                .child(penyakit.id_penyakit!!).removeValue()
                                                .addOnSuccessListener {
                                                    response.onNext(Response.Success("Menu berhasil dihapus"))
                                                }
                                                .addOnFailureListener {
                                                    response.onNext(Response.Error(it.message.toString()))
                                                }
                                        }
                                    }
                                }

                                override fun onCancelled(error: DatabaseError) {
                                    response.onNext(Response.Error(error.message))
                                }
                            })
                    }
                    .addOnFailureListener {
                        response.onNext(Response.Error(it.message.toString()))
                    }
            }
            .addOnFailureListener {
                response.onNext(Response.Error(it.message.toString()))
            }

        return response.toFlowable(BackpressureStrategy.BUFFER)
    }
}