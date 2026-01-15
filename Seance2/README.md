# Séance 2 : Persistance des Données et Architecture MVVM

## 🎯 Objectifs de la Séance

À la fin de cette séance, vous serez capable de :

- ✅ Comprendre les différentes méthodes de persistance des données
- ✅ Utiliser SharedPreferences pour des données simples
- ✅ Créer une base de données locale avec Room
- ✅ Comprendre et utiliser les Coroutines Kotlin
- ✅ Implémenter l'architecture MVVM
- ✅ Gérer les opérations asynchrones correctement

---

## 📖 Table des Matières

1. [Introduction à la Persistance](#1-introduction-à-la-persistance)
2. [SharedPreferences](#2-sharedpreferences)
3. [Room Database](#3-room-database)
4. [Coroutines Kotlin](#4-coroutines-kotlin)
5. [Architecture MVVM](#5-architecture-mvvm)
6. [Application Complète](#6-application-complète)

---

## 1. Introduction à la Persistance

### Pourquoi persister les données ?

**🛑 Sans persistance :**
- Les données disparaissent quand on ferme l'application
- L'utilisateur doit tout re-saisir à chaque lancement
- Pas de mode hors-ligne

**✅ Avec persistance :**
- Les données sont sauvegardées localement
- L'application garde l'état entre les sessions
- Fonctionne sans connexion Internet

### Les Méthodes de Persistance sur Android

| Méthode | Utilisation | Exemples |
|--------|-------------|----------|
| **SharedPreferences** | Données simples (clé-valeur) | Paramètres, token, préférences |
| **Room Database** | Données structurées complexes | Listes, tables relationnelles |
| **Fichiers** | Fichiers multimédias | Images, PDF, vidéos |

---

## 2. SharedPreferences

### Concept de Base

**SharedPreferences** = Un petit carnet où vous notez des informations sous forme `CLÉ = VALEUR`

Exemples :
```kotlin
username = "Ahmed"
isLoggedIn = true
counter = 42
```

C'est un fichier XML stocké sur le téléphone !

### ÉTAPE 1 : Sauvegarder des Données

```kotlin
// 1. Obtenir l'instance SharedPreferences
val sharedPrefs = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)

// 2. Créer un éditeur
val editor = sharedPrefs.edit()

// 3. Ajouter des données
editor.putString("username", "Ahmed")
editor.putInt("age", 25)
editor.putBoolean("isLoggedIn", true)

// 4. IMPORTANT: Sauvegarder!
editor.apply()  // Asynchrone (recommandé)
// OU
editor.commit() // Synchrone (bloque le thread)
```

### ÉTAPE 2 : Lire des Données

```kotlin
// 1. Obtenir l'instance
val sharedPrefs = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)

// 2. Lire les données avec valeur par défaut
val username = sharedPrefs.getString("username", "Invité") ?: "Invité"
val age = sharedPrefs.getInt("age", 0)
val isLoggedIn = sharedPrefs.getBoolean("isLoggedIn", false)

// 3. Utiliser les données
println("Bonjour $username, age: $age")
```

### 💡 apply() vs commit()

**apply() :**
- Sauvegarde en arrière-plan (asynchrone)
- Ne bloque pas l'application
- Recommandé dans 99% des cas

**commit() :**
- Sauvegarde immédiatement (synchrone)
- Bloque l'application jusqu'à la fin
- Utiliser seulement si vous devez être SÛR que c'est sauvegardé

### ÉTAPE 3 : Version Moderne avec Kotlin

```kotlin
// Extension function pour simplifier
fun SharedPreferences.edit(action: SharedPreferences.Editor.() -> Unit) {
    val editor = edit()
    action(editor)
    editor.apply()
}

// Utilisation super simple!
sharedPrefs.edit {
    putString("username", "Ahmed")
    putInt("age", 25)
    putBoolean("isLoggedIn", true)
}
```

### 📝 Exemple Complet : PreferencesManager

Voir le fichier : [`util/PreferencesManager.kt`](util/PreferencesManager.kt)

---

## 3. Room Database

### Qu'est-ce que Room ?

**Room** = Couche d'abstraction au-dessus de SQLite

**Avantages :**
- Moins de code
- Vérification à la compilation
- Intégration avec LiveData et Coroutines
- Moins d'erreurs

### Architecture de Room

```
         APPLICATION (ViewModel)
                 ↓
           DAO (Data Access Object)
                 ↓
           ROOM DATABASE
                 ↓
         SQLite (Stockage)
              
         ENTITY (Table) → Room
```

### Les 3 Composants Essentiels

#### 1️⃣ Entity (Table)

**Définition :** Représente une table dans la base de données

**Analogie :** Une classe Kotlin = Une table SQL

```kotlin
@Entity(tableName = "contacts")
data class Contact(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    
    @ColumnInfo(name = "full_name")
    val name: String,
    
    val phone: String,
    val email: String
)
```

**Explications :**
- `@Entity` : Indique que cette classe est une table
- `@PrimaryKey` : Clé primaire de la table
- `autoGenerate = true` : L'ID est généré automatiquement
- `@ColumnInfo` : Nom de la colonne dans la table (optionnel)

Voir le fichier : [`data/model/Contact.kt`](data/model/Contact.kt)

#### 2️⃣ DAO (Data Access Object)

**Définition :** Interface qui définit les opérations sur la base

**Analogie :** Le DAO est le "guichet" pour accéder aux données

```kotlin
@Dao
interface ContactDao {
    @Insert
    suspend fun insert(contact: Contact)
    
    @Query("SELECT * FROM contacts")
    fun getAllContacts(): Flow<List<Contact>>
    
    @Delete
    suspend fun delete(contact: Contact)
}
```

**💡 Flow vs suspend :**

**suspend fun :** Pour une opération unique
```kotlin
suspend fun insert(contact: Contact)  // Une insertion
```

**Flow :** Pour observer des changements continus
```kotlin
fun getAllContacts(): Flow<List<Contact>>  // Écoute en continu
```

**Flow = LiveData moderne avec Coroutines !**

Voir le fichier : [`data/dao/ContactDao.kt`](data/dao/ContactDao.kt)

#### 3️⃣ Database (Base de données)

**Définition :** Classe abstraite qui contient la base de données

**Analogie :** Le "manager" qui gère tout

```kotlin
@Database(entities = [Contact::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun contactDao(): ContactDao
    
    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null
        
        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "contacts_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
```

**💡 Singleton Pattern :**

**Problème :** On ne veut qu'UNE SEULE instance de la base de données

**Solution :**
- `@Volatile` : Visible par tous les threads
- `synchronized` : Un seul thread à la fois
- `INSTANCE ?: ...` : Créer seulement si null

Voir le fichier : [`data/database/AppDatabase.kt`](data/database/AppDatabase.kt)

---

## 4. Coroutines Kotlin

### Le Problème des Opérations Longues

**🛑 Problème : Bloquer le Thread Principal**

**Thread Principal (UI Thread) :**
- Gère l'interface utilisateur
- Doit rester TOUJOURS réactif
- Si bloqué = Application freeze!

**Opérations qui bloquent :**
- Accès à la base de données (Room)
- Téléchargement réseau
- Lecture/écriture de fichiers

### Qu'est-ce qu'une Coroutine ?

**Définition simple :** Une fonction qui peut être mise en pause et reprise

**Analogie :** Comme un livre qu'on peut fermer et rouvrir plus tard

**Avantage :**
- Code qui ressemble à du code synchrone
- Mais s'exécute de manière asynchrone
- Pas de callback hell
- Facile à lire et maintenir

### Les Mots-clés des Coroutines

#### 1. suspend

```kotlin
// Fonction normale
fun saveContact(contact: Contact) {
    // Bloque le thread!
}

// Fonction suspend (avec coroutine)
suspend fun saveContact(contact: Contact) {
    // Peut être mise en pause
    // Ne bloque pas le thread!
}
```

**💡 Règle :** Une fonction `suspend` ne peut être appelée que :
- Depuis une autre fonction `suspend`
- Depuis une coroutine (launch, async)

#### 2. launch

```kotlin
// Lancer une coroutine
viewModelScope.launch {
    // Code qui s'exécute en arrière-plan
    val contacts = repository.getAllContacts()
    // Mettre à jour l'UI
    _contactsList.value = contacts
}
```

#### 3. withContext

```kotlin
suspend fun loadData() {
    // Changer de thread
    withContext(Dispatchers.IO) {
        // Code sur le thread IO (base de données, réseau)
        database.contactDao().getAllContacts()
    }
    // Automatiquement de retour sur le thread principal
}
```

### Les Dispatchers

| Dispatcher | Utilisation |
|-----------|-------------|
| **Dispatchers.Main** | Thread principal (UI). Pour mettre à jour l'interface |
| **Dispatchers.IO** | Opérations I/O (base de données, réseau, fichiers) |
| **Dispatchers.Default** | Calculs intensifs (tri, filtrage, parsing) |

### Exemple Complet

```kotlin
viewModelScope.launch {
    // Par défaut sur Dispatchers.Main
    
    // Basculer sur IO pour la base de données
    val data = withContext(Dispatchers.IO) {
        database.contactDao().getAllContacts()
    }
    
    // Automatiquement de retour sur Main
    // Mettre à jour l'UI
    updateUI(data)
}
```

---

## 5. Architecture MVVM

### Pourquoi une Architecture ?

**🛑 Sans architecture :**
- Tout le code dans l'Activity
- Difficile à tester
- Difficile à maintenir
- Perte de données lors de rotation d'écran

### MVVM : Model-View-ViewModel

```
     VIEW (Activity/Fragment)
           ↑ observe ↓ events
     VIEWMODEL (Logique métier)
           ↑ data ↓ request
     MODEL (Repository)
           ↑ ↓
     DATA SOURCE (Room/API)
```

### Rôles de Chaque Composant

#### 🟦 VIEW (Activity/Fragment)

**Responsabilités :**
- Afficher les données
- Gérer les interactions utilisateur
- Observer le ViewModel

**NE DOIT PAS :**
- Contenir de la logique métier
- Accéder directement à la base de données

#### 🟩 VIEWMODEL

**Responsabilités :**
- Contenir la logique métier
- Préparer les données pour l'affichage
- Gérer l'état de l'application
- Survivre aux changements de configuration

**NE DOIT PAS :**
- Référencer le Context
- Référencer les Views

Voir le fichier : [`ui/viewmodel/ContactViewModel.kt`](ui/viewmodel/ContactViewModel.kt)

#### 🟪 MODEL (Repository)

**Responsabilités :**
- Centraliser l'accès aux données
- Gérer plusieurs sources de données (Room, API, Cache)
- Fournir une API propre au ViewModel

Voir le fichier : [`data/repository/ContactRepository.kt`](data/repository/ContactRepository.kt)

---

## 6. Application Complète

### Structure du Projet

```
Seance2/
├── data/
│   ├── model/
│   │   └── Contact.kt           # Entity
│   ├── dao/
│   │   └── ContactDao.kt        # Data Access Object
│   ├── database/
│   │   └── AppDatabase.kt       # Database
│   └── repository/
│       └── ContactRepository.kt # Repository
├── ui/
│   ├── viewmodel/
│   │   └── ContactViewModel.kt  # ViewModel
│   └── adapter/
│       └── ContactAdapter.kt    # RecyclerView Adapter
├── util/
│   └── PreferencesManager.kt # SharedPreferences
├── MainActivity.kt
├── AddContactActivity.kt
└── ContactDetailActivity.kt
```

### Flux de Données

#### Ajout d'un Contact

```
1. Utilisateur clique "Ajouter" dans AddContactActivity
   ↓
2. AddContactActivity appelle viewModel.insert(contact)
   ↓
3. ViewModel lance une coroutine et appelle repository.insert()
   ↓
4. Repository appelle contactDao.insert() (sur IO thread)
   ↓
5. Room insère dans SQLite
   ↓
6. Flow<List<Contact>> émet automatiquement la nouvelle liste
   ↓
7. MainActivity observe et met à jour le RecyclerView
```

### Points Clés à Retenir

✅ **SharedPreferences** pour les préférences simples  
✅ **Room** pour les données structurées  
✅ **Coroutines** pour les opérations asynchrones  
✅ **Flow** pour observer les changements  
✅ **MVVM** pour une architecture propre  
✅ **Repository** pour centraliser l'accès aux données  
✅ **ViewModel** survit aux rotations d'écran  

---

## 📚 Ressources Complémentaires

- [Documentation officielle Room](https://developer.android.com/training/data-storage/room)
- [Guide des Coroutines](https://kotlinlang.org/docs/coroutines-guide.html)
- [Architecture MVVM](https://developer.android.com/topic/architecture)
- [SharedPreferences](https://developer.android.com/training/data-storage/shared-preferences)

---

## 🚀 Prochaine Étape

La Séance 3 abordera :
- Retrofit pour les appels API
- Gestion des états (Loading, Success, Error)
- Injection de dépendances avec Hilt
- Tests unitaires

---

## 👨‍🏫 Auteur

Dr. Abdelweheb GUEDDES  
Année Universitaire 2025-2026
