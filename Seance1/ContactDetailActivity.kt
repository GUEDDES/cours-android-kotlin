package com.example.contactsapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.contactsapp.databinding.ActivityContactDetailBinding

/**
 * Activity affichant les détails d'un contact
 * Reçoit les données via Intent depuis MainActivity
 */
class ContactDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityContactDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityContactDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Récupérer les données de l'Intent
        // Elvis operator (?:) fournit une valeur par défaut si null
        val contactId = intent.getIntExtra("CONTACT_ID", -1)
        val name = intent.getStringExtra("CONTACT_NAME") ?: "N/A"
        val phone = intent.getStringExtra("CONTACT_PHONE") ?: "N/A"
        val email = intent.getStringExtra("CONTACT_EMAIL") ?: "N/A"

        // Afficher les détails
        displayContactDetails(name, phone, email)

        // Bouton retour
        binding.backButton.setOnClickListener {
            finish()  // Ferme l'Activity et retourne à MainActivity
        }
    }

    /**
     * Affiche les informations du contact dans les TextViews
     */
    private fun displayContactDetails(name: String, phone: String, email: String) {
        binding.apply {
            nameTextView.text = name
            // String template avec expression
            phoneTextView.text = "Téléphone: $phone"
            emailTextView.text = "Email: $email"
        }
    }
}

/*
 * Démonstration du Null Safety en Kotlin:
 * 
 * getStringExtra() retourne String? (nullable)
 * ?: "N/A" fournit une valeur par défaut si null (Elvis operator)
 * 
 * En Java, on devrait faire:
 * String name = intent.getStringExtra("CONTACT_NAME");
 * if (name == null) {
 *     name = "N/A";
 * }
 * 
 * Kotlin: val name = intent.getStringExtra("CONTACT_NAME") ?: "N/A"
 */
