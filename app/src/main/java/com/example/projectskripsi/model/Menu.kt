package com.example.projectskripsi.model

class Menu {
    lateinit var id_menu: String
    lateinit var nama_menu: String
    lateinit var deskripsi: String
    lateinit var protein_menu: String
    lateinit var lemak_menu: String
    lateinit var serat_menu: String
    lateinit var karbohidrat_menu: String
    lateinit var kalori_menu: String
    lateinit var kolesterol_menu: String
    lateinit var harga: String
    lateinit var gambar: String

    constructor() {}
    constructor(id_menu: String, nama_menu: String, deskripsi: String, protein_menu: String,
                lemak_menu: String, serat_menu: String, karbohidrat_menu: String,
                kalori_menu: String, kolesterol_menu: String, harga: String, gambar: String) {
        this.id_menu = id_menu
        this.nama_menu = nama_menu
        this.deskripsi = deskripsi
        this.protein_menu = protein_menu
        this.lemak_menu = lemak_menu
        this.serat_menu = serat_menu
        this.karbohidrat_menu = karbohidrat_menu
        this.kalori_menu = kalori_menu
        this.kolesterol_menu = kolesterol_menu
        this.harga = harga
        this.gambar = gambar
    }
}