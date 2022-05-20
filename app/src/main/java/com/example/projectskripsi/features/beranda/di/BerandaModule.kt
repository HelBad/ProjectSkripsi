package com.example.projectskripsi.features.beranda.di

import com.example.projectskripsi.features.beranda.data.repositories.BerandaRepositoryImpl
import com.example.projectskripsi.features.beranda.data.source.remote.BerandaRemoteDataSource
import com.example.projectskripsi.features.beranda.domain.repositories.BerandaRepository
import com.example.projectskripsi.features.beranda.domain.usecases.GetMenuUsecase
import com.example.projectskripsi.features.beranda.domain.usecases.GetPenyakitUsecase
import com.example.projectskripsi.features.beranda.presentation.viewmodel.BerandaViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val berandaModule = module {
    factory { BerandaRemoteDataSource() }

    single<BerandaRepository> { BerandaRepositoryImpl(get()) }

    single { GetMenuUsecase(get()) }
    single { GetPenyakitUsecase(get()) }

    viewModel { BerandaViewModel(get(), get()) }
}