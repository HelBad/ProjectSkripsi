package com.example.projectskripsi.model

class Penyakit {
    lateinit var id_penyakit: String
    lateinit var id_menu: String
    lateinit var diabetes: String
    lateinit var obesitas: String
    lateinit var asamlambung: String

    constructor() {}
    constructor(id_penyakit: String, id_menu: String, diabetes: String, obesitas: String, asamlambung: String) {
        this.id_penyakit = id_penyakit
        this.id_menu = id_menu
        this.diabetes = diabetes
        this.obesitas = obesitas
        this.asamlambung = asamlambung
    }
}