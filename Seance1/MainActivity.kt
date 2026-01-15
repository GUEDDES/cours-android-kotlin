package com.example.contactsapp

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.contactsapp.adapter.ContactAdapter
import com.example.contactsapp.databinding.ActivityMainBinding
import com.example.contactsapp.model.Contact

/**
 * Activity principale de l'application
 * Affiche la liste des contacts dans un RecyclerView
 */
class MainActivity : AppCompatActivity() {

    // lateinit = initialisation différée (sera initialisé dans onCreate)
    private lateinit var binding: ActivityMainBinding
    private lateinit var contacts: MutableList<Contact>
    private lateinit var adapter: ContactAdapter

    /**
     * Méthode appelée à la création de l'Activity
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // Initialisation du View Binding
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)  // root = vue racine du layout

        // Initialiser les données de test
        contacts = Contact.getSampleContacts()

        // Configurer le RecyclerView
        setupRecyclerView()

        // Configurer le bouton d'ajout (FloatingActionButton)
        binding.addButton.setOnClickListener {
            // Lambda concise pour le click listener
            showToast("Fonctionnalité à implémenter dans l'exercice 1")
            // TODO: Naviguer vers AddContactActivity
        }
    }

    /**
     * Configure le RecyclerView avec l'adapter et le layout manager
     */
    private fun setupRecyclerView() {
        // Créer l'adapter avec une lambda pour gérer les clics
        adapter = ContactAdapter(contacts) { contact ->
            // Cette lambda sera appelée lors du clic sur un contact
            navigateToDetails(contact)
        }
        
        // apply = fonction de scope pour configuration
        binding.contactsRecyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = this@MainActivity.adapter
        }
    }

    /**
     * Navigue vers l'écran de détails d'un contact
     * @param contact Le contact à afficher
     */
    private fun navigateToDetails(contact: Contact) {
        // Créer un Intent avec apply pour configuration
        val intent = Intent(this, ContactDetailActivity::class.java).apply {
            // Passer les données du contact
            putExtra("CONTACT_ID", contact.id)
            putExtra("CONTACT_NAME", contact.name)
            putExtra("CONTACT_PHONE", contact.phone)
            putExtra("CONTACT_EMAIL", contact.email)
        }
        startActivity(intent)
    }

    /**
     * Extension function personnalisée pour afficher un Toast
     * Note: On pourrait aussi créer une vraie extension de Context
     */
    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}

/*
 * Points clés du code:
 * 
 * 1. View Binding remplace findViewById() - type-safe et null-safe
 * 2. lateinit permet d'initialiser les variables plus tard
 * 3. Les lambdas rendent le code plus concis
 * 4. apply{} permet de configurer des objets facilement
 * 5. String templates: "Message: $variable"
 * 6. this@MainActivity désambiguïse le contexte dans les lambdas
 */
