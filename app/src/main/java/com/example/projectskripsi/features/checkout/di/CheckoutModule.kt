package com.example.projectskripsi.features.checkout.di

import com.example.projectskripsi.features.checkout.data.repositories.CheckoutRepositoryImpl
import com.example.projectskripsi.features.checkout.data.source.local.CheckoutLocalDataSource
import com.example.projectskripsi.features.checkout.data.source.remote.CheckoutRemoteDataSource
import com.example.projectskripsi.features.checkout.domain.repositories.CheckoutRepository
import com.example.projectskripsi.features.checkout.domain.usecases.BuatPesananUsecase
import com.example.projectskripsi.features.checkout.domain.usecases.GetDetailKeranjangUsecase
import com.example.projectskripsi.features.checkout.domain.usecases.GetKeranjangUsecase
import com.example.projectskripsi.features.checkout.domain.usecases.GetUserUsecase
import com.example.projectskripsi.features.checkout.presentation.viewmodel.CheckoutViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val checkoutModule = module {
    factory { CheckoutRemoteDataSource() }
    factory { CheckoutLocalDataSource() }

    single<CheckoutRepository> { CheckoutRepositoryImpl(get(), get()) }

    single { GetUserUsecase(get()) }
    single { GetDetailKeranjangUsecase(get()) }
    single { GetKeranjangUsecase(get()) }
    single { BuatPesananUsecase(get()) }

    viewModel { CheckoutViewModel(get(), get(), get(), get()) }
}