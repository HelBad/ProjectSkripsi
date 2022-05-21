package com.example.projectskripsi.features.edit.domain.usecases

import com.example.projectskripsi.core.Resource
import com.example.projectskripsi.core.UseCase
import com.example.projectskripsi.features.edit.domain.repositories.EditRepository
import io.reactivex.Flowable

class BuatPenyakitUsecase(private val repository: EditRepository) :
    UseCase<Flowable<Resource<String?>>, BuatPenyakitUsecase.BuatPenyakitParams>() {
    override fun run(params: BuatPenyakitParams) = repository.buatPenyakit(
        params.idMenu, params.sehat, params.diabetes, params.obesitas, params.anemia
    )

    data class BuatPenyakitParams(
        val idMenu: String,
        val sehat: String,
        val diabetes: String,
        val obesitas: String,
        val anemia: String,
    )
}