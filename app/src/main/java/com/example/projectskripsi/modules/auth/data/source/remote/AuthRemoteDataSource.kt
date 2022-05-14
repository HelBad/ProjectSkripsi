package com.example.projectskripsi.modules.auth.data.source.remote

import android.util.Log
import com.example.projectskripsi.core.Response
import com.example.projectskripsi.modules.auth.data.responses.UserResponse
import com.example.projectskripsi.utils.Converter
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.subjects.PublishSubject

class AuthRemoteDataSource {
    val firebase = FirebaseDatabase.getInstance()

    fun login(email: String, password: String): Flowable<Response<UserResponse?>> {
        val response = PublishSubject.create<Response<UserResponse?>>()

        firebase.getReference("user").orderByChild("email")
            .equalTo(email).addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(p0: DataSnapshot) {
                    if (p0.exists()) {
                        var user: UserResponse? = null
                        for (h in p0.children) {
                            Log.d("profil", h.toString())
                            user = Converter.toObject(h, UserResponse::class.java)
                            if (user != null && user.password == password){
                                response.onNext(Response.Success(user))
                                break
                            }
                        }

                        if (user == null) {
                            response.onNext(Response.Empty)
                        }
                    }
                    else {
                        response.onNext(Response.Empty)
                    }
                }
                override fun onCancelled(p0: DatabaseError) {
                    response.onNext(Response.Error(p0.message))
                    Log.e("AuthRemoteDataSource", p0.message)
                }
            })

        return response.toFlowable(BackpressureStrategy.BUFFER)
    }

    fun register(nama: String, email: String, password: String, tanggal: String, gender: String, alamat: String, telp: String): Flowable<Response<UserResponse?>> {
        val response = PublishSubject.create<Response<UserResponse?>>()

        firebase.getReference("user").orderByChild("email")
            .equalTo(email).addListenerForSingleValueEvent( object : ValueEventListener {
                override fun onDataChange(p0: DataSnapshot) {
                    if(!p0.exists()) {
                        val ref = firebase.getReference("user")
                        val idUser  = ref.push().key.toString()
                        val addData = UserResponse(idUser, nama, email, password, tanggal, gender, alamat, telp, "Pengguna")

                        ref.child(idUser).setValue(addData).addOnCompleteListener {
                            response.onNext(Response.Success(addData))
                        }. addOnCanceledListener {
                            response.onNext(Response.Error("Gagal Register"))
                            Log.e("AuthRemoteDataSource", "Gagal Register")
                        }
                    }
                    else {
                        response.onNext(Response.Error("Email sudah terdaftar"))
                        Log.e("AuthRemoteDataSource", "Email sudah terdaftar")
                    }
                }
                override fun onCancelled(p0: DatabaseError) {
                    response.onNext(Response.Error(p0.message))
                    Log.e("AuthRemoteDataSource", p0.message)
                }
            })

        return response.toFlowable(BackpressureStrategy.BUFFER)
    }
}