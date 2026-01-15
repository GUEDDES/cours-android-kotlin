# Exercices - Séance 2

## 🎯 Objectif

Ces exercices vous permettront de pratiquer les concepts vus dans la Séance 2 :
- SharedPreferences
- Room Database
- Coroutines
- Architecture MVVM

---

## Exercice 1 : Compteur de Contacts ⭐

**Difficulté :** Facile

### Objectif

Afficher le nombre total de contacts dans MainActivity.

### Instructions

1. Ajouter un TextView dans `activity_main.xml` :

```xml
<TextView
    android:id="@+id/counterTextView"
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    android:text="Total: 0 contacts"
    android:textSize="16sp"
    android:layout_margin="16dp" />
```

2. Dans MainActivity, observer la liste et mettre à jour le compteur :

```kotlin
viewModel.allContacts.observe(this) { contacts ->
    adapter.submitList(contacts)
    binding.counterTextView.text = "Total: ${contacts.size} contacts"
}
```

### Résultat Attendu

Le TextView affiche "Total: X contacts" où X est le nombre de contacts.

---

## Exercice 2 : Message de Bienvenue ⭐

**Difficulté :** Facile

### Objectif

Utiliser SharedPreferences pour afficher un message de bienvenue avec le nom de l'utilisateur.

### Instructions

1. Créer une nouvelle Activity `WelcomeActivity` avec un champ de texte et un bouton.

2. Sauvegarder le nom de l'utilisateur dans SharedPreferences :

```kotlin
val prefsManager = PreferencesManager(this)
prefsManager.userName = binding.nameEditText.text.toString()
```

3. Dans MainActivity, afficher le message de bienvenue :

```kotlin
val userName = prefsManager.userName ?: "Invité"
binding.welcomeTextView.text = "Bienvenue $userName !"
```

### Résultat Attendu

L'application affiche "Bienvenue [Nom] !" dans MainActivity.

---

## Exercice 3 : Recherche en Temps Réel ⭐⭐

**Difficulté :** Moyenne

### Objectif

Ajouter une SearchView pour filtrer les contacts en temps réel.

### Instructions

1. Ajouter une SearchView dans `activity_main.xml` :

```xml
<androidx.appcompat.widget.SearchView
    android:id="@+id/searchView"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:queryHint="Rechercher un contact" />
```

2. Dans ViewModel, ajouter une fonction de recherche :

```kotlin
private val _searchQuery = MutableLiveData<String>()
val searchResults = _searchQuery.switchMap { query ->
    if (query.isNullOrBlank()) {
        repository.allContacts.asLiveData()
    } else {
        repository.searchContacts(query).asLiveData()
    }
}

fun search(query: String) {
    _searchQuery.value = query
}
```

3. Dans MainActivity, connecter la SearchView :

```kotlin
binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
    override fun onQueryTextChange(newText: String): Boolean {
        viewModel.search(newText)
        return true
    }
    
    override fun onQueryTextSubmit(query: String): Boolean {
        return false
    }
})

viewModel.searchResults.observe(this) { contacts ->
    adapter.submitList(contacts)
}
```

### Résultat Attendu

La liste de contacts se filtre en temps réel pendant la saisie.

---

## Exercice 4 : Mode Sombre ⭐⭐

**Difficulté :** Moyenne

### Objectif

Implémenter un switch pour activer/désactiver le thème sombre.

### Instructions

1. Ajouter un Switch dans le menu ou dans les paramètres :

```xml
<androidx.appcompat.widget.SwitchCompat
    android:id="@+id/darkModeSwitch"
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    android:text="Mode sombre" />
```

2. Dans MainActivity, initialiser le switch :

```kotlin
val prefsManager = PreferencesManager(this)
binding.darkModeSwitch.isChecked = prefsManager.isDarkModeEnabled

binding.darkModeSwitch.setOnCheckedChangeListener { _, isChecked ->
    prefsManager.isDarkModeEnabled = isChecked
    AppCompatDelegate.setDefaultNightMode(
        if (isChecked) AppCompatDelegate.MODE_NIGHT_YES 
        else AppCompatDelegate.MODE_NIGHT_NO
    )
}
```

3. Dans Application ou MainActivity, appliquer le thème au démarrage :

```kotlin
val prefsManager = PreferencesManager(this)
AppCompatDelegate.setDefaultNightMode(
    if (prefsManager.isDarkModeEnabled) AppCompatDelegate.MODE_NIGHT_YES 
    else AppCompatDelegate.MODE_NIGHT_NO
)
```

### Résultat Attendu

L'application bascule entre thème clair et sombre.

---

## Exercice 5 : Tri des Contacts ⭐⭐

**Difficulté :** Moyenne

### Objectif

Ajouter la possibilité de trier les contacts par nom, date de création, ou téléphone.

### Instructions

1. Dans ContactDao, ajouter des requêtes de tri :

```kotlin
@Query("SELECT * FROM contacts ORDER BY name ASC")
fun getContactsSortedByName(): Flow<List<Contact>>

@Query("SELECT * FROM contacts ORDER BY createdAt DESC")
fun getContactsSortedByDate(): Flow<List<Contact>>

@Query("SELECT * FROM contacts ORDER BY phone ASC")
fun getContactsSortedByPhone(): Flow<List<Contact>>
```

