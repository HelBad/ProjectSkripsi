package com.example.projectskripsi.model

class Menu {
    lateinit var id_menu: String
    lateinit var nama_menu: String
    lateinit var deskripsi: String
    lateinit var mineral: String
    lateinit var protein: String
    lateinit var lemak: String
    lateinit var karbohidrat: String
    lateinit var harga: String
    lateinit var gambar: String

    constructor() {}
    constructor(id_menu: String, nama_menu: String, deskripsi: String, mineral: String, protein: String,
                lemak: String, karbohidrat: String, harga: String, gambar: String) {
        this.id_menu = id_menu
        this.nama_menu = nama_menu
        this.deskripsi = deskripsi
        this.mineral = mineral
        this.protein = protein
        this.lemak = lemak
        this.karbohidrat = karbohidrat
        this.harga = harga
        this.gambar = gambar
    }
}