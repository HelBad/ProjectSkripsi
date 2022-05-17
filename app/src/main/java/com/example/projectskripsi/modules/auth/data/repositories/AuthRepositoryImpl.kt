package com.example.projectskripsi.modules.auth.data.repositories

import android.annotation.SuppressLint
import android.util.Log
import com.example.projectskripsi.core.Resource
import com.example.projectskripsi.core.Response
import com.example.projectskripsi.modules.auth.data.source.local.AuthLocalDataSource
import com.example.projectskripsi.modules.auth.data.source.remote.AuthRemoteDataSource
import com.example.projectskripsi.modules.auth.domain.entities.User
import com.example.projectskripsi.modules.auth.domain.repositories.AuthRepository
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject

class AuthRepositoryImpl constructor(
    private val remoteDataSource: AuthRemoteDataSource,
    private val localDataSource: AuthLocalDataSource,
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
                    if (it.data != null) {
                        val user = User(
                            idUser = it.data.id_user,
                            nama = it.data.nama,
                            email = it.data.email,
                            password = it.data.password,
                            tglLahir = it.data.tgl_lahir,
                            gender = it.data.gender,
                            alamat = it.data.alamat,
                            telp = it.data.telp,
                            level = it.data.level
                        )
                        result.onNext(Resource.Success(user))
                    }
                    else {
                        result.onNext(Resource.Success(null))
                    }
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

    @SuppressLint("CheckResult")
    override fun register(nama: String, email: String, password: String, tanggal: String, gender: String, alamat: String, telp: String): Flowable<Resource<User?>> {
        val result = PublishSubject.create<Resource<User?>>()
        result.onNext(Resource.Loading())

        remoteDataSource.register(nama, email, password, tanggal, gender, alamat, telp)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .take(1)
            .subscribe {
                when(it){
                    is Response.Success -> {
                        if (it.data != null) {
                            val user = User(
                                idUser = it.data.id_user,
                                nama = it.data.nama,
                                email = it.data.email,
                                password = it.data.password,
                                tglLahir = it.data.tgl_lahir,
                                gender = it.data.gender,
                                alamat = it.data.alamat,
                                telp = it.data.telp,
                                level = it.data.level
                            )
                            result.onNext(Resource.Success(user))
                        }
                        else {
                            result.onNext(Resource.Success(null))
                        }
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

    @SuppressLint("CheckResult")
    override fun getUser(): Flowable<Resource<User?>> {
        val result = PublishSubject.create<Resource<User?>>()
        result.onNext(Resource.Loading())

        localDataSource.getUser()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .take(1)
            .subscribe {
                when(it){
                    is Response.Success -> {
                        Log.d("auth", it.data.toString())
                        if (it.data != null) {
                            val user = User(
                                idUser = it.data.id_user,
                                nama = it.data.nama,
                                email = it.data.email,
                                password = it.data.password,
                                tglLahir = it.data.tgl_lahir,
                                gender = it.data.gender,
                                alamat = it.data.alamat,
                                telp = it.data.telp,
                                level = it.data.level
                            )
                            result.onNext(Resource.Success(user))
                        }
                        else {
                            result.onNext(Resource.Success(null))
                        }
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

    @SuppressLint("CheckResult")
    override fun saveUser(id: String?, nama: String?, email: String?, password: String?, tglLahir: String?, gender: String?, alamat: String?, telp: String?, level: String?): Flowable<Resource<String?>> {
        val result = PublishSubject.create<Resource<String?>>()
        result.onNext(Resource.Loading())

        localDataSource.saveUser(id, nama, email, password, tglLahir, gender, alamat, telp, level)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .take(1)
            .subscribe {
                when(it){
                    is Response.Success -> {
                        Log.d("auth", it.data.toString())
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
}