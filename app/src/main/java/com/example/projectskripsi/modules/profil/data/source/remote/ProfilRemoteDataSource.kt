package com.example.projectskripsi.modules.profil.data.source.remote

import com.example.projectskripsi.core.Response
import com.google.firebase.database.FirebaseDatabase
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.subjects.PublishSubject

class ProfilRemoteDataSource {
    val firebase = FirebaseDatabase.getInstance()

    fun updateUser(
        id: String,
        nama: String,
        email: String,
        password: String,
        tglLahir: String,
        gender: String,
        alamat: String,
        telp: String,
        level: String,
    ): Flowable<Response<String?>> {
        val response = PublishSubject.create<Response<String?>>()

        val map = HashMap<String, Any>()
        map["nama"] = nama
        map["email"] = email
        map["password"] = password
        map["tgl_lahir"] = tglLahir
        map["gender"] = gender
        map["alamat"] = alamat
        map["telp"] = telp
        map["level"] = level

        firebase.getReference("user")
            .child(id)
            .updateChildren(map)
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
}