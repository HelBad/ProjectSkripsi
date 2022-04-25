package com.example.projectskripsi.modules.detail.data.source.remote

import android.util.Log
import com.example.projectskripsi.core.Response
import com.example.projectskripsi.modules.detail.domain.entities.Menu
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.gson.Gson
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.subjects.PublishSubject

class DetailRemoteDataSource {
    val firebase = FirebaseDatabase.getInstance()
    val gson = Gson()

    fun getDetailMenu(id: String): Flowable<Response<Menu?>> {
        val response = PublishSubject.create<Response<Menu?>>()

        firebase.getReference("menu").orderByKey().equalTo(id)
            .addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (snap in snapshot.children) {
                        val menu = snap.getValue(Menu::class.java)
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
}