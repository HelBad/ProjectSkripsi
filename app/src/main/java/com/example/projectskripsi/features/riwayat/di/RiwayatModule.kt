package com.example.projectskripsi.features.riwayat.di

import com.example.projectskripsi.features.riwayat.data.repositories.RiwayatRepositoryImpl
import com.example.projectskripsi.features.riwayat.data.source.local.RiwayatLocalDataSource
import com.example.projectskripsi.features.riwayat.data.source.remote.RiwayatRemoteDataSource
import com.example.projectskripsi.features.riwayat.domain.repositories.RiwayatRepository
import com.example.projectskripsi.features.riwayat.domain.usecases.*
import com.example.projectskripsi.features.riwayat.presentation.viewmodel.RiwayatViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val riwayatModule = module {
    factory { RiwayatRemoteDataSource() }
    factory { RiwayatLocalDataSource() }

    single<RiwayatRepository> { RiwayatRepositoryImpl(get(), get()) }

    single { GetUserUsecase(get()) }
    single { GetUserRemoteUsecase(get()) }
    single { GetDetailPesananUsecase(get()) }
    single { GetKeranjangUsecase(get()) }
    single { GetDetailKeranjangUsecase(get()) }
    single { GetMenuUsecase(get()) }
    single { UpdatePesananUsecase(get()) }

    viewModel { RiwayatViewModel(get(), get(), get(), get(), get(), get(), get()) }
}