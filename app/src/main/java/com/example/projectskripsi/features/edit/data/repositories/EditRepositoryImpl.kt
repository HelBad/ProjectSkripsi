package com.example.projectskripsi.features.edit.data.repositories

import android.annotation.SuppressLint
import android.net.Uri
import com.example.projectskripsi.core.Resource
import com.example.projectskripsi.core.Response
import com.example.projectskripsi.features.edit.data.source.remote.EditRemoteDataSource
import com.example.projectskripsi.features.edit.domain.entities.Menu
import com.example.projectskripsi.features.edit.domain.entities.Penyakit
import com.example.projectskripsi.features.edit.domain.repositories.EditRepository
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject

class EditRepositoryImpl constructor(
    private val remoteDataSource: EditRemoteDataSource,
) : EditRepository {
    @SuppressLint("CheckResult")
    override fun getDetailMenu(id_menu: String): Flowable<Resource<Menu?>> {
        val result = PublishSubject.create<Resource<Menu?>>()
        result.onNext(Resource.Loading())

        remoteDataSource.getDetailMenu(id_menu)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .take(1)
            .subscribe {
                when (it) {
                    is Response.Success -> {
                        val res = it.data
                        if (res != null) {
                            val menu = Menu(
                                id_menu = res.id_menu,
                                nama_menu = res.nama_menu,
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
    override fun getDetailPenyakit(id_menu: String): Flowable<Resource<Penyakit?>> {
        val result = PublishSubject.create<Resource<Penyakit?>>()
        result.onNext(Resource.Loading())

        remoteDataSource.getDetailPenyakit(id_menu)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .take(1)
            .subscribe {
                when (it) {
                    is Response.Success -> {
                        val res = it.data
                        if (res != null) {
                            val penyakit = Penyakit(
                                id_penyakit = res.id_penyakit,
                                id_menu = res.id_menu,
                                sehat = res.sehat,
                                diabetes = res.diabetes,
                                jantung = res.jantung,
                                kelelahan = res.kelelahan,
                                obesitas = res.obesitas,
                                sembelit = res.sembelit
                            )
                            result.onNext(Resource.Success(penyakit))
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
    override fun buatMenu(
        nama: String,
        deskripsi: String,
        lemak: String,
        protein: String,
        kalori: String,
        karbohidrat: String,
        harga: String,
        gambar: String
    ): Flowable<Resource<String?>> {
        val result = PublishSubject.create<Resource<String?>>()
        result.onNext(Resource.Loading())

        remoteDataSource.buatMenu(
            nama,
            deskripsi,
            lemak,
            protein,
            kalori,
            karbohidrat,
            harga,
            gambar
        )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .take(1)
            .subscribe {
                when (it) {
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
    override fun updateMenu(
        id: String,
        nama: String,
        deskripsi: String,
        lemak: String,
        protein: String,
        kalori: String,
        karbohidrat: String,
        harga: String,
        gambar: String
    ): Flowable<Resource<String?>> {
        val result = PublishSubject.create<Resource<String?>>()
        result.onNext(Resource.Loading())

        remoteDataSource.updateMenu(
            id,
            nama,
            deskripsi,
            lemak,
            protein,
            kalori,
            karbohidrat,
            harga,
            gambar
        )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .take(1)
            .subscribe {
                when (it) {
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
    override fun buatPenyakit(
        id_menu: String,
        sehat: String,
        diabetes: String,
        jantung: String,
        kelelahan: String,
        obesitas: String,
        sembelit: String
    ): Flowable<Resource<String?>> {
        val result = PublishSubject.create<Resource<String?>>()
        result.onNext(Resource.Loading())

        remoteDataSource.buatPenyakit(id_menu, sehat, diabetes, jantung, kelelahan, obesitas, sembelit)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .take(1)
            .subscribe {
                when (it) {
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
    override fun updatePenyakit(
        id_penyakit: String,
        id_menu: String,
        sehat: String,
        diabetes: String,
        jantung: String,
        kelelahan: String,
        obesitas: String,
        sembelit: String
    ): Flowable<Resource<String?>> {
        val result = PublishSubject.create<Resource<String?>>()
        result.onNext(Resource.Loading())

        remoteDataSource.updatePenyakit(id_penyakit, id_menu, sehat, diabetes, jantung, kelelahan, obesitas, sembelit)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .take(1)
            .subscribe {
                when (it) {
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
    override fun uploadGambar(id_menu: String, uri: Uri): Flowable<Resource<Uri?>> {
        val result = PublishSubject.create<Resource<Uri?>>()
        result.onNext(Resource.Loading())

        remoteDataSource.uploadGambar(id_menu, uri)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .take(1)
            .subscribe {
                when (it) {
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