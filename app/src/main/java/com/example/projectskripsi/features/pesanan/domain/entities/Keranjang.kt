package com.example.projectskripsi.features.pesanan.domain.entities

data class Keranjang (
    var id_keranjang: String? = null,
    var id_user: String? = null,
    var id_menu: String? = null,
    var jumlah: String? = null,
    var total: String? = null,
)