package com.example.projectskripsi.modules.detail.domain.entities

data class Keranjang (
    var idKeranjang: String? = null,
    var idUser: String? = null,
    var idMenu: String? = null,
    var jumlah: String? = null,
    var total: String? = null,
)