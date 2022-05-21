package com.example.projectskripsi.features.riwayat.domain.entities

data class Pesanan (
    var id_pesanan: String? = null,
    var id_user: String? = null,
    var id_keranjang: String? = null,
    var catatan: String? = null,
    var waktu: String? = null,
    var lokasi: String? = null,
    var subtotal: String? = null,
    var ongkir: String? = null,
    var total_bayar: String? = null,
    var status: String? = null,
    var keterangan: String? = null,
)