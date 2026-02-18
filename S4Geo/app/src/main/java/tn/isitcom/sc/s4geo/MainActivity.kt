package tn.isitcom.sc.s4geo

import android.Manifest
import android.annotation.SuppressLint
import android.app.PendingIntent
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Geocoder
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import java.util.Locale

import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    private lateinit var locationText: TextView
    private lateinit var stopBtn: Button
    
    // Client de localisation
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    
    // Code de requete permission
    private val LOCATION_PERMISSION_CODE = 100

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialisation UI
        locationText = findViewById(R.id.locationTextView)
        stopBtn = findViewById(R.id.btnStopUpdates)
        
        // Initialisation FusedLocationProviderClient
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        findViewById<Button>(R.id.btnGetLocation).setOnClickListener {
            checkPermissionAndGetLocation()
        }
        
        findViewById<Button>(R.id.btnStartUpdates).setOnClickListener {
            checkPermissionAndStartUpdates()
        }
        
        stopBtn.setOnClickListener {
            stopLocationUpdates()
        }
    }

    // --- Permissions ---

    private fun checkPermissionAndGetLocation() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) 
            == PackageManager.PERMISSION_GRANTED) {
            getLastLocation()
        } else {
            requestPermission()
        }
    }
    
    private fun checkPermissionAndStartUpdates() {
         if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) 
            == PackageManager.PERMISSION_GRANTED) {
            startLocationUpdates()
        } else {
            requestPermission()
        }
    }
    
    private fun requestPermission() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION),
            LOCATION_PERMISSION_CODE
        )
    }
    
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == LOCATION_PERMISSION_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permission accordee", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Permission refusee", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // --- Single Location ---

    @SuppressLint("MissingPermission")
    private fun getLastLocation() {
        fusedLocationClient.lastLocation.addOnSuccessListener { location ->
            if (location != null) {
                // Utilisation de coroutine pour l'appel non-bloquant
                lifecycleScope.launch {
                    val address = getAddress(location.latitude, location.longitude)
                    locationText.text = "Lat: ${location.latitude}\nLon: ${location.longitude}\n\nAdresse:\n$address"
                }
            } else {
                locationText.text = "Aucune derniere position connue"
            }
        }
    }

    // --- Continuous Updates ---

    @SuppressLint("MissingPermission")
    private fun startLocationUpdates() {
        val locationRequest = LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, 5000)
            .setWaitForAccurateLocation(false)
            .setMinUpdateIntervalMillis(2000)
            .build()
        
        val intent = Intent(this, LocationReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(
            this, 0, intent, 
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        
        fusedLocationClient.requestLocationUpdates(locationRequest, pendingIntent)
        
        stopBtn.visibility = View.VISIBLE
        Toast.makeText(this, "Suivi demarre", Toast.LENGTH_SHORT).show()
    }
    
    private fun stopLocationUpdates() {
        val intent = Intent(this, LocationReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(
            this, 0, intent, 
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        
        fusedLocationClient.removeLocationUpdates(pendingIntent)
        stopBtn.visibility = View.GONE
        Toast.makeText(this, "Suivi arrete", Toast.LENGTH_SHORT).show()
    }

    // --- Geocoding (Exercise) ---

    // Fonction suspendue pour execution background
    private suspend fun getAddress(lat: Double, lng: Double): String {
        return withContext(Dispatchers.IO) {
            val geocoder = Geocoder(this@MainActivity, Locale.getDefault())
            try {
                @Suppress("DEPRECATION")
                val addresses = geocoder.getFromLocation(lat, lng, 1)
                if (addresses != null && addresses.isNotEmpty()) {
                    val address = addresses[0]
                    // Retourne l'adresse si trouvee
                    address.getAddressLine(0) ?: "Adresse introuvable"
                } else {
                    "Adresse introuvable"
                }
            } catch (e: Exception) {
                e.printStackTrace()
                "Erreur de geocodage: ${e.message}"
            }
        }
    }
}