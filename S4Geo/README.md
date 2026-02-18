# Séance 4 : Géolocalisation avec Android & Kotlin

Ce projet est une application Android de démonstration pour la **Séance 4** du cours Android/Kotlin. Elle illustre l'utilisation de l'API **Fused Location Provider** pour récupérer la position de l'utilisateur et effectue du **Geocoding** pour convertir les coordonnées en adresse postale.

![Architecture S4](s4.png)

## Fonctionnalités

1.  **Permission Runtime** : Demande dynamique des permissions de localisation (`ACCESS_FINE_LOCATION`).
2.  **Position Unique** (`lastLocation`) : Récupère la dernière position connue.
3.  **Suivi Continu** (`requestLocationUpdates`) : Reçoit les mises à jour de position en arrière-plan via un `BroadcastReceiver`.
4.  **Geocoding** : Convertit Latitude/Longitude en adresse (Rue, Ville) via `Geocoder` (exécution asynchrone avec Coroutines).

## Prérequis Techniques

-   **Langage** : Kotlin
-   **API** : Google Play Services Location (`com.google.android.gms:play-services-location`).
-   **Asynchronisme** : Kotlin Coroutines (`kotlinx-coroutines-android`).

## Configuration du Projet

### 1. Dépendances (`build.gradle.kts`)

```kotlin
dependencies {
    // API de Localisation
    implementation("com.google.android.gms:play-services-location:21.0.1")
    
    // Pour éviter les blocages UI (Geocoding)
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.2")
}
```

### 2. Manifest (`AndroidManifest.xml`)

Ajout des permissions et déclaration du Receiver.

```xml
<manifest ...>
    <!-- Permissions de localisation -->
    <uses-permission android:name="android.permission.ACCESS_FINE_LOCATION" />
    <uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION" />
    <!-- Permission Internet requise pour le Geocoding -->
    <uses-permission android:name="android.permission.INTERNET" />

    <application ...>
        <!-- Receiver pour les mises à jour en arrière-plan -->
        <receiver android:name=".LocationReceiver" android:exported="false">
            <intent-filter>
                <action android:name="com.google.android.gms.location.LocationServices"/>
            </intent-filter>
        </receiver>
    </application>
</manifest>
```

## Implémentation Clé (`MainActivity.kt`)

### Initialisation
```kotlin
private lateinit var fusedLocationClient: FusedLocationProviderClient

override fun onCreate(savedInstanceState: Bundle?) {
    // ...
    fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
}
```

### Récupération Position Unique
```kotlin
fusedLocationClient.lastLocation.addOnSuccessListener { location ->
    if (location != null) {
        // Lancer le Geocoding en arrière-plan
        lifecycleScope.launch {
            val address = getAddress(location.latitude, location.longitude)
            // Mise à jour UI
        }
    }
}
```

### Geocoding Asynchrone (Correctif Timeout)
L'appel réseau `geocoder.getFromLocation` est bloquant. On l'exécute sur `Dispatchers.IO`.

```kotlin
private suspend fun getAddress(lat: Double, lng: Double): String {
    return withContext(Dispatchers.IO) {
        val geocoder = Geocoder(this@MainActivity, Locale.getDefault())
        try {
            val addresses = geocoder.getFromLocation(lat, lng, 1)
            // Retourner l'adresse formatée
        } catch (e: Exception) {
            "Erreur: ${e.message}"
        }
    }
}
```

## Structure du Projet
```
S4Geo/
├── app/src/main/java/tn/isitcom/sc/s4geo/
│   ├── MainActivity.kt       // Logique principale
│   └── LocationReceiver.kt   // Réception des mises à jour
├── app/src/main/AndroidManifest.xml
└── app/build.gradle.kts
```

---
**Note** : Testez cette application sur un émulateur avec les services Google Play ou un appareil physique. Sur émulateur, n'oubliez pas de simuler une position GPS dans les paramètres étendus.
