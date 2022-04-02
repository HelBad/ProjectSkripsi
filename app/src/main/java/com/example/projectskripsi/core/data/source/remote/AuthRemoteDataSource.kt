package com.example.projectskripsi.core.data.source.remote

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.example.projectskripsi.model.User
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class AuthRemoteDataSource {
    val firebase = FirebaseDatabase.getInstance()

    fun login(email: String, password: String): User? {
        var user: User? = null

        firebase.getReference("user").orderByChild("email")
            .equalTo(email).addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(p0: DataSnapshot) {
                    if (p0.exists()) {
                        for (h in p0.children) {
                            user = h.getValue(User::class.java)
                            Log.d("login", h.value.toString())
                        }
                    }
                }
                override fun onCancelled(p0: DatabaseError) {
                    Log.e("AuthRemoteDataSource", p0.message)
                }
            })

        Log.d("login", user.toString())
        return user
    }

    fun register(nama: String, email: String, password: String, tanggal: String, gender: String, alamat: String, telp: String): User? {
        var user: User? = null

        firebase.getReference("user").orderByChild("email")
            .equalTo(email).addListenerForSingleValueEvent( object : ValueEventListener {
                override fun onDataChange(p0: DataSnapshot) {
                    if(!p0.exists()) {
                        val ref = FirebaseDatabase.getInstance().getReference("user")
                        val idUser  = ref.push().key.toString()
                        val addData = User(idUser, nama, email, password, tanggal, gender, alamat, telp, "Pengguna")

                        ref.child(idUser).setValue(addData).addOnCompleteListener {
                            user = addData
                        }. addOnCanceledListener {
                            Log.e("AuthRemoteDataSource", "Gagal Register")
                        }
                    }
                    else {
                        Log.e("AuthRemoteDataSource", "Email sudah terdaftar")
                    }
                }
                override fun onCancelled(p0: DatabaseError) {
                    Log.e("AuthRemoteDataSource", p0.message)
                }
            })

        return user
    }
}