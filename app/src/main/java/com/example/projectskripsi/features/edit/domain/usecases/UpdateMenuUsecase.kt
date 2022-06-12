package com.example.projectskripsi.features.edit.domain.usecases

import com.example.projectskripsi.core.Resource
import com.example.projectskripsi.core.Usecase
import com.example.projectskripsi.features.edit.domain.repositories.EditRepository
import io.reactivex.Flowable

class UpdateMenuUsecase(private val repository: EditRepository) :
    Usecase<Flowable<Resource<String?>>, UpdateMenuUsecase.UpdateMenuParams>() {
    override fun run(params: UpdateMenuParams) = repository.updateMenu(
        params.id_menu, params.nama_menu, params.deskripsi, params.lemak, params.protein,
        params.kalori, params.karbohidrat, params.harga, params.gambar,
    )

    data class UpdateMenuParams(
        val id_menu: String,
        val nama_menu: String,
        val deskripsi: String,
        val lemak: String,
        val protein: String,
        val kalori: String,
        val karbohidrat: String,
        val harga: String,
        val gambar: String,
    )
}