# Installation et Configuration - Séance 2

## 🛠️ Prérequis

Avant de commencer cette séance, assurez-vous d'avoir :

- ✅ Android Studio Hedgehog (2023.1.1) ou plus récent
- ✅ SDK Android minimum : API 24 (Android 7.0)
- ✅ Connaissance de Kotlin de base (Séance 1)
- ✅ Connaissance de RecyclerView et Adapter (Séance 1)

---

## 📚 Étape 1 : Créer le Projet

### 1.1 Nouveau Projet Android Studio

1. Ouvrir Android Studio
2. `File` > `New` > `New Project`
3. Sélectionner `Empty Activity`
4. Configurer :
   - **Name** : `ContactsAppRoom`
   - **Package name** : `com.example.contactsapp`
   - **Language** : **Kotlin**
   - **Minimum SDK** : API 24 (Android 7.0)
   - **Build configuration language** : Kotlin DSL

5. Cliquer sur `Finish`

---

## ⚙️ Étape 2 : Configuration Gradle

### 2.1 Fichier `build.gradle.kts` (Project level)

Ajouter le plugin KSP (Kotlin Symbol Processing) :

```kotlin
plugins {
    id("com.android.application") version "8.2.1" apply false
    id("org.jetbrains.kotlin.android") version "1.9.20" apply false
    id("com.google.devtools.ksp") version "1.9.20-1.0.14" apply false
}
```

### 2.2 Fichier `build.gradle.kts` (Module :app)

Remplacer le contenu par :

```kotlin
plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.devtools.ksp")
}

android {
    namespace = "com.example.contactsapp"
    compileSdk = 34
    
    defaultConfig {
        applicationId = "com.example.contactsapp"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
    }
    
    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    
    kotlinOptions {
        jvmTarget = "17"
    }
    
    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    // Core
    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    
    // Room
    val roomVersion = "2.6.1"
    implementation("androidx.room:room-runtime:$roomVersion")
    implementation("androidx.room:room-ktx:$roomVersion")
    ksp("androidx.room:room-compiler:$roomVersion")
    
    // Lifecycle (ViewModel, LiveData)
    val lifecycleVersion = "2.7.0"
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycleVersion")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:$lifecycleVersion")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:$lifecycleVersion")
    implementation("androidx.activity:activity-ktx:1.8.2")
    implementation("androidx.fragment:fragment-ktx:1.6.2")
    
    // Coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")
    
    // RecyclerView
    implementation("androidx.recyclerview:recyclerview:1.3.2")
    
    // CardView
    implementation("androidx.cardview:cardview:1.0.0")
}
```

### 2.3 Synchroniser le Projet

Cliquer sur `Sync Now` en haut à droite pour télécharger les dépendances.

---

## 📁 Étape 3 : Structure des Packages

Créer la structure de packages suivante dans `app/src/main/java/com/example/contactsapp/` :

```
com.example.contactsapp/
├── data/
│   ├── model/
│   ├── dao/
│   ├── database/
│   └── repository/
├── ui/
│   ├── viewmodel/
│   └── adapter/
└── util/
```

**Pour créer un package :**
1. Clic droit sur `com.example.contactsapp`
2. `New` > `Package`
3. Saisir le nom (ex: `data.model`)

---

## 📝 Étape 4 : Copier les Fichiers Sources

### 4.1 Modèle de Données

Copier le fichier [`data/model/Contact.kt`](data/model/Contact.kt) dans votre package `data.model`

### 4.2 DAO

Copier le fichier [`data/dao/ContactDao.kt`](data/dao/ContactDao.kt) dans votre package `data.dao`

### 4.3 Database

Copier le fichier [`data/database/AppDatabase.kt`](data/database/AppDatabase.kt) dans votre package `data.database`

### 4.4 Repository

Copier le fichier [`data/repository/ContactRepository.kt`](data/repository/ContactRepository.kt) dans votre package `data.repository`

### 4.5 ViewModel

Copier les fichiers du dossier [`ui/viewmodel/`](ui/viewmodel/) dans votre package `ui.viewmodel`

### 4.6 Adapter

