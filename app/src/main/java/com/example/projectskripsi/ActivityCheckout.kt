package com.example.projectskripsi

import android.content.Context
import android.content.SharedPreferences
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.Manifest.permission
import android.annotation.SuppressLint
import android.content.DialogInterface
import android.content.Intent
import android.location.Address
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*
import java.text.SimpleDateFormat
import java.util.*
import android.location.Geocoder
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.cardview.widget.CardView
import com.example.projectskripsi.adapter.ViewholderCheckout
import com.example.projectskripsi.model.Keranjang
import com.example.projectskripsi.model.Pesanan
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.google.android.gms.location.*
import com.google.android.gms.location.LocationServices
import java.text.DecimalFormat
import java.text.NumberFormat
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.ValueEventListener

class ActivityCheckout : AppCompatActivity() {
    lateinit var databaseCo: DatabaseReference
    lateinit var SP: SharedPreferences
    lateinit var alertDialog: AlertDialog.Builder
    lateinit var mLayoutManager: LinearLayoutManager
    lateinit var mRecyclerView: RecyclerView
    lateinit var identitasCo: TextView
    lateinit var lokasiCo: TextView
    lateinit var radioJarakCo: RadioGroup
    lateinit var keteranganCo: EditText
    lateinit var subtotalCo: TextView
    lateinit var ongkirCo: TextView
    lateinit var totalCo: TextView
    lateinit var btnPesanCo: Button
    lateinit var idLayoutCo: CardView
    lateinit var btnLayoutCo: CardView
    lateinit var kosongCo: TextView

    var formatter: NumberFormat = DecimalFormat("#,###")
    var ongkir = 0

    @SuppressLint("MissingPermission")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_checkout)

        identitasCo = findViewById(R.id.identitasCo)
        lokasiCo = findViewById(R.id.lokasiCo)
        radioJarakCo = findViewById(R.id.radioJarakCo)
        keteranganCo = findViewById(R.id.keteranganCo)
        subtotalCo = findViewById(R.id.subtotalCo)
        ongkirCo = findViewById(R.id.ongkirCo)
        totalCo = findViewById(R.id.totalCo)
        btnPesanCo = findViewById(R.id.btnPesanCo)
        idLayoutCo = findViewById(R.id.idLayoutCo)
        btnLayoutCo = findViewById(R.id.btnLayoutCo)
        kosongCo = findViewById(R.id.kosongCo)

        alertDialog = AlertDialog.Builder(this)
        SP = applicationContext.getSharedPreferences("User", Context.MODE_PRIVATE)
        databaseCo = FirebaseDatabase.getInstance().getReference("keranjang")
            .child(SP.getString("id_user", "").toString())
        val nama = SP.getString("nama", "")
        val telp = SP.getString("telp", "")
        identitasCo.text = "$nama ($telp)"

        mLayoutManager = LinearLayoutManager(this)
        mRecyclerView = findViewById(R.id.recyclerCo)
        mRecyclerView.setHasFixedSize(true)
        mRecyclerView.layoutManager = mLayoutManager

        databaseCo.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                var total = 0
                for (ds in dataSnapshot.children) {
                    val keranjang = ds.getValue(Keranjang::class.java)
                    val mTotalPrice = Integer.valueOf(keranjang!!.total)
                    total += mTotalPrice
                    subtotalCo.text = "Rp. " + formatter.format(total) + ",00"

                    radioJarakCo.setOnCheckedChangeListener { group, checkedId ->
                        val radioButton = findViewById<RadioButton>(checkedId)
                        if(radioButton.text.toString() == "Dibawah 10 km") {
                            ongkir = 5000
                        } else {
                            ongkir = 10000
                        }
                        ongkirCo.text = "Rp. " + formatter.format(ongkir) + ",00"
                        totalCo.text = "Rp. " + formatter.format(total + ongkir) + ",00"
                    }
                }
            }
            override fun onCancelled(databaseError: DatabaseError) {}
        })

        val mLocationRequest: LocationRequest = LocationRequest.create()
        mLocationRequest.interval = 60000
        mLocationRequest.fastestInterval = 5000
        mLocationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        val mLocationCallback: LocationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                for (location in locationResult.locations) {
                    if (location != null) {
                        //TODO: UI updates.
                    }
                }
            }
        }
        LocationServices.getFusedLocationProviderClient(this)
            .requestLocationUpdates(mLocationRequest, mLocationCallback, null)

        checkPermissions()
        listKeranjang()

        btnPesanCo.setOnClickListener {
            alertDialog.setMessage("Pesanan akan langsung diproses dan tidak dapat dibatalkan. Cek kembali pesanan anda, Apakah sudah sesuai ?").setCancelable(false)
                .setPositiveButton("YA", object: DialogInterface.OnClickListener {
                    override fun onClick(dialog: DialogInterface, id:Int) {
                        if(validate()){
                            buatPesanan()
                            val intent = Intent(this@ActivityCheckout, ActivityUtama::class.java)
                            intent.putExtra("pesanan", "true")
                            startActivity(intent)
                            finish()
                        }
                    }
                })
                .setNegativeButton("TIDAK", object: DialogInterface.OnClickListener {
                    override fun onClick(dialog: DialogInterface, id:Int) {
                        dialog.cancel()
                    }
                }).create().show()
        }
    }

    private fun checkPermissions() {
        if(ContextCompat.checkSelfPermission(this,
                permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
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
            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if(requestCode == 1) {
            if(grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if(ContextCompat.checkSelfPermission(this,
                        permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show()
                    getLocations()
                } else {
                    Toast.makeText(this, "Permission Danied", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

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
                        val intent = Intent(view.context, ActivityDetail::class.java)
                        intent.putExtra("id_menu", viewHolder.keranjang.id_menu)
                        startActivity(intent)
                    }
                    override fun onItemLongClick(view: View, position:Int) {}
                })
                return viewHolder
            }
        }
        mRecyclerView.adapter = firebaseRecyclerAdapter
    }

    private fun validate(): Boolean {
        if(totalCo.text.toString() == "") {
            Toast.makeText(this, "Pilih Jarak yang sesuai", Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }

    @SuppressLint("NewApi")
    private fun buatPesanan() {
        val ref = FirebaseDatabase.getInstance().getReference("pesanan")
        val id_pesanan  = ref.push().key.toString()
        val time = SimpleDateFormat("dd MMM YYYY, hh:mm aa")
        val currentTime = time.format(Date())
        val keranjang = arrayListOf<Keranjang>()

        val addData = Pesanan(id_pesanan, keranjang, keteranganCo.text.toString(),
            currentTime.toString(), lokasiCo.text.toString(), subtotalCo.text.toString(),
            ongkirCo.text.toString(), totalCo.text.toString(), "Diproses")
        ref.child(id_pesanan).setValue(addData)

        databaseCo.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                ref.child(id_pesanan).child("keranjang").setValue(dataSnapshot.value)
            }
            override fun onCancelled(databaseError: DatabaseError) {}
        })
        databaseCo.removeValue()
    }

    override fun onStart() {
        super.onStart()
        databaseCo.addValueEventListener(object: ValueEventListener {
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