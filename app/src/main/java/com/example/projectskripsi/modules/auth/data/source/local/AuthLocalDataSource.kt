package com.example.projectskripsi.modules.auth.data.source.local

import android.content.Context
import android.util.Log
import com.example.projectskripsi.MyApplication
import com.example.projectskripsi.core.Response
import com.example.projectskripsi.modules.auth.data.responses.UserResponse
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.subjects.PublishSubject

class AuthLocalDataSource {
    fun getUser(): Flowable<Response<UserResponse?>> {
        val response = PublishSubject.create<Response<UserResponse?>>()

        val sp = MyApplication.instance.applicationContext
            .getSharedPreferences("User", Context.MODE_PRIVATE)

        val user = UserResponse(
            id_user = sp.getString("id_user", null),
            nama = sp.getString("nama", null),
            email = sp.getString("email", null),
            password = sp.getString("password", null),
            tgl_lahir = sp.getString("tgl_lahir", null),
            gender = sp.getString("gender", null),
            alamat = sp.getString("alamat", null),
            telp = sp.getString("telp", null),
            level = sp.getString("level", null),
        )

        if (user.id_user != null) {
            Log.d("auth", user.toString())
            response.onNext(Response.Success(user))
        } else {
            response.onNext(Response.Empty)
        }

        return response.toFlowable(BackpressureStrategy.BUFFER)
    }

    fun saveUser(id: String?, nama: String?, email: String?, password: String?, tglLahir: String?, gender: String?, alamat: String?, telp: String?, level: String?): Flowable<Response<String?>> {
        val response = PublishSubject.create<Response<String?>>()

        val sp = MyApplication.instance.applicationContext
            .getSharedPreferences("User", Context.MODE_PRIVATE)

        val editor = sp.edit()
        editor.putString("id_user", id)
        editor.putString("nama", nama)
        editor.putString("email", email)
        editor.putString("password", password)
        editor.putString("tgl_lahir", tglLahir)
        editor.putString("gender", gender)
        editor.putString("alamat", alamat)
        editor.putString("telp", telp)
        editor.putString("level", level)
        editor.apply()

        val status = editor.commit()

        if (status) {
            response.onNext(Response.Success("success"))
        } else {
            response.onNext(Response.Error("error"))
        }

        return response.toFlowable(BackpressureStrategy.BUFFER)
    }
}