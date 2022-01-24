package com.example.projectskripsi.model

class Keranjang {
    var id_keranjang: String = ""
    lateinit var id_user: String
    lateinit var id_menu: String
    lateinit var jumlah: String

    constructor() {}
    constructor(id_keranjang: String, id_user: String, id_menu: String, jumlah: String) {
        this.id_keranjang = id_keranjang
        this.id_user = id_user
        this.id_menu = id_menu
        this.jumlah = jumlah
    }
}