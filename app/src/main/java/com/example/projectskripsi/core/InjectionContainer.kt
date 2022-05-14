package com.example.projectskripsi.core

import com.example.projectskripsi.modules.auth.data.repositories.AuthRepositoryImpl
import com.example.projectskripsi.modules.auth.data.source.local.AuthLocalDataSource
import com.example.projectskripsi.modules.auth.data.source.remote.AuthRemoteDataSource
import com.example.projectskripsi.modules.auth.domain.repositories.AuthRepository
import com.example.projectskripsi.modules.auth.domain.usecases.AuthUsecase
import com.example.projectskripsi.modules.auth.presentation.viewmodel.LoginViewModel
import com.example.projectskripsi.modules.auth.presentation.viewmodel.RegisterViewModel
import com.example.projectskripsi.modules.beranda.data.repositories.BerandaRepositoryImpl
import com.example.projectskripsi.modules.beranda.data.source.remote.BerandaRemoteDataSource
import com.example.projectskripsi.modules.beranda.domain.repositories.BerandaRepository
import com.example.projectskripsi.modules.beranda.domain.usecases.BerandaUsecase
import com.example.projectskripsi.modules.beranda.presentation.viewmodel.BerandaViewModel
import com.example.projectskripsi.modules.detail.data.repositories.DetailRepositoryImpl
import com.example.projectskripsi.modules.detail.data.source.local.DetailLocalDataSource
import com.example.projectskripsi.modules.detail.data.source.remote.DetailRemoteDataSource
import com.example.projectskripsi.modules.detail.domain.repositories.DetailRepository
import com.example.projectskripsi.modules.detail.domain.usecases.DetailUsecase
import com.example.projectskripsi.modules.detail.presentation.viewmodel.DetailViewModel
import com.example.projectskripsi.modules.profil.data.repositories.ProfilRepositoryImpl
import com.example.projectskripsi.modules.profil.data.source.local.ProfilLocalDataSource
import com.example.projectskripsi.modules.profil.data.source.remote.ProfilRemoteDataSource
import com.example.projectskripsi.modules.profil.domain.repositories.ProfilRepository
import com.example.projectskripsi.modules.profil.domain.usecases.ProfilUsecase
import com.example.projectskripsi.modules.profil.presentation.viewmodel.ProfilViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val authModule = module {
    factory { AuthRemoteDataSource() }
    factory { AuthLocalDataSource() }
    single<AuthRepository> { AuthRepositoryImpl(get(), get()) }
    single { AuthUsecase(get()) }
    viewModel { LoginViewModel(get()) }
    viewModel { RegisterViewModel(get()) }

    factory { BerandaRemoteDataSource() }
    single<BerandaRepository> { BerandaRepositoryImpl(get()) }
    single { BerandaUsecase(get()) }
    viewModel { BerandaViewModel(get()) }

    factory { DetailRemoteDataSource() }
    factory { DetailLocalDataSource() }
    single<DetailRepository> { DetailRepositoryImpl(get(), get()) }
    single { DetailUsecase(get()) }
    viewModel { DetailViewModel(get()) }

    factory { ProfilRemoteDataSource() }
    factory { ProfilLocalDataSource() }
    single<ProfilRepository> { ProfilRepositoryImpl(get(), get()) }
    single { ProfilUsecase(get()) }
    viewModel { ProfilViewModel(get()) }
}