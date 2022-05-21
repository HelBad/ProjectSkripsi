package com.example.projectskripsi.features.edit.domain.usecases

import com.example.projectskripsi.core.Resource
import com.example.projectskripsi.core.UseCase
import com.example.projectskripsi.features.edit.domain.entities.Penyakit
import com.example.projectskripsi.features.edit.domain.repositories.EditRepository
import io.reactivex.Flowable

class GetDetailPenyakitUsecase(private val repository: EditRepository) :
    UseCase<Flowable<Resource<Penyakit?>>, GetDetailPenyakitUsecase.GetDetailPenyakitParams>() {
    override fun run(params: GetDetailPenyakitParams) = repository.getDetailPenyakit(params.id)

    data class GetDetailPenyakitParams(
        val id: String,
    )
}

