package com.example.projectskripsi.core

abstract class UseCase<out Type, in Params> where Type : Any {
    abstract fun run(params: Params): Type

    operator fun invoke(params: Params) {
        run(params)
    }

    class NoParams
}