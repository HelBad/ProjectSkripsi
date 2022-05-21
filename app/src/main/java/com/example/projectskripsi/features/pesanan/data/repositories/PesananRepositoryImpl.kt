package com.example.projectskripsi.features.pesanan.data.repositories

import android.annotation.SuppressLint
import com.example.projectskripsi.core.Resource
import com.example.projectskripsi.core.Response
import com.example.projectskripsi.features.pesanan.data.source.local.PesananLocalDataSource
import com.example.projectskripsi.features.pesanan.data.source.remote.PesananRemoteDataSource
import com.example.projectskripsi.features.pesanan.domain.entities.Keranjang
import com.example.projectskripsi.features.pesanan.domain.entities.Pesanan
import com.example.projectskripsi.features.pesanan.domain.entities.User
import com.example.projectskripsi.features.pesanan.domain.repositories.PesananRepository
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject

class PesananRepositoryImpl constructor(
    private val remoteDataSource: PesananRemoteDataSource,
    private val localDataSource: PesananLocalDataSource,
) : PesananRepository {

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
    override fun getPesanan(status: String, idUser: String?
    ): Flowable<Resource<ArrayList<Pesanan>>> {
        val result = PublishSubject.create<Resource<ArrayList<Pesanan>>>()
        result.onNext(Resource.Loading())

        remoteDataSource.getPesanan(status, idUser)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .take(1)
            .subscribe {
                when(it){
                    is Response.Success -> {
                        val list = arrayListOf<Pesanan>()
                        it.data.map { res ->
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
                            list.add(pesanan)
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