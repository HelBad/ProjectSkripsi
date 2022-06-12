package com.example.projectskripsi.features.checkout.data.source.local

import android.content.Context
import com.example.projectskripsi.MyApplication
import com.example.projectskripsi.features.checkout.data.responses.UserResponse
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.Observable

class CheckoutLocalDataSource {
    fun getUser(): Flowable<UserResponse> {
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
}