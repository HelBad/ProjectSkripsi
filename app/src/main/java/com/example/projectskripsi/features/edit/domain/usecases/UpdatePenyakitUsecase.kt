package com.example.projectskripsi.features.edit.domain.usecases

import com.example.projectskripsi.core.Resource
import com.example.projectskripsi.core.UseCase
import com.example.projectskripsi.features.edit.domain.repositories.EditRepository
import io.reactivex.Flowable

class UpdatePenyakitUsecase(private val repository: EditRepository) :
    UseCase<Flowable<Resource<String?>>, UpdatePenyakitUsecase.UpdatePenyakitParams>() {
    override fun run(params: UpdatePenyakitParams) = repository.updatePenyakit(
        params.idPenyakit,
        params.idMenu,
        params.sehat,
        params.diabetes,
        params.obesitas,
        params.anemia,
    )

    data class UpdatePenyakitParams(
        val idPenyakit: String,
        val idMenu: String,
        val sehat: String,
        val diabetes: String,
        val obesitas: String,
        val anemia: String,
    )
}