package com.example.projectskripsi.features.edit.presentation.viewmodel

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.LiveDataReactiveStreams
import androidx.lifecycle.ViewModel
import com.example.projectskripsi.core.Resource
import com.example.projectskripsi.features.edit.domain.entities.Menu
import com.example.projectskripsi.features.edit.domain.entities.Penyakit
import com.example.projectskripsi.features.edit.domain.usecases.*

class EditViewModel(
    private val getDetailMenuUsecase: GetDetailMenuUsecase,
    private val buatMenuUsecase: BuatMenuUsecase,
    private val updateMenuUsecase: UpdateMenuUsecase,
    private val getDetailPenyakitUsecase: GetDetailPenyakitUsecase,
    private val buatPenyakitUsecase: BuatPenyakitUsecase,
    private val updatePenyakitUsecase: UpdatePenyakitUsecase,
    private val uploadGambarUsecase: UploadGambarUsecase,
) : ViewModel() {
    fun getDetailMenu(id: String): LiveData<Resource<Menu?>> {
        return LiveDataReactiveStreams.fromPublisher(
            getDetailMenuUsecase.run(GetDetailMenuUsecase.GetDetailMenuParams(id))
        )
    }

    fun getDetailPenyakit(id_menu: String): LiveData<Resource<Penyakit?>> {
        return LiveDataReactiveStreams.fromPublisher(
            getDetailPenyakitUsecase.run(GetDetailPenyakitUsecase.GetDetailPenyakitParams(id_menu))
        )
    }

    fun uploadGambar(id_menu: String, uri: Uri): LiveData<Resource<Uri?>> {
        return LiveDataReactiveStreams.fromPublisher(
            uploadGambarUsecase.run(UploadGambarUsecase.UploadGambarParams(id_menu, uri))
        )
    }

    fun buatMenu(
        nama: String,
        deskripsi: String,
        lemak: String,
        protein: String,
        kalori: String,
        karbohidrat: String,
        harga: String,
        gambar: String
    ): LiveData<Resource<String?>> {
        return LiveDataReactiveStreams.fromPublisher(
            buatMenuUsecase.run(
                BuatMenuUsecase.BuatMenuParams(
                    nama,
                    deskripsi,
                    lemak,
                    protein,
                    kalori,
                    karbohidrat,
                    harga,
                    gambar
                )
            )
        )
    }

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
    ): LiveData<Resource<String?>> {
        return LiveDataReactiveStreams.fromPublisher(
            updateMenuUsecase.run(
                UpdateMenuUsecase.UpdateMenuParams(
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
            )
        )
    }

    fun buatPenyakit(
        id_menu: String,
        sehat: String,
        diabetes: String,
        jantung: String,
        kelelahan: String,
        obesitas: String,
        sembelit: String
    ): LiveData<Resource<String?>> {
        return LiveDataReactiveStreams.fromPublisher(
            buatPenyakitUsecase.run(
                BuatPenyakitUsecase.BuatPenyakitParams(
                    id_menu, sehat, diabetes, jantung, kelelahan, obesitas, sembelit
                )
            )
        )
    }

    fun updatePenyakit(
        id: String,
        id_menu: String,
        sehat: String,
        diabetes: String,
        jantung: String,
        kelelahan: String,
        obesitas: String,
        sembelit: String
    ): LiveData<Resource<String?>> {
        return LiveDataReactiveStreams.fromPublisher(
            updatePenyakitUsecase.run(
                UpdatePenyakitUsecase.UpdatePenyakitParams(id, id_menu, sehat, diabetes, jantung,
                    kelelahan, obesitas, sembelit)
            )
        )
    }
}