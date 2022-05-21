package com.example.projectskripsi.features.checkout.domain.repositories

import com.example.projectskripsi.core.Resource
import com.example.projectskripsi.features.checkout.domain.entities.User
import io.reactivex.Flowable

interface CheckoutRepository {
    fun getUser(): Flowable<Resource<User?>>
}