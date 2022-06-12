package com.example.projectskripsi.features.edit.domain.repositories

import android.net.Uri
import com.example.projectskripsi.core.Resource
import com.example.projectskripsi.features.edit.domain.entities.Menu
import com.example.projectskripsi.features.edit.domain.entities.Penyakit
import io.reactivex.Flowable

interface EditRepository {
    fun getDetailMenu(id: String): Flowable<Resource<Menu?>>

    fun getDetailPenyakit(id_menu: String): Flowable<Resource<Penyakit?>>

    fun buatMenu(
        nama_menu: String,
        deskripsi: String,
        lemak: String,
        protein: String,
        kalori: String,
        karbohidrat: String,
        harga: String,
        gambar: String
    ): Flowable<Resource<String?>>

    fun updateMenu(
        id_menu: String,
        nama_menu: String,
        deskripsi: String,
        lemak: String,
        protein: String,
        kalori: String,
        karbohidrat: String,
        harga: String,
        gambar: String
    ): Flowable<Resource<String?>>

    fun buatPenyakit(
        id_menu: String,
        sehat: String,
        diabetes: String,
        jantung: String,
        kelelahan: String,
        obesitas: String,
        sembelit: String
    ): Flowable<Resource<String?>>

    fun updatePenyakit(
        id_penyakit: String,
        id_menu: String,
        sehat: String,
        diabetes: String,
        jantung: String,
        kelelahan: String,
        obesitas: String,
        sembelit: String
    ): Flowable<Resource<String?>>

    fun uploadGambar(
        id_menu: String,
        uri: Uri
    ): Flowable<Resource<Uri?>>
}