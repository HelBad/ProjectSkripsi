package com.example.projectskripsi.features.edit.data.repositories

import android.annotation.SuppressLint
import android.net.Uri
import com.example.projectskripsi.core.Resource
import com.example.projectskripsi.core.Response
import com.example.projectskripsi.features.edit.data.responses.PenyakitResponse
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
    override fun getDetailMenu(id: String): Flowable<Resource<Menu?>> {
        val result = PublishSubject.create<Resource<Menu?>>()
        result.onNext(Resource.Loading())

        remoteDataSource.getDetailMenu(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .take(1)
            .subscribe {
                when (it) {
                    is Response.Success -> {
                        val res = it.data
                        if (res != null) {
                            val menu = Menu(
                                idMenu = res.id_menu,
                                namaMenu = res.nama_menu,
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
    override fun getDetailPenyakit(idMenu: String): Flowable<Resource<Penyakit?>> {
        val result = PublishSubject.create<Resource<Penyakit?>>()
        result.onNext(Resource.Loading())

        remoteDataSource.getDetailPenyakit(idMenu)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .take(1)
            .subscribe {
                when (it) {
                    is Response.Success -> {
                        val res = it.data
                        if (res != null) {
                            val penyakit = Penyakit(
                                idPenyakit = res.id_penyakit,
                                idMenu = res.id_menu,
                                sehat = res.sehat,
                                diabetes = res.diabetes,
                                obesitas = res.obesitas,
                                anemia = res.anemia,
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
        idMenu: String,
        sehat: String,
        diabetes: String,
        obesitas: String,
        anemia: String
    ): Flowable<Resource<String?>> {
        val result = PublishSubject.create<Resource<String?>>()
        result.onNext(Resource.Loading())

        remoteDataSource.buatPenyakit(idMenu, sehat, diabetes, obesitas, anemia)
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
        id: String,
        idMenu: String,
        sehat: String,
        diabetes: String,
        obesitas: String,
        anemia: String
    ): Flowable<Resource<String?>> {
        val result = PublishSubject.create<Resource<String?>>()
        result.onNext(Resource.Loading())

        remoteDataSource.updatePenyakit(id, idMenu, sehat, diabetes, obesitas, anemia)
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
    override fun uploadGambar(idMenu: String, uri: Uri): Flowable<Resource<Uri?>> {
        val result = PublishSubject.create<Resource<Uri?>>()
        result.onNext(Resource.Loading())

        remoteDataSource.uploadGambar(idMenu, uri)
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