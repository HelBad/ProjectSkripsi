package com.example.projectskripsi.features.profil.data.source.local

import android.content.Context
import com.example.projectskripsi.MyApplication
import com.example.projectskripsi.features.profil.data.responses.UserResponse
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.Observable

class ProfilLocalDataSource {
    fun getUser(): Flowable<UserResponse?> {
        val sp = MyApplication.instance.applicationContext.getSharedPreferences(
            "User",
            Context.MODE_PRIVATE
        )

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

        return Observable.create<UserResponse?> {
            it.onNext(user)
            it.onComplete()
        }.toFlowable(BackpressureStrategy.BUFFER)
    }

    fun saveUser(
        id: String?,
        nama: String?,
        email: String?,
        password: String?,
        tglLahir: String?,
        gender: String?,
        alamat: String?,
        telp: String?,
        level: String?
    ): Flowable<String> {
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

        return if (status) {
            Observable.create<String> {
                it.onNext("success")
                it.onComplete()
            }.toFlowable(BackpressureStrategy.BUFFER)
        } else {
            Observable.create<String> {
                it.onNext("error")
                it.onComplete()
            }.toFlowable(BackpressureStrategy.BUFFER)
        }
    }
}