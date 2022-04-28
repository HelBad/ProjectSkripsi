package com.example.projectskripsi.modules.detail.data.source.local

import android.content.Context
import com.example.projectskripsi.MyApplication
import com.example.projectskripsi.core.Response
import com.example.projectskripsi.modules.auth.data.responses.UserResponse
import com.google.gson.Gson
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.subjects.PublishSubject

class DetailLocalDataSource {
    fun getUser(): Flowable<Response<UserResponse?>> {
        val response = PublishSubject.create<Response<UserResponse?>>()

        val sp = MyApplication.instance.applicationContext
            .getSharedPreferences("User", Context.MODE_PRIVATE)

        val user = UserResponse(
            idUser = sp.getString("id_user", null),
            nama = sp.getString("nama", null),
            email = sp.getString("email", null),
            password = sp.getString("password", null),
            tglLahir = sp.getString("tgl_lahir", null),
            gender = sp.getString("gender", null),
            alamat = sp.getString("alamat", null),
            telp = sp.getString("telp", null),
            level = sp.getString("level", null),
        )

        if (user.idUser != null) {
            response.onNext(Response.Success(user))
        } else {
            response.onNext(Response.Empty)
        }

        return response.toFlowable(BackpressureStrategy.BUFFER)
    }
}