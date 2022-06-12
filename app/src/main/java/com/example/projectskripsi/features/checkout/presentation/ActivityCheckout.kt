package com.example.projectskripsi.features.checkout.presentation

import android.Manifest.permission
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.projectskripsi.R
import com.example.projectskripsi.core.Resource
import com.example.projectskripsi.features.beranda.presentation.ActivityUtamaUser
import com.example.projectskripsi.features.checkout.domain.entities.Keranjang
import com.example.projectskripsi.features.checkout.domain.entities.User
import com.example.projectskripsi.features.checkout.presentation.adapter.CheckoutAdapter
import com.example.projectskripsi.features.checkout.presentation.viewmodel.CheckoutViewModel
import com.example.projectskripsi.utils.Rupiah
import com.example.projectskripsi.utils.Tanggal
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import org.koin.android.viewmodel.ext.android.viewModel
import java.text.DecimalFormat
import java.util.*

class ActivityCheckout : AppCompatActivity() {
    private val checkoutViewModel: CheckoutViewModel by viewModel()
    lateinit var alertDialog: AlertDialog.Builder
    lateinit var mLayoutManager: LinearLayoutManager
    lateinit var mRecyclerView: RecyclerView
    lateinit var identitasCo: TextView
    lateinit var lokasiCo: TextView
    lateinit var jarakCo: TextView
    lateinit var keteranganCo: EditText
    lateinit var subtotalCo: TextView
    lateinit var ongkirCo: TextView
    lateinit var totalCo: TextView
    lateinit var btnPesanCo: Button
    lateinit var idLayoutCo: CardView
    lateinit var btnLayoutCo: CardView
    lateinit var kosongCo: TextView
    lateinit var loadingCo: ProgressBar

    var idKeranjang = ""
    var total = 0
    var ongkir = 0
    var user: User? = null

