package com.example.projectskripsi.features.edit.domain.usecases

import com.example.projectskripsi.core.Resource
import com.example.projectskripsi.core.Usecase
import com.example.projectskripsi.features.edit.domain.repositories.EditRepository
import io.reactivex.Flowable

class BuatPenyakitUsecase(private val repository: EditRepository) :
    Usecase<Flowable<Resource<String?>>, BuatPenyakitUsecase.BuatPenyakitParams>() {
    override fun run(params: BuatPenyakitParams) = repository.buatPenyakit(
        params.id_menu, params.sehat, params.diabetes, params.jantung, params.kelelahan,
        params.obesitas, params.sembelit
    )

    data class BuatPenyakitParams(
        val id_menu: String,
        val sehat: String,
        val diabetes: String,
        val jantung: String,
        val kelelahan: String,
        val obesitas: String,
        val sembelit: String
    )
}