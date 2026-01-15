# ContactsApp - Séance 2

Application Android de gestion de contacts avec Room Database, Architecture MVVM et Coroutines.

## 📱 Fonctionnalités

- ✅ Liste des contacts avec RecyclerView
- ✅ Ajout de nouveaux contacts
- ✅ Suppression de contacts
- ✅ Détails d'un contact
- ✅ Persistance avec Room Database
- ✅ Architecture MVVM
- ✅ Coroutines pour opérations asynchrones
- ✅ View Binding
- ✅ SharedPreferences pour préférences

## 🛠️ Technologies Utilisées

- **Langage**: Kotlin
- **Architecture**: MVVM (Model-View-ViewModel)
- **Base de données**: Room Database
- **Asynchronisme**: Kotlin Coroutines & Flow
- **UI**: View Binding, RecyclerView, Material Design
- **Minimum SDK**: 24 (Android 7.0)
- **Target SDK**: 34

## 📦 Dépendances Principales

```gradle
// Room Database
implementation("androidx.room:room-runtime:2.6.1")
implementation("androidx.room:room-ktx:2.6.1")
ksp("androidx.room:room-compiler:2.6.1")

// ViewModel & LiveData
implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.7.0")
implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.7.0")

// Coroutines
implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")
```

## 🏗️ Architecture

```
┌─────────────────────────────────────────┐
│           VIEW (Activity)               │
│  - Affichage                            │
│  - Interactions utilisateur             │
└─────────────────┬───────────────────────┘
                  │ observe()
                  ↓
┌─────────────────────────────────────────┐
│           VIEWMODEL                     │
│  - Logique métier                       │
│  - Gestion de l'état                    │
└─────────────────┬───────────────────────┘
                  │
                  ↓
┌─────────────────────────────────────────┐
│           REPOSITORY                    │
│  - Accès aux données                    │
└─────────────────┬───────────────────────┘
                  │
                  ↓
┌─────────────────────────────────────────┐
│      DATA SOURCE (Room/SharedPrefs)     │
│  - Stockage persistant                  │
└─────────────────────────────────────────┘
```

## 🚀 Installation

1. Cloner le repository
```bash
git clone https://github.com/GUEDDES/cours-android-kotlin.git
cd cours-android-kotlin/Seance2/ContactsApp
```

2. Ouvrir avec Android Studio

3. Synchroniser Gradle

4. Lancer sur émulateur ou appareil

## 📚 Concepts Appris

- Entity, DAO, Database (Room)
- suspend functions
- Coroutines et Dispatchers
- Flow et LiveData
- Repository Pattern
- ViewModel avec Factory
- View Binding
- ListAdapter avec DiffUtil

## 👨‍💻 Auteur

**Dr. Abdelweheb GUEDDES**
- Cours: Android Avancé avec Kotlin
- Année: 2025-2026

## 📄 Licence

Ce projet est à des fins éducatives.