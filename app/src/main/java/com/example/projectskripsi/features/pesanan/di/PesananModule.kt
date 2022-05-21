package com.example.projectskripsi.features.pesanan.di

import com.example.projectskripsi.features.pesanan.data.repositories.PesananRepositoryImpl
import com.example.projectskripsi.features.pesanan.data.source.local.PesananLocalDataSource
import com.example.projectskripsi.features.pesanan.data.source.remote.PesananRemoteDataSource
import com.example.projectskripsi.features.pesanan.domain.repositories.PesananRepository
import com.example.projectskripsi.features.pesanan.domain.usecases.*
import com.example.projectskripsi.features.pesanan.presentation.viewmodel.PesananViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val pesananModule = module {
    factory { PesananRemoteDataSource() }
    factory { PesananLocalDataSource() }

    single<PesananRepository> { PesananRepositoryImpl(get(), get()) }

    single { GetUserUsecase(get()) }
    single { GetPesananUsecase(get()) }

    viewModel { PesananViewModel(get(), get()) }
}