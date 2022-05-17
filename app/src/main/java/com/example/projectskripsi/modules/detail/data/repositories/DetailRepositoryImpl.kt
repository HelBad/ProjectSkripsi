package com.example.projectskripsi.modules.detail.data.repositories

import android.annotation.SuppressLint
import android.util.Log
import com.example.projectskripsi.core.Resource
import com.example.projectskripsi.core.Response
import com.example.projectskripsi.modules.detail.data.source.local.DetailLocalDataSource
import com.example.projectskripsi.modules.detail.domain.entities.Menu
import com.example.projectskripsi.modules.detail.data.source.remote.DetailRemoteDataSource
import com.example.projectskripsi.modules.detail.domain.entities.Keranjang
import com.example.projectskripsi.modules.detail.domain.entities.User
import com.example.projectskripsi.modules.detail.domain.repositories.DetailRepository
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject

class DetailRepositoryImpl constructor(
    private val remoteDataSource: DetailRemoteDataSource,
    private val localDataSource: DetailLocalDataSource,
) : DetailRepository {

    @SuppressLint("CheckResult")
    override fun getDetailMenu(id: String): Flowable<Resource<Menu?>> {
        val result = PublishSubject.create<Resource<Menu?>>()
        result.onNext(Resource.Loading())

        remoteDataSource.getDetailMenu(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .take(1)
            .subscribe {
                when(it){
                    is Response.Success -> {
                        val res = it.data
                        if (res != null) {
                            val menu = Menu(
                                idMenu = res.idMenu,
                                namaMenu = res.namaMenu,
                                deskripsi = res.deskripsi,
                                lemak = res.lemak,
                                protein = res.protein,
                                kalori = res.kalori,
                                karbohidrat = res.karbohidrat,
                                harga = res.harga,
                                gambar = res.gambar
                            )
                            result.onNext(Resource.Success(menu))
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

    @SuppressLint("CheckResult")
    override fun getDetailKeranjang(
        idKeranjang: String,
        idUser: String
    ): Flowable<Resource<Keranjang?>> {
        val result = PublishSubject.create<Resource<Keranjang?>>()
        result.onNext(Resource.Loading())

        remoteDataSource.getDetailKeranjang(idKeranjang, idUser)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .take(1)
            .subscribe {
                when(it){
                    is Response.Success -> {
                        val res = it.data
                        if (res != null) {
                            val keranjang = Keranjang(
                                idKeranjang = res.idKeranjang,
                                idUser = res.idUser,
                                idMenu = res.idMenu,
                                jumlah = res.jumlah,
                                total = res.total,
                            )
                            result.onNext(Resource.Success(keranjang))
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

    @SuppressLint("CheckResult")
    override fun hapusPesanan(idKeranjang: String, idUser: String): Flowable<Resource<String?>> {
        val result = PublishSubject.create<Resource<String?>>()
        result.onNext(Resource.Loading())

        remoteDataSource.hapusPesanan(idKeranjang, idUser)
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

    @SuppressLint("CheckResult")
    override fun buatPesanan(
        idUser: String,
        idMenu: String,
        jumlah: String,
        total: String
    ): Flowable<Resource<String?>> {
        val result = PublishSubject.create<Resource<String?>>()
        result.onNext(Resource.Loading())

        remoteDataSource.buatPesanan(idUser, idMenu, jumlah, total)
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

    @SuppressLint("CheckResult")
    override fun updatePesanan(
        idKeranjang: String,
        jumlah: String,
        total: String,
        idUser: String
    ): Flowable<Resource<String?>> {
        val result = PublishSubject.create<Resource<String?>>()
        result.onNext(Resource.Loading())

        remoteDataSource.updatePesanan(idKeranjang, jumlah, total, idUser)
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
                    idUser = it?.idUser,
                    nama = it?.nama,
                    email = it?.email,
                    password = it?.password,
                    tglLahir = it?.tglLahir,
                    gender = it?.gender,
                    alamat = it?.alamat,
                    telp = it?.telp,
                    level = it?.level
                )
                result.onNext(Resource.Success(user))
            }

        return result.toFlowable(BackpressureStrategy.BUFFER)
    }
}