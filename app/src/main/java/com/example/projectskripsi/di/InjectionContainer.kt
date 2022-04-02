package com.example.projectskripsi.di

import com.example.projectskripsi.core.data.repositories.AuthRepositoryImpl
import com.example.projectskripsi.core.data.source.remote.AuthRemoteDataSource
import com.example.projectskripsi.core.domain.repositories.AuthRepository
import com.example.projectskripsi.core.domain.usecases.AuthUsecase
import com.example.projectskripsi.core.ui.auth.viewmodel.AuthViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val authModule = module {
    factory { AuthRemoteDataSource() }
    single<AuthRepository> { AuthRepositoryImpl(get()) }
    single { AuthUsecase(get()) }
    viewModel { AuthViewModel(get()) }
}