    @SuppressLint("MissingPermission")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_checkout)

        identitasCo = findViewById(R.id.identitasCo)
        lokasiCo = findViewById(R.id.lokasiCo)
        jarakCo = findViewById(R.id.jarakCo)
        keteranganCo = findViewById(R.id.keteranganCo)
        subtotalCo = findViewById(R.id.subtotalCo)
        ongkirCo = findViewById(R.id.ongkirCo)
        totalCo = findViewById(R.id.totalCo)
        btnPesanCo = findViewById(R.id.btnPesanCo)
        idLayoutCo = findViewById(R.id.idLayoutCo)
        btnLayoutCo = findViewById(R.id.btnLayoutCo)
        kosongCo = findViewById(R.id.kosongCo)
        loadingCo = findViewById(R.id.loadingCo)

        alertDialog = AlertDialog.Builder(this)
        loadData()

        btnPesanCo.setOnClickListener {
            alertDialog.setMessage("Pesanan akan langsung diproses dan tidak dapat dibatalkan. Cek kembali pesanan anda, Apakah sudah sesuai ?").setCancelable(false)
                .setPositiveButton("YA"
                ) { _, _ ->
                    if (validate()) {
                        buatPesanan()
                        val intent = Intent(this@ActivityCheckout, ActivityUtamaUser::class.java)
                        intent.putExtra("pesanan", "true")
                        startActivity(intent)
                        finish()
                    }
                }
                .setNegativeButton("TIDAK") { dialog, _ -> dialog.cancel() }.create().show()
        }
    }

    private fun loadData() {
        checkoutViewModel.getUser().observe(this@ActivityCheckout) {
            if (it is Resource.Success) {
                user = it.data
                identitasCo.text = "${user?.nama} (${user?.telp})"

                mLayoutManager = LinearLayoutManager(this)
                mRecyclerView = findViewById(R.id.recyclerCo)
                mRecyclerView.setHasFixedSize(true)
                mRecyclerView.layoutManager = mLayoutManager

                loadKeranjangDetail()
                lokasiSekarang()
                listKeranjang()
            }
        }
    }

    private fun loadKeranjangDetail() {
        user?.id_user?.let { id_user ->
            checkoutViewModel.getDetailKeranjang(id_user).observe(this@ActivityCheckout) {
                if (it is Resource.Success && it.data != null) {
                    val keranjang = it.data
                    idKeranjang = keranjang.id_keranjang.toString()
                    total += keranjang.total?.toInt()!!
                    loadingCo.visibility = View.GONE
                    idLayoutCo.visibility = View.VISIBLE
                    mRecyclerView.visibility = View.VISIBLE
                    btnLayoutCo.visibility = View.VISIBLE
                    kosongCo.visibility = View.GONE
                    subtotalCo.text = Rupiah.format(total)
                } else if (it is Resource.Loading) {
                    loadingCo.visibility = View.VISIBLE
                    idLayoutCo.visibility = View.GONE
                    mRecyclerView.visibility = View.GONE
                    btnLayoutCo.visibility = View.GONE
                    kosongCo.visibility = View.VISIBLE
                } else {
                    idLayoutCo.visibility = View.GONE
                    mRecyclerView.visibility = View.GONE
                    btnLayoutCo.visibility = View.GONE
                    kosongCo.visibility = View.VISIBLE
                    loadingCo.visibility = View.GONE
                }
            }
        }
    }

    //Set Lokasi Sekarang
    @SuppressLint("MissingPermission")
    private fun lokasiSekarang() {
        val mLocationRequest: LocationRequest = LocationRequest.create()
        mLocationRequest.interval = 60000
        mLocationRequest.fastestInterval = 5000
        mLocationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        val mLocationCallback: LocationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                for (location in locationResult.locations) {
                    if (location != null) {

                    }
                }
            }
        }
        LocationServices.getFusedLocationProviderClient(this)
            .requestLocationUpdates(mLocationRequest, mLocationCallback, null)
        checkPermissions()
    }

    private fun checkPermissions() {
        if(ContextCompat.checkSelfPermission(this,
                permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(permission.ACCESS_FINE_LOCATION), 1)
        } else {
            getLocations()
        }
    }

    @SuppressLint("MissingPermission")
    private fun getLocations() {
        LocationServices.getFusedLocationProviderClient(this).lastLocation.addOnSuccessListener {
            if(it == null) {
                Toast.makeText(this, "Lokasi gagal ditampilkan", Toast.LENGTH_SHORT).show()
            } else it.apply {
                val geocoder = Geocoder(this@ActivityCheckout, Locale.getDefault())
                val addresses: List<Address> = geocoder.getFromLocation(latitude, longitude, 1)
                val address: String = addresses[0].getAddressLine(0)
                lokasiCo.text = address

                val df = DecimalFormat("#.##")
                val latitude1 = addresses[0].latitude
                val longitude1 = addresses[0].longitude

                val loc1 = Location("")
                loc1.latitude = latitude1
                loc1.longitude = longitude1
                val loc2 = Location("")
                loc2.latitude = -7.834607
                loc2.longitude = 112.031276
                val distanceInMeters: Float = loc1.distanceTo(loc2)
                jarakCo.text = df.format(distanceInMeters / 1000).toString() + " km"

                ongkir = when (distanceInMeters) {
                    in 0.00..10.00 -> {
                        5000
                    }
                    in 10.01..20.00 -> {
                        10000
                    }
                    else -> {
                        20000
                    }
                }
                ongkirCo.text = Rupiah.format(ongkir)
                totalCo.text = Rupiah.format(total + ongkir)
            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode == 1) {
            if(grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if(ContextCompat.checkSelfPermission(this,
                        permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    getLocations()
                } else {
                    Toast.makeText(this, "Permission Gagal", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    //List Keranjang
    private fun listKeranjang() {
        val listKeranjang = arrayListOf<Keranjang>()

        user?.id_user?.let { id_user ->
            checkoutViewModel.getKeranjang(id_user).observe(this@ActivityCheckout) {
                if (it is Resource.Success && it.data != null) {
                    it.data.forEach { keranjang ->
                        keranjang.id_menu?.let { idMenu ->
                            checkoutViewModel.getDetailMenu(idMenu).observe(this@ActivityCheckout) { it1 ->
                                if (it1 is Resource.Success && it1.data != null) {
                                    keranjang.nama_menu = it1.data.nama_menu
                                    listKeranjang.add(keranjang)
                                    val adapter = CheckoutAdapter(listKeranjang)
                                    mRecyclerView.adapter = adapter
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    //Validasi Alamat User
    private fun validate(): Boolean {
        if(lokasiCo.text.toString() == "") {
            Toast.makeText(this, "Alamat masih kosong", Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }

    //Checkout Pesanan
    @SuppressLint("NewApi")
    private fun buatPesanan() {
        if (user != null) {
            val currentTime = Tanggal.format(Date(), "dd MMM YYYY, hh:mm aa")

            checkoutViewModel.buatPesanan(
                user?.id_user.toString(),
                idKeranjang, keteranganCo.text.toString(), currentTime, lokasiCo.text.toString(),
                total.toString(), ongkir.toString(), (total + ongkir).toString(), "diproses", ""
            ).observe(this@ActivityCheckout) {

            }
        }
    }

    //Validasi Pesanan
    override fun onStart() {
        super.onStart()
        user?.id_user?.let { idUser ->
            checkoutViewModel.getKeranjang(idUser).observe(this@ActivityCheckout) {
                if (it is Resource.Success && it.data != null) {
                    val list = it.data
                    if (list.isEmpty()) {
                        idLayoutCo.visibility = View.GONE
                        mRecyclerView.visibility = View.GONE
                        btnLayoutCo.visibility = View.GONE
                        kosongCo.visibility = View.VISIBLE
                    } else {
                        idLayoutCo.visibility = View.VISIBLE
                        mRecyclerView.visibility = View.VISIBLE
                        btnLayoutCo.visibility = View.VISIBLE
                        kosongCo.visibility = View.GONE
                    }
                    loadingCo.visibility = View.GONE
                } else if (it is Resource.Loading) {
                    idLayoutCo.visibility = View.VISIBLE
                    mRecyclerView.visibility = View.GONE
                    btnLayoutCo.visibility = View.GONE
                    kosongCo.visibility = View.GONE
                    loadingCo.visibility = View.GONE
                } else {
                    idLayoutCo.visibility = View.GONE
                    mRecyclerView.visibility = View.GONE
                    btnLayoutCo.visibility = View.GONE
                    kosongCo.visibility = View.VISIBLE
                    loadingCo.visibility = View.GONE
                }
            }
        }
    }
}