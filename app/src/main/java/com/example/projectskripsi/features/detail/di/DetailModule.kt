package com.example.projectskripsi.features.detail.di

import com.example.projectskripsi.features.detail.data.repositories.DetailRepositoryImpl
import com.example.projectskripsi.features.detail.data.source.local.DetailLocalDataSource
import com.example.projectskripsi.features.detail.data.source.remote.DetailRemoteDataSource
import com.example.projectskripsi.features.detail.domain.repositories.DetailRepository
import com.example.projectskripsi.features.detail.domain.usecases.*
import com.example.projectskripsi.features.detail.presentation.viewmodel.DetailViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val detailModule = module {
    factory { DetailRemoteDataSource() }
    factory { DetailLocalDataSource() }

    single<DetailRepository> { DetailRepositoryImpl(get(), get()) }

    single { BuatPesananUsecase(get()) }
    single { GetDetailKeranjangUsecase(get()) }
    single { GetDetailMenuUsecase(get()) }
    single { GetUserUsecase(get()) }
    single { HapusPesananUsecase(get()) }
    single { UpdatePesananUsecase(get()) }

    viewModel { DetailViewModel(get(), get(), get(), get(), get(), get()) }
}