2. Dans Repository, exposer ces méthodes :

```kotlin
fun getContactsSorted(sortBy: SortType): Flow<List<Contact>> {
    return when (sortBy) {
        SortType.NAME -> contactDao.getContactsSortedByName()
        SortType.DATE -> contactDao.getContactsSortedByDate()
        SortType.PHONE -> contactDao.getContactsSortedByPhone()
    }
}

enum class SortType {
    NAME, DATE, PHONE
}
```

3. Dans ViewModel, gérer le tri :

```kotlin
private val _sortType = MutableLiveData(SortType.NAME)
val contacts = _sortType.switchMap { sortType ->
    repository.getContactsSorted(sortType).asLiveData()
}

fun setSortType(sortType: SortType) {
    _sortType.value = sortType
}
```

4. Ajouter un menu pour choisir le tri.

### Résultat Attendu

L'utilisateur peut trier la liste par nom, date ou téléphone.

---

## Exercice 6 : Modifier un Contact ⭐⭐⭐

**Difficulté :** Difficile

### Objectif

Créer une Activity pour modifier un contact existant.

### Instructions

1. Créer `EditContactActivity` (similaire à AddContactActivity).

2. Récupérer le contact à modifier :

```kotlin
val contactId = intent.getIntExtra("CONTACT_ID", -1)
if (contactId != -1) {
    viewModel.getContactById(contactId).observe(this) { contact ->
        binding.nameEditText.setText(contact.name)
        binding.phoneEditText.setText(contact.phone)
        binding.emailEditText.setText(contact.email)
    }
}
```

3. Mettre à jour le contact :

```kotlin
binding.saveButton.setOnClickListener {
    val updatedContact = contact.copy(
        name = binding.nameEditText.text.toString(),
        phone = binding.phoneEditText.text.toString(),
        email = binding.emailEditText.text.toString()
    )
    viewModel.update(updatedContact)
    finish()
}
```

4. Dans ViewModel, ajouter :

```kotlin
fun getContactById(id: Int): LiveData<Contact> {
    return liveData {
        val contact = repository.getContactById(id)
        if (contact != null) {
            emit(contact)
        }
    }
}
```

### Résultat Attendu

L'utilisateur peut modifier un contact existant.

---

## Exercice 7 : Confirmation de Suppression ⭐⭐⭐

**Difficulté :** Difficile

### Objectif

Afficher un dialogue de confirmation avant de supprimer un contact.

### Instructions

1. Dans l'Adapter, modifier le listener de suppression :

```kotlin
deleteButton.setOnClickListener {
    onDeleteClick(contact)
}
```

2. Dans MainActivity, afficher un AlertDialog :

```kotlin
private fun showDeleteConfirmation(contact: Contact) {
    AlertDialog.Builder(this)
        .setTitle("Supprimer le contact")
        .setMessage("Voulez-vous vraiment supprimer ${contact.name} ?")
        .setPositiveButton("Supprimer") { _, _ ->
            viewModel.delete(contact)
            Toast.makeText(this, "Contact supprimé", Toast.LENGTH_SHORT).show()
        }
        .setNegativeButton("Annuler", null)
        .show()
}
```

3. Mettre à jour l'Adapter :

```kotlin
val adapter = ContactAdapter(
    onContactClick = { contact -> /* ... */ },
    onDeleteClick = { contact -> showDeleteConfirmation(contact) }
)
```

### Résultat Attendu

Un dialogue de confirmation apparaît avant la suppression.

---

## Exercice 8 : Statistiques ⭐⭐⭐

**Difficulté :** Difficile

### Objectif

Afficher des statistiques sur les contacts (total, ajoutés aujourd'hui, cette semaine).

### Instructions

1. Dans ContactDao, ajouter des requêtes statistiques :

```kotlin
@Query("SELECT COUNT(*) FROM contacts")
fun getContactCount(): Flow<Int>

@Query("SELECT COUNT(*) FROM contacts WHERE createdAt >= :timestamp")
fun getContactCountSince(timestamp: Long): Flow<Int>
```

2. Dans Repository :

```kotlin
fun getContactCount() = contactDao.getContactCount()

fun getContactsAddedToday(): Flow<Int> {
    val todayStart = Calendar.getInstance().apply {
        set(Calendar.HOUR_OF_DAY, 0)
        set(Calendar.MINUTE, 0)
        set(Calendar.SECOND, 0)
    }.timeInMillis
    return contactDao.getContactCountSince(todayStart)
}
```

3. Afficher dans une nouvelle Activity `StatisticsActivity`.

### Résultat Attendu

L'utilisateur peut voir des statistiques sur ses contacts.

---

## 🎓 Solutions

Les solutions détaillées sont disponibles dans le dossier `solutions/` de ce répertoire.

---

## 💬 Aide

Si vous avez des difficultés :

1. Relisez le README.md
2. Consultez la documentation officielle Android
3. Vérifiez les exemples de code dans les fichiers source
4. N'hésitez pas à demander de l'aide !

---

Bonne chance ! 🚀
