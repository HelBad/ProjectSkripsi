package com.example.projectskripsi.model

class Pesanan {
    lateinit var id_pesanan: String
    lateinit var keranjang: ArrayList<Keranjang>
    lateinit var catatan: String
    lateinit var waktu: String
    lateinit var lokasi: String
    lateinit var subtotal: String
    lateinit var ongkir: String
    lateinit var total_bayar: String
    lateinit var status: String

    constructor() {}
    constructor(
        id_pesanan: String, keranjang: ArrayList<Keranjang>, catatan: String, waktu: String,
        lokasi: String, subtotal: String, ongkir: String, total_bayar: String, status: String) {
        this.id_pesanan = id_pesanan
        this.keranjang = keranjang
        this.catatan = catatan
        this.waktu = waktu
        this.lokasi = lokasi
        this.subtotal = subtotal
        this.ongkir = ongkir
        this.total_bayar = total_bayar
        this.status = status
    }
}