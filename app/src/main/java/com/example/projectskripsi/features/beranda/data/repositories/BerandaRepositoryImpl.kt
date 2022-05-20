package com.example.projectskripsi.features.beranda.data.repositories

import android.annotation.SuppressLint
import com.example.projectskripsi.core.Resource
import com.example.projectskripsi.core.Response
import com.example.projectskripsi.features.beranda.domain.entities.Menu
import com.example.projectskripsi.features.beranda.domain.entities.Penyakit
import com.example.projectskripsi.features.beranda.data.source.remote.BerandaRemoteDataSource
import com.example.projectskripsi.features.beranda.domain.repositories.BerandaRepository
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject

class BerandaRepositoryImpl constructor(
    private val remoteDataSource: BerandaRemoteDataSource
) : BerandaRepository {

    @SuppressLint("CheckResult")
    override fun getMenu(): Flowable<Resource<ArrayList<Menu>>> {
        val result = PublishSubject.create<Resource<ArrayList<Menu>>>()
        result.onNext(Resource.Loading())

        remoteDataSource.getMenu()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .take(1)
            .subscribe {
                when(it){
                    is Response.Success -> {
                        val list = arrayListOf<Menu>()
                        it.data.map { res ->
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
                            list.add(menu)
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
    override fun getPenyakit(): Flowable<Resource<ArrayList<Penyakit>>> {
        val result = PublishSubject.create<Resource<ArrayList<Penyakit>>>()
        result.onNext(Resource.Loading())

        remoteDataSource.getPenyakit()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .take(1)
            .subscribe {
                when(it){
                    is Response.Success -> {
                        val list = arrayListOf<Penyakit>()
                        it.data.map { res ->
                            val penyakit = Penyakit(
                                idPenyakit = res.idPenyakit,
                                idMenu = res.idMenu,
                                sehat = res.sehat,
                                diabetes = res.diabetes,
                                obesitas = res.obesitas,
                                anemia = res.anemia,
                            )
                            list.add(penyakit)
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