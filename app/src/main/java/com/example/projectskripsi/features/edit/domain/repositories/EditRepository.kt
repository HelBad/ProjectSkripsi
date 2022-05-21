package com.example.projectskripsi.features.edit.domain.repositories

import android.net.Uri
import com.example.projectskripsi.core.Resource
import com.example.projectskripsi.features.edit.data.responses.PenyakitResponse
import com.example.projectskripsi.features.edit.domain.entities.Menu
import com.example.projectskripsi.features.edit.domain.entities.Penyakit
import io.reactivex.Flowable

interface EditRepository {
    fun getDetailMenu(id: String): Flowable<Resource<Menu?>>

    fun getDetailPenyakit(idMenu: String): Flowable<Resource<Penyakit?>>

    fun buatMenu(
        nama: String,
        deskripsi: String,
        lemak: String,
        protein: String,
        kalori: String,
        karbohidrat: String,
        harga: String,
        gambar: String
    ): Flowable<Resource<String?>>

    fun updateMenu(
        id: String,
        nama: String,
        deskripsi: String,
        lemak: String,
        protein: String,
        kalori: String,
        karbohidrat: String,
        harga: String,
        gambar: String
    ): Flowable<Resource<String?>>

    fun buatPenyakit(
        idMenu: String,
        sehat: String,
        diabetes: String,
        obesitas: String,
        anemia: String,
    ): Flowable<Resource<String?>>

    fun updatePenyakit(
        id: String,
        idMenu: String,
        sehat: String,
        diabetes: String,
        obesitas: String,
        anemia: String,
    ): Flowable<Resource<String?>>

    fun uploadGambar(
        idMenu: String,
        uri: Uri,
    ): Flowable<Resource<Uri?>>
}