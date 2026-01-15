# Exercices Complémentaires - Séance 1

## Exercice 1: Ajouter un Contact

### Objectif
Créer une nouvelle Activity `AddContactActivity` qui permet d'ajouter un nouveau contact à la liste.

### Spécifications

#### Fonctionnalités requises:
- Champs de saisie pour:
  - Nom (EditText)
  - Téléphone (EditText avec inputType="phone")
  - Email (EditText avec inputType="textEmailAddress")
- Bouton "Enregistrer" pour sauvegarder le contact
- Bouton "Annuler" pour revenir en arrière
- Validation des champs (non vides)
- Message d'erreur si validation échoue
- Retour à MainActivity avec le nouveau contact

#### Layout suggéré (activity_add_contact.xml):
```xml
<?xml version="1.0" encoding="utf-8"?>
<ScrollView xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:padding="16dp">

    <LinearLayout
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:orientation="vertical">

        <TextView
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:text="Ajouter un Contact"
            android:textSize="24sp"
            android:textStyle="bold"
            android:layout_marginBottom="24dp" />

        <com.google.android.material.textfield.TextInputLayout
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:layout_marginBottom="16dp">

            <com.google.android.material.textfield.TextInputEditText
                android:id="@+id/nameEditText"
                android:layout_width="match_parent"
                android:layout_height="wrap_content"
                android:hint="Nom complet" />
        </com.google.android.material.textfield.TextInputLayout>

        <com.google.android.material.textfield.TextInputLayout
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:layout_marginBottom="16dp">

            <com.google.android.material.textfield.TextInputEditText
                android:id="@+id/phoneEditText"
                android:layout_width="match_parent"
                android:layout_height="wrap_content"
                android:hint="Numéro de téléphone"
                android:inputType="phone" />
        </com.google.android.material.textfield.TextInputLayout>

        <com.google.android.material.textfield.TextInputLayout
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:layout_marginBottom="24dp">

            <com.google.android.material.textfield.TextInputEditText
                android:id="@+id/emailEditText"
                android:layout_width="match_parent"
                android:layout_height="wrap_content"
                android:hint="Adresse email"
                android:inputType="textEmailAddress" />
        </com.google.android.material.textfield.TextInputLayout>

        <LinearLayout
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:orientation="horizontal"
            android:gravity="end">

            <Button
                android:id="@+id/cancelButton"
                style="@style/Widget.Material3.Button.OutlinedButton"
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:text="Annuler"
                android:layout_marginEnd="8dp" />

            <Button
                android:id="@+id/saveButton"
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:text="Enregistrer" />
        </LinearLayout>

    </LinearLayout>
</ScrollView>
```

#### Code Kotlin suggéré:
```kotlin
class AddContactActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddContactBinding
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddContactBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        binding.saveButton.setOnClickListener {
            saveContact()
        }
        
        binding.cancelButton.setOnClickListener {
            finish()
        }
    }
    
    private fun saveContact() {
        val name = binding.nameEditText.text.toString().trim()
        val phone = binding.phoneEditText.text.toString().trim()
        val email = binding.emailEditText.text.toString().trim()
        
        // Validation
        if (name.isEmpty()) {
            binding.nameEditText.error = "Le nom est requis"
            return
        }
        
        if (phone.isEmpty()) {
            binding.phoneEditText.error = "Le téléphone est requis"
            return
        }
        
        if (email.isEmpty()) {
            binding.emailEditText.error = "L'email est requis"
            return
        }
        
        // Retourner les données à MainActivity
        val resultIntent = Intent().apply {
            putExtra("NAME", name)
            putExtra("PHONE", phone)
            putExtra("EMAIL", email)
        }
        setResult(RESULT_OK, resultIntent)
        finish()
    }
}
```

#### Modification de MainActivity:
```kotlin
private val addContactLauncher = registerForActivityResult(
    ActivityResultContracts.StartActivityForResult()
) { result ->
    if (result.resultCode == RESULT_OK) {
        result.data?.let { data ->
            val name = data.getStringExtra("NAME") ?: return@let
            val phone = data.getStringExtra("PHONE") ?: return@let
            val email = data.getStringExtra("EMAIL") ?: return@let
            
            val newId = contacts.maxOfOrNull { it.id }?.plus(1) ?: 1
            val newContact = Contact(newId, name, phone, email)
            contacts.add(newContact)
            adapter.notifyItemInserted(contacts.size - 1)
            showToast("Contact ajouté avec succès")
        }
    }
}

// Dans onCreate():
binding.addButton.setOnClickListener {
    val intent = Intent(this, AddContactActivity::class.java)
    addContactLauncher.launch(intent)
}
```

---

## Exercice 2: Supprimer un Contact

