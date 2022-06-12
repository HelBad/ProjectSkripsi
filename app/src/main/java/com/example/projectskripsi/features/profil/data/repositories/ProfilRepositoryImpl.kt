package com.example.projectskripsi.features.profil.data.repositories

import android.annotation.SuppressLint
import com.example.projectskripsi.core.Resource
import com.example.projectskripsi.core.Response
import com.example.projectskripsi.features.profil.data.source.local.ProfilLocalDataSource
import com.example.projectskripsi.features.profil.data.source.remote.ProfilRemoteDataSource
import com.example.projectskripsi.features.profil.domain.entities.User
import com.example.projectskripsi.features.profil.domain.repositories.ProfilRepository
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject

class ProfilRepositoryImpl constructor(
    private val remoteDataSource: ProfilRemoteDataSource,
    private val localDataSource: ProfilLocalDataSource,
) : ProfilRepository {

    @SuppressLint("CheckResult")
    override fun getUser(): Flowable<Resource<User?>> {
        val result = PublishSubject.create<Resource<User?>>()
        result.onNext(Resource.Loading())

        val source = localDataSource.getUser()

        source.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .take(1)
            .subscribe {
                val user = User(
                    id_user = it?.id_user,
                    nama = it?.nama,
                    email = it?.email,
                    password = it?.password,
                    tgl_lahir = it?.tgl_lahir,
                    gender = it?.gender,
                    alamat = it?.alamat,
                    telp = it?.telp,
                    level = it?.level
                )
                result.onNext(Resource.Success(user))
            }

        return result.toFlowable(BackpressureStrategy.BUFFER)
    }

    @SuppressLint("CheckResult")
    override fun saveUser(
        id: String?,
        nama: String?,
        email: String?,
        password: String?,
        tglLahir: String?,
        gender: String?,
        alamat: String?,
        telp: String?,
        level: String?
    ): Flowable<Resource<String?>> {
        val result = PublishSubject.create<Resource<String?>>()
        result.onNext(Resource.Loading())

        localDataSource.saveUser(id, nama, email, password, tglLahir, gender, alamat, telp, level)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .take(1)
            .subscribe {
                if (it == "success") {
                    result.onNext(Resource.Success(it))
                } else {
                    result.onNext(Resource.Error(it))
                }
            }
        return result.toFlowable(BackpressureStrategy.BUFFER)
    }

    @SuppressLint("CheckResult")
    override fun updateUser(
        id: String,
        nama: String,
        email: String,
        password: String,
        tglLahir: String,
        gender: String,
        alamat: String,
        telp: String,
        level: String
    ): Flowable<Resource<String?>> {
        val result = PublishSubject.create<Resource<String?>>()
        result.onNext(Resource.Loading())

        remoteDataSource.updateUser(id, nama, email, password, tglLahir, gender, alamat, telp, level)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .take(1)
            .subscribe {
                when(it){
                    is Response.Success -> {
                        val res = it.data
                        if (res != null) {
                            result.onNext(Resource.Success(res))
                        } else {
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
}