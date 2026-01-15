# Séance 1: Rappel Android et Transition Java-Kotlin

## 🎯 Objectifs de la Séance

- Faire un rappel des concepts fondamentaux d'Android vus avec Java
- Comprendre les équivalences entre Java et Kotlin pour le développement Android
- Maîtriser la syntaxe de base de Kotlin
- Créer une première application Android en Kotlin

## 📚 Concepts Abordés

### 1. Introduction à Kotlin
- Avantages de Kotlin (concision, null safety, interopérabilité)
- Comparaison avec Java

### 2. Syntaxe de Base
- **Variables**: `var` (mutable) vs `val` (immutable)
- **Fonctions**: déclaration avec `fun`, fonctions d'expression
- **Classes**: classes normales, data classes
- **Null Safety**: types nullable (`?`), safe call (`?.`), Elvis operator (`?:`)

### 3. Concepts Android en Kotlin
- **Activity**: cycle de vie, création d'activities
- **View Binding**: remplacement de `findViewById()`
- **RecyclerView**: adapter avec lambdas
- **Intent**: navigation entre activities
- **Extensions Kotlin**: ajout de méthodes aux classes existantes

## 🏗️ Projet: ContactsApp

### Description
Application de gestion de contacts permettant de:
- Afficher une liste de contacts dans un RecyclerView
- Voir les détails d'un contact
- Naviguer entre plusieurs Activities

### Structure du Projet

```
com.example.contactsapp/
├── model/
│   └── Contact.kt              # Data class pour les contacts
├── adapter/
│   └── ContactAdapter.kt       # Adapter pour le RecyclerView
├── MainActivity.kt             # Écran principal avec liste
├── ContactDetailActivity.kt    # Écran de détails
└── res/
    └── layout/
        ├── activity_main.xml
        ├── activity_contact_detail.xml
        └── item_contact.xml
```

## 📦 Configuration du Projet

### build.gradle.kts (Module: app)

```kotlin
android {
    // ...
    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.recyclerview:recyclerview:1.3.2")
    implementation("androidx.cardview:cardview:1.0.0")
}
```

## 💡 Points Clés du Code

### 1. Data Class Contact
```kotlin
data class Contact(
    val id: Int,
    val name: String,
    val phone: String,
    val email: String
)
```

**Équivalent Java**: 30+ lignes avec getters, setters, toString, equals, hashCode
**Kotlin**: 6 lignes avec tout généré automatiquement!

### 2. View Binding
```kotlin
private lateinit var binding: ActivityMainBinding

binding = ActivityMainBinding.inflate(layoutInflater)
setContentView(binding.root)

binding.button.setOnClickListener { /* ... */ }
```

**Avantages**:
- Type-safe (pas de cast)
- Null-safe (pas de NPE)
- Plus concis que findViewById()

### 3. Lambda Expressions
```kotlin
// Java
button.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        // code
    }
});

// Kotlin
button.setOnClickListener {
    // code
}
```

### 4. String Templates
```kotlin
// Java
String message = "Téléphone: " + phone;

// Kotlin
val message = "Téléphone: $phone"
```

## 🔧 Comment Utiliser ce Code

### Étape 1: Créer un Nouveau Projet
1. Android Studio > New Project > Empty Activity
2. Nom: **ContactsApp**
3. Language: **Kotlin**
4. Minimum SDK: API 24

### Étape 2: Activer View Binding
Dans `build.gradle.kts` (Module: app):
```kotlin
android {
    buildFeatures {
        viewBinding = true
    }
}
```

### Étape 3: Copier les Fichiers
- Copier tous les fichiers `.kt` de ce dossier vers votre projet
- Copier les fichiers XML vers `res/layout/`
- Mettre à jour `AndroidManifest.xml`

### Étape 4: Synchroniser et Lancer
- Sync Gradle
- Run (Shift + F10)

## 📝 Exercices Complémentaires

### Exercice 1: Ajouter un Contact ⭐
Créer `AddContactActivity` pour:
- Ajouter un formulaire avec EditText
- Valider les champs
- Retourner le nouveau contact à MainActivity

### Exercice 2: Supprimer un Contact ⭐⭐
Dans `ContactDetailActivity`:
- Ajouter un bouton "Supprimer"
- Afficher une AlertDialog de confirmation
- Retourner à MainActivity et actualiser la liste

### Exercice 3: Recherche de Contacts ⭐⭐⭐
Dans `MainActivity`:
- Ajouter une SearchView
- Filtrer les contacts en temps réel
- Afficher "Aucun résultat" si vide

## 🆚 Comparaison Java vs Kotlin

| Aspect | Java | Kotlin |
|--------|------|--------|
| **Verbosité** | Élevée | Faible (-40% de code) |
| **Null Safety** | Manuelle | Native |
| **Lambdas** | Java 8+ | Natif et concis |
| **Extensions** | Non | Oui |
| **Data Classes** | Boilerplate | Automatique |
| **Smart Cast** | Cast manuel | Automatique |

## 📖 Code Source Complet

Tous les fichiers sources sont disponibles dans ce dossier:
- `model/Contact.kt`
- `adapter/ContactAdapter.kt`
- `MainActivity.kt`
- `ContactDetailActivity.kt`
- `layouts/` (fichiers XML)

## 🔗 Ressources

- [Kotlin Documentation](https://kotlinlang.org/docs/home.html)
- [Android Developers - Kotlin](https://developer.android.com/kotlin)
- [View Binding Guide](https://developer.android.com/topic/libraries/view-binding)

---

**Prochaine séance**: Coroutines et programmation asynchrone