### Objectif
Ajouter une fonctionnalité de suppression de contact dans `ContactDetailActivity`.

### Spécifications

#### Fonctionnalités requises:
- Bouton "Supprimer" dans ContactDetailActivity
- Boîte de dialogue de confirmation avant suppression
- Suppression du contact de la liste
- Retour à MainActivity avec mise à jour de la liste
- Message de confirmation après suppression

#### Ajout dans le layout (activity_contact_detail.xml):
```xml
<Button
    android:id="@+id/deleteButton"
    style="@style/Widget.Material3.Button.TonalButton"
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    android:text="Supprimer"
    android:backgroundTint="@android:color/holo_red_light"
    android:layout_marginTop="32dp"
    app:layout_constraintTop_toBottomOf="@id/emailTextView"
    app:layout_constraintStart_toStartOf="parent" />
```

#### Code Kotlin suggéré:
```kotlin
class ContactDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityContactDetailBinding
    private var contactId: Int = -1
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityContactDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        contactId = intent.getIntExtra("CONTACT_ID", -1)
        val name = intent.getStringExtra("CONTACT_NAME") ?: "N/A"
        val phone = intent.getStringExtra("CONTACT_PHONE") ?: "N/A"
        val email = intent.getStringExtra("CONTACT_EMAIL") ?: "N/A"
        
        displayContactDetails(name, phone, email)
        
        binding.backButton.setOnClickListener {
            finish()
        }
        
        binding.deleteButton.setOnClickListener {
            showDeleteConfirmationDialog()
        }
    }
    
    private fun showDeleteConfirmationDialog() {
        AlertDialog.Builder(this)
            .setTitle("Supprimer le contact")
            .setMessage("Êtes-vous sûr de vouloir supprimer ce contact ?")
            .setPositiveButton("Supprimer") { _, _ ->
                deleteContact()
            }
            .setNegativeButton("Annuler", null)
            .show()
    }
    
    private fun deleteContact() {
        val resultIntent = Intent().apply {
            putExtra("DELETE_CONTACT_ID", contactId)
        }
        setResult(RESULT_OK, resultIntent)
        Toast.makeText(this, "Contact supprimé", Toast.LENGTH_SHORT).show()
        finish()
    }
    
    private fun displayContactDetails(name: String, phone: String, email: String) {
        binding.apply {
            nameTextView.text = name
            phoneTextView.text = "Téléphone: $phone"
            emailTextView.text = "Email: $email"
        }
    }
}
```

#### Modification de MainActivity:
```kotlin
private val detailLauncher = registerForActivityResult(
    ActivityResultContracts.StartActivityForResult()
) { result ->
    if (result.resultCode == RESULT_OK) {
        result.data?.let { data ->
            val deletedId = data.getIntExtra("DELETE_CONTACT_ID", -1)
            if (deletedId != -1) {
                val position = contacts.indexOfFirst { it.id == deletedId }
                if (position != -1) {
                    contacts.removeAt(position)
                    adapter.notifyItemRemoved(position)
                }
            }
        }
    }
}

private fun navigateToDetails(contact: Contact) {
    val intent = Intent(this, ContactDetailActivity::class.java).apply {
        putExtra("CONTACT_ID", contact.id)
        putExtra("CONTACT_NAME", contact.name)
        putExtra("CONTACT_PHONE", contact.phone)
        putExtra("CONTACT_EMAIL", contact.email)
    }
    detailLauncher.launch(intent)
}
```

---

## Exercice 3: Recherche de Contacts

### Objectif
Ajouter une fonctionnalité de recherche dans la liste des contacts.

### Spécifications

#### Fonctionnalités requises:
- SearchView dans la toolbar ou en haut de la liste
- Filtrage en temps réel pendant la saisie
- Recherche sur le nom, téléphone ou email
- Message "Aucun résultat" si la recherche ne trouve rien
- Possibilité de réinitialiser la recherche

#### Ajout dans le layout (activity_main.xml):
```xml
<androidx.appcompat.widget.SearchView
    android:id="@+id/searchView"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:layout_marginTop="8dp"
    android:queryHint="Rechercher un contact..."
    app:iconifiedByDefault="false"
    app:layout_constraintTop_toBottomOf="@id/titleTextView"
    app:layout_constraintStart_toStartOf="parent"
    app:layout_constraintEnd_toEndOf="parent" />

<TextView
    android:id="@+id/emptyTextView"
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    android:text="Aucun contact trouvé"
    android:textSize="16sp"
    android:textColor="@android:color/darker_gray"
    android:visibility="gone"
    app:layout_constraintTop_toTopOf="@id/contactsRecyclerView"
    app:layout_constraintBottom_toBottomOf="@id/contactsRecyclerView"
    app:layout_constraintStart_toStartOf="parent"
    app:layout_constraintEnd_toEndOf="parent" />
```

