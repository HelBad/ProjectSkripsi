package com.example.projectskripsi.features.pesanan.domain.entities

class Pesanan {
    lateinit var id_pesanan: String
    lateinit var id_user: String
    lateinit var id_keranjang: String
    lateinit var catatan: String
    lateinit var waktu: String
    lateinit var lokasi: String
    lateinit var subtotal: String
    lateinit var ongkir: String
    lateinit var total_bayar: String
    lateinit var status: String
    lateinit var keterangan: String

    constructor() {}
    constructor(id_pesanan: String, id_user: String, id_keranjang: String, catatan: String, waktu: String,
        lokasi: String, subtotal: String, ongkir: String, total_bayar: String, status: String, keterangan: String) {
        this.id_pesanan = id_pesanan
        this.id_user = id_user
        this.id_keranjang = id_keranjang
        this.catatan = catatan
        this.waktu = waktu
        this.lokasi = lokasi
        this.subtotal = subtotal
        this.ongkir = ongkir
        this.total_bayar = total_bayar
        this.status = status
        this.keterangan = keterangan
    }
}