Copier le fichier [`ui/adapter/ContactAdapter.kt`](ui/adapter/ContactAdapter.kt) dans votre package `ui.adapter`

### 4.7 Utilities

Copier le fichier [`util/PreferencesManager.kt`](util/PreferencesManager.kt) dans votre package `util`

### 4.8 Activities

Copier les fichiers suivants dans le package principal `com.example.contactsapp` :
- [`MainActivity.kt`](MainActivity.kt)
- [`AddContactActivity.kt`](AddContactActivity.kt)
- [`ContactDetailActivity.kt`](ContactDetailActivity.kt)

---

## 🎨 Étape 5 : Layouts XML

Copier les fichiers XML du dossier [`layouts/`](layouts/) dans `app/src/main/res/layout/` :

- `activity_main.xml`
- `activity_add_contact.xml`
- `activity_contact_detail.xml`
- `item_contact.xml`

---

## 📜 Étape 6 : AndroidManifest.xml

Mettre à jour le fichier `AndroidManifest.xml` :

```xml
<?xml version="1.0" encoding="utf-8"?>
<manifest xmlns:android="http://schemas.android.com/apk/res/android">

    <application
        android:allowBackup="true"
        android:icon="@mipmap/ic_launcher"
        android:label="@string/app_name"
        android:theme="@style/Theme.ContactsApp">
        
        <activity
            android:name=".MainActivity"
            android:exported="true">
            <intent-filter>
                <action android:name="android.intent.action.MAIN" />
                <category android:name="android.intent.category.LAUNCHER" />
            </intent-filter>
        </activity>
        
        <activity
            android:name=".AddContactActivity"
            android:exported="false"
            android:parentActivityName=".MainActivity" />
        
        <activity
            android:name=".ContactDetailActivity"
            android:exported="false"
            android:parentActivityName=".MainActivity" />
            
    </application>

</manifest>
```

---

## ✅ Étape 7 : Vérification

### 7.1 Vérifier les Erreurs

1. Ouvrir `Build` > `Rebuild Project`
2. Vérifier qu'il n'y a pas d'erreur de compilation
3. Si erreurs, vérifier :
   - Les imports
   - Les noms de packages
   - Les noms de fichiers XML

### 7.2 Exécuter l'Application

1. Sélectionner un émulateur ou connecter un appareil
2. Cliquer sur `Run` (bouton vert)
3. L'application devrait se lancer sans erreur

---

## 🐛 Dépannage

### Problème : "Unresolved reference"

**Solution :** Vérifier que tous les fichiers sont dans les bons packages.

### Problème : "Cannot find symbol class ActivityMainBinding"

**Solution :** 
1. Vérifier que `viewBinding = true` est dans `build.gradle.kts`
2. `Build` > `Clean Project`
3. `Build` > `Rebuild Project`

### Problème : "Room schema export directory is not provided"

**Solution :** C'est un avertissement, pas une erreur. Vous pouvez l'ignorer ou ajouter `exportSchema = false` dans `@Database`.

### Problème : Erreur de compilation KSP

**Solution :**
1. Vérifier que le plugin KSP est bien ajouté
2. Vérifier que les versions correspondent (Kotlin 1.9.20 <=> KSP 1.9.20-1.0.14)
3. `File` > `Invalidate Caches / Restart`

---

## 📚 Ressources

- [Configuration Room](https://developer.android.com/training/data-storage/room)
- [Configuration ViewBinding](https://developer.android.com/topic/libraries/view-binding)
- [KSP (Kotlin Symbol Processing)](https://github.com/google/ksp)

---

## ✅ Checklist Finale

Avant de commencer les exercices, vérifiez que :

- [ ] Le projet compile sans erreur
- [ ] L'application se lance sur l'émulateur
- [ ] Vous pouvez ajouter un contact
- [ ] La liste des contacts s'affiche
- [ ] Vous pouvez supprimer un contact
- [ ] Les contacts persistent après fermeture de l'app

Si tout fonctionne, vous êtes prêt pour les exercices ! 🎉

---

## 🚀 Prochaine Étape

Consultez le fichier [EXERCICES.md](EXERCICES.md) pour pratiquer !