#### Modification de ContactAdapter:
```kotlin
class ContactAdapter(
    private var contacts: List<Contact>,
    private val onContactClick: (Contact) -> Unit
) : RecyclerView.Adapter<ContactAdapter.ContactViewHolder>() {

    private var filteredContacts: List<Contact> = contacts

    fun filter(query: String) {
        filteredContacts = if (query.isEmpty()) {
            contacts
        } else {
            contacts.filter {
                it.name.contains(query, ignoreCase = true) ||
                it.phone.contains(query, ignoreCase = true) ||
                it.email.contains(query, ignoreCase = true)
            }
        }
        notifyDataSetChanged()
    }

    fun updateContacts(newContacts: List<Contact>) {
        contacts = newContacts
        filteredContacts = newContacts
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        val contact = filteredContacts[position]
        holder.binding.apply {
            nameTextView.text = contact.name
            phoneTextView.text = contact.phone
            
            root.setOnClickListener {
                onContactClick(contact)
            }
        }
    }

    override fun getItemCount() = filteredContacts.size

    // ... reste du code
}
```

#### Modification de MainActivity:
```kotlin
class MainActivity : AppCompatActivity() {
    // ... variables existantes
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        contacts = Contact.getSampleContacts()
        setupRecyclerView()
        setupSearchView()
        
        binding.addButton.setOnClickListener {
            val intent = Intent(this, AddContactActivity::class.java)
            addContactLauncher.launch(intent)
        }
    }
    
    private fun setupSearchView() {
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }
            
            override fun onQueryTextChange(newText: String?): Boolean {
                adapter.filter(newText ?: "")
                updateEmptyView()
                return true
            }
        })
    }
    
    private fun updateEmptyView() {
        if (adapter.itemCount == 0) {
            binding.emptyTextView.visibility = View.VISIBLE
            binding.contactsRecyclerView.visibility = View.GONE
        } else {
            binding.emptyTextView.visibility = View.GONE
            binding.contactsRecyclerView.visibility = View.VISIBLE
        }
    }
    
    // ... reste du code
}
```

---

## Exercice Bonus: Menu d'Options

### Objectif
Ajouter un menu avec plusieurs options.

### Fonctionnalités:
- Menu "Trier" (par nom, par date d'ajout)
- Menu "À propos" (afficher info sur l'app)
- Menu "Paramètres" (pour exercices futurs)

#### Créer menu_main.xml:
```xml
<?xml version="1.0" encoding="utf-8"?>
<menu xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto">
    
    <item
        android:id="@+id/action_sort"
        android:title="Trier"
        app:showAsAction="never">
        <menu>
            <item
                android:id="@+id/sort_by_name"
                android:title="Par nom" />
            <item
                android:id="@+id/sort_by_date"
                android:title="Par date" />
        </menu>
    </item>
    
    <item
        android:id="@+id/action_about"
        android:title="À propos"
        app:showAsAction="never" />
        
</menu>
```

#### Dans MainActivity:
```kotlin
override fun onCreateOptionsMenu(menu: Menu?): Boolean {
    menuInflater.inflate(R.menu.menu_main, menu)
    return true
}

override fun onOptionsItemSelected(item: MenuItem): Boolean {
    return when (item.itemId) {
        R.id.sort_by_name -> {
            contacts.sortBy { it.name }
            adapter.updateContacts(contacts)
            true
        }
        R.id.sort_by_date -> {
            contacts.sortBy { it.id }
            adapter.updateContacts(contacts)
            true
        }
        R.id.action_about -> {
            showAboutDialog()
            true
        }
        else -> super.onOptionsItemSelected(item)
    }
}

private fun showAboutDialog() {
    AlertDialog.Builder(this)
        .setTitle("À propos")
        .setMessage("Application Contacts\nVersion 1.0\nDéveloppé avec Kotlin")
        .setPositiveButton("OK", null)
        .show()
}
```

---

## Critères d'Évaluation

### Exercice 1 (30 points):
- Layout correct et fonctionnel (10 pts)
- Validation des champs (8 pts)
- Communication entre Activities (8 pts)
- Mise à jour de la liste (4 pts)

### Exercice 2 (30 points):
- Boîte de dialogue de confirmation (10 pts)
- Suppression du contact (10 pts)
- Mise à jour de la liste (8 pts)
- Interface utilisateur (2 pts)

### Exercice 3 (30 points):
- Implémentation SearchView (10 pts)
- Filtrage fonctionnel (12 pts)
- Gestion du cas vide (5 pts)
- Performance (3 pts)

### Exercice Bonus (10 points):
- Menu fonctionnel (5 pts)
- Tri des contacts (5 pts)

**Total: 100 points**