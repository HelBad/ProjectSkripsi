package com.example.projectskripsi.features.auth.di

import com.example.projectskripsi.features.auth.data.repositories.AuthRepositoryImpl
import com.example.projectskripsi.features.auth.data.source.local.AuthLocalDataSource
import com.example.projectskripsi.features.auth.data.source.remote.AuthRemoteDataSource
import com.example.projectskripsi.features.auth.domain.repositories.AuthRepository
import com.example.projectskripsi.features.auth.domain.usecases.GetUserUsecase
import com.example.projectskripsi.features.auth.domain.usecases.LoginUsecase
import com.example.projectskripsi.features.auth.domain.usecases.RegisterUsecase
import com.example.projectskripsi.features.auth.domain.usecases.SaveUserUsecase
import com.example.projectskripsi.features.auth.presentation.viewmodel.LoginViewModel
import com.example.projectskripsi.features.auth.presentation.viewmodel.RegisterViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val authModule = module {
    factory { AuthRemoteDataSource() }
    factory { AuthLocalDataSource() }

    single<AuthRepository> { AuthRepositoryImpl(get(), get()) }

    single { LoginUsecase(get()) }
    single { RegisterUsecase(get()) }
    single { GetUserUsecase(get()) }
    single { SaveUserUsecase(get()) }

    viewModel { LoginViewModel(get(), get()) }
    viewModel { RegisterViewModel(get(), get()) }
}