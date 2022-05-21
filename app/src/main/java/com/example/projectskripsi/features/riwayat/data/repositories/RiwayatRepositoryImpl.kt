package com.example.projectskripsi.features.riwayat.data.repositories

import android.annotation.SuppressLint
import com.example.projectskripsi.core.Resource
import com.example.projectskripsi.core.Response
import com.example.projectskripsi.features.riwayat.domain.entities.User
import com.example.projectskripsi.features.riwayat.data.source.local.RiwayatLocalDataSource
import com.example.projectskripsi.features.riwayat.data.source.remote.RiwayatRemoteDataSource
import com.example.projectskripsi.features.riwayat.domain.entities.Keranjang
import com.example.projectskripsi.features.riwayat.domain.entities.Pesanan
import com.example.projectskripsi.features.riwayat.domain.repositories.RiwayatRepository
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject

class RiwayatRepositoryImpl constructor(
    private val remoteDataSource: RiwayatRemoteDataSource,
    private val localDataSource: RiwayatLocalDataSource,
) : RiwayatRepository {

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
    override fun getDetailPesanan(status: String, idPesanan: String): Flowable<Resource<Pesanan?>> {
        val result = PublishSubject.create<Resource<Pesanan?>>()
        result.onNext(Resource.Loading())

        remoteDataSource.getDetailPesanan(status, idPesanan)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .take(1)
            .subscribe {
                when(it){
                    is Response.Success -> {
                        val res = it.data
                        if (res != null) {
                            val pesanan = Pesanan(
                                id_pesanan = res.idPesanan,
                                id_user = res.idUser,
                                id_keranjang = res.idKeranjang,
                                catatan = res.catatan,
                                waktu = res.waktu,
                                lokasi = res.lokasi,
                                subtotal = res.subtotal,
                                ongkir = res.ongkir,
                                total_bayar = res.totalBayar,
                                status = res.status,
                                keterangan = res.keterangan,
                            )
                            result.onNext(Resource.Success(pesanan))
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
    override fun getKeranjang(
        idKeranjang: String,
        idUser: String
    ): Flowable<Resource<ArrayList<Keranjang>>> {
        val result = PublishSubject.create<Resource<ArrayList<Keranjang>>>()
        result.onNext(Resource.Loading())

        remoteDataSource.getKeranjang(idKeranjang, idUser)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .take(1)
            .subscribe {
                when(it){
                    is Response.Success -> {
                        val list = arrayListOf<Keranjang>()
                        it.data.map { res ->
                            val keranjang = Keranjang(
                                idKeranjang = res.idKeranjang,
                                idUser = res.idUser,
                                idMenu = res.idMenu,
                                jumlah = res.jumlah,
                                total = res.total,
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
}