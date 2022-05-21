package com.example.projectskripsi.features.checkout.data.source.remote

import android.util.Log
import com.example.projectskripsi.core.Response
import com.example.projectskripsi.features.checkout.data.responses.KeranjangResponse
import com.example.projectskripsi.features.checkout.domain.entities.Keranjang
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

    fun getKeranjang(idUser: String): Flowable<Response<KeranjangResponse?>> {
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
        idMenu: String,
        jumlah: String,
        total: String
    ): Flowable<Response<String?>> {
        val response = PublishSubject.create<Response<String?>>()

        val ref = firebase.getReference("pesanan")
        val id = ref.push().key.toString()
        val data = Keranjang(id, idUser, idMenu, jumlah, total)
        ref.child(id).setValue(data).addOnCompleteListener {
            response.onNext(Response.Success(id))
        }.addOnCanceledListener {
            response.onNext(Response.Error("error"))
        }
        .addOnFailureListener {
            response.onNext(Response.Error(it.message.toString()))
        }

        return response.toFlowable(BackpressureStrategy.BUFFER)
    }
}