package com.example.projectskripsi.features.edit.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.LiveDataReactiveStreams
import androidx.lifecycle.ViewModel
import com.example.projectskripsi.core.Resource
import com.example.projectskripsi.core.UseCase
import com.example.projectskripsi.features.edit.domain.usecases.*
import com.example.projectskripsi.features.menu.domain.entities.Keranjang
import com.example.projectskripsi.features.menu.domain.entities.Menu
import com.example.projectskripsi.features.menu.domain.entities.User
import com.example.projectskripsi.features.menu.domain.usecases.*
import com.example.projectskripsi.features.menu.domain.usecases.GetDetailMenuUsecase

class EditViewModel (
    private val getDetailMenuUsecase: GetDetailMenuUsecase,
    private val buatMenuUsecase: BuatMenuUsecase,
    private val updateMenuUsecase: UpdateMenuUsecase,
    private val getDetailPenyakitUsecase: GetDetailPenyakitUsecase,
    private val buatPenyakitUsecase: BuatPenyakitUsecase,
    private val updatePenyakitUsecase: UpdatePenyakitUsecase,
    private val uploadGambarUsecase: UploadGambarUsecase,
) : ViewModel() {
//    fun getDetailMenu(id: String): LiveData<Resource<Menu?>> {
//        return LiveDataReactiveStreams.fromPublisher(
//            getDetailMenuUsecase.run(GetDetailMenuUsecase.GetDetailMenuParams(id))
//        )
//    }
//
//    fun getDetailKeranjang(idMenu: String, idUser: String): LiveData<Resource<Keranjang?>> {
//        return LiveDataReactiveStreams.fromPublisher(
//            getDetailKeranjangUsecase.run(
//                GetDetailKeranjangUsecase.GetDetailKeranjangParams(
//                    idMenu, idUser
//                )
//            )
//        )
//    }
//
//    fun hapusPesanan(idKeranjang: String, idUser: String): LiveData<Resource<String?>> {
//        return LiveDataReactiveStreams.fromPublisher(
//            hapusKeranjangUsecase.run(HapusKeranjangUsecase.HapusKeranjangParams(idKeranjang, idUser))
//        )
//    }
//
//    fun buatPesanan(
//        idUser: String,
//        idMenu: String,
//        jumlah: String,
//        total: String
//    ): LiveData<Resource<String?>> {
//        return LiveDataReactiveStreams.fromPublisher(
//            buatKeranjangUsecase.run(
//                BuatKeranjangUsecase.BuatKeranjangParams(
//                    idUser,
//                    idMenu,
//                    jumlah,
//                    total
//                )
//            )
//        )
//    }
//
//    fun updatePesanan(
//        idKeranjang: String,
//        jumlah: String,
//        total: String,
//        idUser: String
//    ): LiveData<Resource<String?>> {
//        return LiveDataReactiveStreams.fromPublisher(
//            updateKeranjangUsecase.run(
//                UpdateKeranjangUsecase.UpdateKeranjangParams(
//                    idKeranjang,
//                    jumlah,
//                    total,
//                    idUser
//                )
//            )
//        )
//    }
//
//    fun getUser(): LiveData<Resource<User?>> {
//        return LiveDataReactiveStreams.fromPublisher(
//            getUserUsecase.run(UseCase.NoParams())
//        )
//    }
}