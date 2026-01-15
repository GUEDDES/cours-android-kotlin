package com.example.contactsapp.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.contactsapp.data.database.AppDatabase
import com.example.contactsapp.data.repository.ContactRepository
import com.example.contactsapp.databinding.ActivityMainBinding
import com.example.contactsapp.ui.adapter.ContactAdapter
import com.example.contactsapp.ui.viewmodel.ContactViewModel
import com.example.contactsapp.ui.viewmodel.ContactViewModelFactory
import com.example.contactsapp.util.PreferencesManager
import com.google.android.material.snackbar.Snackbar

/**
 * MainActivity - Activity principale qui affiche la liste des contacts
 * 
 * Utilise:
 * - ViewBinding pour accéder aux vues
 * - ViewModel pour gérer les données
 * - LiveData pour observer les changements
 * - RecyclerView pour afficher la liste
 */
class MainActivity : AppCompatActivity() {

    // ViewBinding - Généré automatiquement depuis activity_main.xml
    private lateinit var binding: ActivityMainBinding
    
    // Adapter pour le RecyclerView
    private lateinit var adapter: ContactAdapter
    
    // PreferencesManager pour les préférences
    private lateinit var preferencesManager: PreferencesManager
    
    /**
     * by viewModels - Délégué de propriété Kotlin
     * Crée automatiquement le ViewModel avec la Factory
     * Le ViewModel survit aux changements de configuration (rotation)
     */
    private val viewModel: ContactViewModel by viewModels {
        // Initialiser la base de données et le repository
        val database = AppDatabase.getDatabase(applicationContext)
        val repository = ContactRepository(database.contactDao())
        ContactViewModelFactory(repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // Initialiser ViewBinding
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        // Initialiser PreferencesManager
        preferencesManager = PreferencesManager(this)
        
        // Configurer la Toolbar
        setSupportActionBar(binding.toolbar)
        
        // Configurer le RecyclerView
        setupRecyclerView()
        
        // Observer les contacts
        observeContacts()
        
        // Observer le compteur
        observeContactCount()
        
        // Configurer le bouton FAB
        setupFab()
        
        // Message de bienvenue (premier lancement)
        if (preferencesManager.isFirstLaunch) {
            showWelcomeMessage()
            preferencesManager.isFirstLaunch = false
        }
    }

    /**
     * Configurer le RecyclerView et son Adapter
     */
    private fun setupRecyclerView() {
        // Créer l'adapter avec les callbacks
        adapter = ContactAdapter(
            // Callback quand on clique sur un contact
            onContactClick = { contact ->
                // Ouvrir l'activity de détail
                val intent = Intent(this, ContactDetailActivity::class.java).apply {
                    putExtra("CONTACT_ID", contact.id)
                }
                startActivity(intent)
            },
            // Callback quand on clique sur supprimer
            onDeleteClick = { contact ->
                // Supprimer le contact via le ViewModel
                viewModel.delete(contact)
                
                // Afficher un Snackbar de confirmation
                Snackbar.make(
                    binding.root,
                    "${contact.name} supprimé",
                    Snackbar.LENGTH_LONG
                ).show()
            }
        )
        
        // Configurer le RecyclerView
        binding.contactsRecyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = this@MainActivity.adapter
            
            // Optimisations
            setHasFixedSize(true)
        }
    }
    
    /**
     * Observer les changements de la liste de contacts
     * LiveData notifie automatiquement quand les données changent
     */
    private fun observeContacts() {
        viewModel.allContacts.observe(this) { contacts ->
            // Mettre à jour l'adapter avec la nouvelle liste
            adapter.submitList(contacts)
            
            // Afficher/masquer le message "liste vide"
            if (contacts.isEmpty()) {
                binding.emptyTextView.visibility = View.VISIBLE
                binding.contactsRecyclerView.visibility = View.GONE
            } else {
                binding.emptyTextView.visibility = View.GONE
                binding.contactsRecyclerView.visibility = View.VISIBLE
            }
        }
    }
    
    /**
     * Observer le compteur de contacts (optionnel)
     */
    private fun observeContactCount() {
        viewModel.contactCount.observe(this) { count ->
            if (count > 0) {
                binding.contactCountTextView.text = "$count contact${if (count > 1) "s" else ""}"
                binding.contactCountTextView.visibility = View.VISIBLE
            } else {
                binding.contactCountTextView.visibility = View.GONE
            }
        }
    }
    
    /**
     * Configurer le bouton FAB (Floating Action Button)
     */
    private fun setupFab() {
        binding.fabAddContact.setOnClickListener {
            // Ouvrir l'activity pour ajouter un contact
            val intent = Intent(this, AddContactActivity::class.java)
            startActivity(intent)
        }
    }
    
    /**
     * Afficher un message de bienvenue
     */
    private fun showWelcomeMessage() {
        Snackbar.make(
            binding.root,
            "Bienvenue dans Contacts App !",
            Snackbar.LENGTH_LONG
        ).show()
    }
}