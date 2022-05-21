package com.example.projectskripsi.features.checkout.data.repositories

import android.annotation.SuppressLint
import com.example.projectskripsi.core.Resource
import com.example.projectskripsi.core.Response
import com.example.projectskripsi.features.checkout.data.source.local.CheckoutLocalDataSource
import com.example.projectskripsi.features.checkout.data.source.remote.CheckoutRemoteDataSource
import com.example.projectskripsi.features.checkout.domain.entities.Keranjang
import com.example.projectskripsi.features.checkout.domain.entities.User
import com.example.projectskripsi.features.checkout.domain.repositories.CheckoutRepository
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject

class CheckoutRepositoryImpl constructor(
    private val remoteDataSource: CheckoutRemoteDataSource,
    private val localDataSource: CheckoutLocalDataSource,
) : CheckoutRepository {

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

    @SuppressLint("CheckResult")
    override fun buatPesanan(
        idUser: String,
        idKeranjang: String,
        catatan: String,
        waktu: String,
        lokasi: String,
        subtotal: String,
        ongkir: String,
        totalBayar: String,
        status: String,
        keterangan: String,
    ): Flowable<Resource<String?>> {
        val result = PublishSubject.create<Resource<String?>>()
        result.onNext(Resource.Loading())

        val source = remoteDataSource.buatPesanan(idUser, idKeranjang, catatan, waktu, lokasi, subtotal, ongkir, totalBayar, status, keterangan)

        source.subscribeOn(Schedulers.io())
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

    @SuppressLint("CheckResult")
    override fun getDetailKeranjang(idUser: String): Flowable<Resource<Keranjang?>> {
        val result = PublishSubject.create<Resource<Keranjang?>>()
        result.onNext(Resource.Loading())

        remoteDataSource.getDetailKeranjang(idUser)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .take(1)
            .subscribe {
                when(it){
                    is Response.Success -> {
                        val res = it.data
                        if (res != null) {
                            val keranjang = Keranjang(
                                idKeranjang = res.id_keranjang,
                                idUser = res.id_user,
                                idMenu = res.id_menu,
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
    override fun getKeranjang(idUser: String): Flowable<Resource<ArrayList<Keranjang>>> {
        val result = PublishSubject.create<Resource<ArrayList<Keranjang>>>()
        result.onNext(Resource.Loading())

        remoteDataSource.getKeranjang(idUser)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .take(1)
            .subscribe {
                when(it){
                    is Response.Success -> {
                        val list = arrayListOf<Keranjang>()
                        it.data.map { res ->
                            val keranjang = Keranjang(
                                    idKeranjang = res.id_keranjang,
                                    idUser = res.id_user,
                                    idMenu = res.id_menu,
                                    jumlah = res.jumlah,
                                    total = res.total,
                                    namaMenu = res.nama_menu,
                                )
                            list.add(keranjang)
                        }
                        result.onNext(Resource.Success(list))
                    }
                    is Response.Empty -> {
                        result.onNext(Resource.Success(arrayListOf()))
                    }
                    is Response.Error -> {
                        result.onNext(Resource.Error(it.errorMessage, null))
                    }
                }
            }

        return result.toFlowable(BackpressureStrategy.BUFFER)
    }
}