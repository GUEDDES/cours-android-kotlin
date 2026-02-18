package tn.isitcom.sc.s4geo

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import com.google.android.gms.location.LocationResult

class LocationReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        if (LocationResult.hasResult(intent)) {
            val result = LocationResult.extractResult(intent)

            result?.locations?.forEach { location ->
                val lat = location.latitude
                val lon = location.longitude

                val message = "Maj: $lat / $lon"
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                Log.d("LocationReceiver", "Position recue: $lat, $lon")
            }
        }
    }
}
