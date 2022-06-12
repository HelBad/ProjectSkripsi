package com.example.projectskripsi.features.edit.domain.usecases

import android.net.Uri
import com.example.projectskripsi.core.Resource
import com.example.projectskripsi.core.Usecase
import com.example.projectskripsi.features.edit.domain.repositories.EditRepository
import io.reactivex.Flowable

class UploadGambarUsecase(private val repository: EditRepository) :
    Usecase<Flowable<Resource<Uri?>>, UploadGambarUsecase.UploadGambarParams>() {
    override fun run(params: UploadGambarParams) =
        repository.uploadGambar(params.id_menu, params.uri)

    data class UploadGambarParams(
        val id_menu: String,
        val uri: Uri,
    )
}