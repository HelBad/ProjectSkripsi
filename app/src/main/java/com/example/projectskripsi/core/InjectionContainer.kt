package com.example.projectskripsi.core

import com.example.projectskripsi.modules.auth.data.repositories.AuthRepositoryImpl
import com.example.projectskripsi.modules.auth.data.source.remote.AuthRemoteDataSource
import com.example.projectskripsi.modules.auth.domain.repositories.AuthRepository
import com.example.projectskripsi.modules.auth.domain.usecases.AuthUsecase
import com.example.projectskripsi.modules.auth.ui.viewmodel.AuthViewModel
import com.example.projectskripsi.modules.beranda.data.repositories.BerandaRepositoryImpl
import com.example.projectskripsi.modules.beranda.data.source.remote.BerandaRemoteDataSource
import com.example.projectskripsi.modules.beranda.domain.repositories.BerandaRepository
import com.example.projectskripsi.modules.beranda.domain.usecases.BerandaUsecase
import com.example.projectskripsi.modules.beranda.ui.viewmodel.BerandaViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val authModule = module {
    factory { AuthRemoteDataSource() }
    single<AuthRepository> { AuthRepositoryImpl(get()) }
    single { AuthUsecase(get()) }
    viewModel { AuthViewModel(get()) }

    factory { BerandaRemoteDataSource() }
    single<BerandaRepository> { BerandaRepositoryImpl(get()) }
    single { BerandaUsecase(get()) }
    viewModel { BerandaViewModel(get()) }
}