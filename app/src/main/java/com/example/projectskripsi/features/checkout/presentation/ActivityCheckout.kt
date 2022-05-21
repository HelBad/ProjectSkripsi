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
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
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
import com.example.projectskripsi.features.checkout.presentation.adapter.ViewholderCheckout
import com.example.projectskripsi.features.checkout.presentation.viewmodel.CheckoutViewModel
import com.example.projectskripsi.features.menu.presentation.ActivityMenuUser
import com.example.projectskripsi.features.pesanan.domain.entities.Pesanan
import com.example.projectskripsi.utils.Rupiah
import com.example.projectskripsi.utils.Tanggal
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.firebase.database.*
import org.koin.android.viewmodel.ext.android.viewModel
import java.text.DecimalFormat
import java.util.*

class ActivityCheckout : AppCompatActivity() {
    private val checkoutViewModel: CheckoutViewModel by viewModel()

    var databaseCo: DatabaseReference? = null
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

                databaseCo = FirebaseDatabase.getInstance().getReference("keranjang")
                    .child("ready").child(user?.idUser.toString())

                mLayoutManager = LinearLayoutManager(this)
                mRecyclerView = findViewById(R.id.recyclerCo)
                mRecyclerView.setHasFixedSize(true)
                mRecyclerView.layoutManager = mLayoutManager

                databaseCo?.addValueEventListener(object: ValueEventListener {
                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        for (ds in dataSnapshot.children) {
                            val keranjang = ds.getValue(Keranjang::class.java)
                            idKeranjang = keranjang!!.id_keranjang
                            val mTotalPrice = Integer.valueOf(keranjang.total)
                            total += mTotalPrice
                            subtotalCo.text = Rupiah.format(total)
                        }
                    }
                    override fun onCancelled(databaseError: DatabaseError) {}
                })

                lokasiSekarang()
                listKeranjang()
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
        val firebaseRecyclerAdapter = object: FirebaseRecyclerAdapter<Keranjang, ViewholderCheckout>(
            Keranjang::class.java,
            R.layout.menu_checkout,
            ViewholderCheckout::class.java,
            databaseCo
        ) {
            override fun populateViewHolder(viewHolder: ViewholderCheckout, model: Keranjang, position:Int) {
                viewHolder.setDetails(model)
            }
            override fun onCreateViewHolder(parent: ViewGroup, viewType:Int): ViewholderCheckout {
                val viewHolder = super.onCreateViewHolder(parent, viewType)
                viewHolder.setOnClickListener(object: ViewholderCheckout.ClickListener {
                    override fun onItemClick(view: View, position:Int) {
                        val intent = Intent(view.context, ActivityMenuUser::class.java)
                        intent.putExtra("id_menu", viewHolder.keranjang.id_menu)
                        startActivity(intent)
                        finish()
                    }
                    override fun onItemLongClick(view: View, position:Int) {}
                })
                return viewHolder
            }
        }
        mRecyclerView.adapter = firebaseRecyclerAdapter
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
            val ref = FirebaseDatabase.getInstance().getReference("pesanan")
            val idPesanan  = ref.push().key.toString()
            val currentTime = Tanggal.format(Date(), "dd MMM YYYY, hh:mm aa")

            val addData = Pesanan(idPesanan, user?.idUser.toString(),
                idKeranjang, keteranganCo.text.toString(), currentTime, lokasiCo.text.toString(),
                total.toString(), ongkir.toString(), (total + ongkir).toString(), "diproses", "")
            ref.child("diproses").child(idPesanan).setValue(addData)

            databaseCo?.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    FirebaseDatabase.getInstance().getReference("keranjang").child("kosong")
                        .child(user?.idUser.toString() + " | $idKeranjang").setValue(dataSnapshot.value)
                    databaseCo?.removeValue()
                }
                override fun onCancelled(databaseError: DatabaseError) {}
            })
        }
    }

    //Validasi Pesanan
    override fun onStart() {
        super.onStart()
        if (databaseCo != null) {
            databaseCo?.addValueEventListener(object: ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    val project = dataSnapshot.childrenCount.toInt()
                    if(project == 0) {
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
                }
                override fun onCancelled(databaseError: DatabaseError) {}
            })
        }
    }
}