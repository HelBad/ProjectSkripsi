package com.example.projectskripsi.core.data.repositories

import android.annotation.SuppressLint
import com.example.projectskripsi.core.Response
import com.example.projectskripsi.core.data.source.remote.AuthRemoteDataSource
import com.example.projectskripsi.core.Resource
import com.example.projectskripsi.core.domain.repositories.AuthRepository
import com.example.projectskripsi.model.User
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject

class AuthRepositoryImpl constructor(
    private val remoteDataSource: AuthRemoteDataSource
) : AuthRepository {

    @SuppressLint("CheckResult")
    override fun login(email: String, password: String): Flowable<Resource<User?>> {
        val result = PublishSubject.create<Resource<User?>>()
        result.onNext(Resource.Loading())

        remoteDataSource.login(email, password)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .take(1)
            .subscribe {
            when(it){
                is Response.Success -> {
                    result.onNext(Resource.Success(it.data))
                }
                is Response.Empty -> {
                    result.onNext(Resource.Success(null))
                }
                is Response.Error -> {
                    result.onNext(Resource.Error(it.errorMessage, null))
                }
            }
        }

        return result.toFlowable(BackpressureStrategy.BUFFER)
    }

    override fun register(nama: String, email: String, password: String, tanggal: String, gender: String, alamat: String, telp: String): User? {
        return remoteDataSource.register(nama, email, password, tanggal, gender, alamat, telp)
    }
}