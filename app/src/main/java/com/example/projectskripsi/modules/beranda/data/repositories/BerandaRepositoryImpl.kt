package com.example.projectskripsi.modules.beranda.data.repositories

import android.annotation.SuppressLint
import com.example.projectskripsi.core.Resource
import com.example.projectskripsi.core.Response
import com.example.projectskripsi.modules.beranda.data.models.Menu
import com.example.projectskripsi.modules.beranda.data.models.Penyakit
import com.example.projectskripsi.modules.beranda.data.source.remote.BerandaRemoteDataSource
import com.example.projectskripsi.modules.beranda.domain.repositories.BerandaRepository
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
                        result.onNext(Resource.Success(it.data))
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
                        result.onNext(Resource.Success(it.data))
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