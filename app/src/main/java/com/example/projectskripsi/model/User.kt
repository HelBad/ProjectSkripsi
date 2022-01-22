package com.example.projectskripsi.model

class User {
    lateinit var id_user: String
    lateinit var nama: String
    lateinit var email: String
    lateinit var password: String
    lateinit var tgl_lahir: String
    lateinit var gender: String
    lateinit var alamat: String
    lateinit var telp: String
    lateinit var level: String

    constructor() {}
    constructor(id_user:String, nama:String, email:String, password:String, tgl_lahir:String, gender:String,
                alamat:String, telp: String, level: String) {
        this.id_user = id_user
        this.nama = nama
        this.email = email
        this.password = password
        this.tgl_lahir = tgl_lahir
        this.gender = gender
        this.alamat = alamat
        this.telp = telp
        this.level = level
    }
}