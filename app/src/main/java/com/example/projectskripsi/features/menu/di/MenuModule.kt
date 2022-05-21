package com.example.projectskripsi.features.menu.di

import com.example.projectskripsi.features.menu.data.repositories.MenuRepositoryImpl
import com.example.projectskripsi.features.menu.data.source.local.MenuLocalDataSource
import com.example.projectskripsi.features.menu.data.source.remote.MenuRemoteDataSource
import com.example.projectskripsi.features.menu.domain.repositories.MenuRepository
import com.example.projectskripsi.features.menu.domain.usecases.*
import com.example.projectskripsi.features.menu.presentation.viewmodel.MenuViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val menuModule = module {
    factory { MenuRemoteDataSource() }
    factory { MenuLocalDataSource() }

    single<MenuRepository> { MenuRepositoryImpl(get(), get()) }

    single { BuatKeranjangUsecase(get()) }
    single { GetDetailKeranjangUsecase(get()) }
    single { GetDetailMenuUsecase(get()) }
    single { GetUserUsecase(get()) }
    single { HapusKeranjangUsecase(get()) }
    single { UpdateKeranjangUsecase(get()) }

    viewModel { MenuViewModel(get(), get(), get(), get(), get(), get()) }
}