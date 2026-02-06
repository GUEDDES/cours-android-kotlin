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

## 🏗️ Architecture Détaillée (TP Séance 2)

Cette application illustre l'utilisation conjointe de **Room** (base de données) et **SharedPreferences** (préférences simples) au sein d'une architecture **MVVM**.

![Architecture Séance 2](architecture_s2.png)

### 1. PERSISTANCE DES DONNÉES (Le Stockage) 💾
Deux méthodes de stockage sont utilisées selon la complexité des données :

- **Shared Preferences (Zone Bleue)** :
  - *Usage :* Stockage léger clé-valeur.
  - *Exemple :* Compteur de lancements de l'application (`launch_count`).
  - *Fichier :* XML simple dans le dossier privé de l'app.

- **Room Database (Zone Rouge)** :
  - *Usage :* Stockage de données structurées et complexes.
  - *Exemple :* La liste des contacts (Nom, Numéro, Email).
  - *Technologie :* Surcouche à SQLite qui vérifie le SQL à la compilation.
  - *Composants :*
    - `AppDatabase.kt` : Le point d'entrée de la BDD.
    - `ContactDao.kt` : L'interface définissant les opérations (Insert, Delete, Query).
    - `Contact.kt` : L'entité (table).

### 2. ARCHITECTURE MVVM (Le Flux de Données) 🔄

- **VIEW (Zone Bleue - UI)** :
  - `MainActivity`, `AddContactActivity`, `Adapter`.
  - Son rôle est uniquement d'afficher. Elle **observe** le ViewModel.
  - Elle ne touche jamais directement à la base de données.

- **VIEWMODEL (Zone Verte - Cerveau)** :
  - `ContactViewModel`.
  - Il fait le pont entre l'UI et le Repository.
  - Il s'exécute sur le **UI Thread** mais lance les tâches lourdes via des **Coroutines**.
  - Il expose des `LiveData` ou `Flow` que la Vue observe.

- **MODEL / REPOSITORY (Zone Jaune - Source)** :
  - `ContactRepository`.
  - C'est la source unique de vérité.
  - Il décide si on va chercher les données dans Room ou ailleurs.
  - Il s'exécute en arrière-plan (Background) via `suspend functions`.

### 3. TP STRUCTURE & CLASSES (Organisation) 📁

- **data/model/** : `Contact.kt` (Ce qu'on sauvegarde)
- **data/dao/** : `ContactDao.kt` (Comment on sauvegarde)
- **data/database/** : `AppDatabase.kt` (Où on sauvegarde)
- **data/repository/** : `ContactRepository.kt` (Qui gère la sauvegarde)
- **viewmodel/** : `ContactViewModel.kt` (Qui prépare les données)
- **ui/** : `MainActivity.kt` (Qui affiche les données)

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