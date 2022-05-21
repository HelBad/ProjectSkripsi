package com.example.projectskripsi.features.edit.domain.usecases

import android.net.Uri
import com.example.projectskripsi.core.Resource
import com.example.projectskripsi.core.UseCase
import com.example.projectskripsi.features.edit.domain.repositories.EditRepository
import io.reactivex.Flowable

class UploadGambarUsecase(private val repository: EditRepository) :
    UseCase<Flowable<Resource<Uri?>>, UploadGambarUsecase.UploadGambarParams>() {
    override fun run(params: UploadGambarParams) =
        repository.uploadGambar(params.idMenu, params.uri)

    data class UploadGambarParams(
        val idMenu: String,
        val uri: Uri,
    )
}