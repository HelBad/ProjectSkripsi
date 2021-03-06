package com.example.projectskripsi.model

class Penyakit {
    lateinit var id_penyakit: String
    lateinit var id_menu: String
    lateinit var sehat: String
    lateinit var diabetes: String
    lateinit var jantung: String
    lateinit var kelelahan: String
    lateinit var obesitas: String
    lateinit var sembelit: String

    constructor() {}
    constructor(id_penyakit: String, id_menu: String, sehat: String, diabetes: String,
                jantung: String, kelelahan: String, obesitas: String, sembelit: String) {
        this.id_penyakit = id_penyakit
        this.id_menu = id_menu
        this.sehat = sehat
        this.diabetes = diabetes
        this.jantung = jantung
        this.kelelahan = kelelahan
        this.obesitas = obesitas
        this.sembelit = sembelit
    }
}