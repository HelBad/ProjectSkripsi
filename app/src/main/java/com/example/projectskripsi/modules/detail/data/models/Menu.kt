package com.example.projectskripsi.modules.detail.data.models

class Menu {
    lateinit var id_menu: String
    lateinit var nama_menu: String
    lateinit var deskripsi: String
    lateinit var lemak: String
    lateinit var protein: String
    lateinit var kalori: String
    lateinit var karbohidrat: String
    lateinit var harga: String
    lateinit var gambar: String

    constructor() {}
    constructor(id_menu: String, nama_menu: String, deskripsi: String, lemak: String, protein: String,
                kalori: String, karbohidrat: String, harga: String, gambar: String) {
        this.id_menu = id_menu
        this.nama_menu = nama_menu
        this.deskripsi = deskripsi
        this.lemak = lemak
        this.protein = protein
        this.kalori = kalori
        this.karbohidrat = karbohidrat
        this.harga = harga
        this.gambar = gambar
    }
}