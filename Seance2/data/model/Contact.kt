package com.example.contactsapp.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Entity Contact
 * Représente une table "contacts" dans la base de données Room
 * 
 * @property id Clé primaire auto-générée
 * @property name Nom complet du contact
 * @property phone Numéro de téléphone
 * @property email Adresse email
 * @property createdAt Timestamp de création
 */
@Entity(tableName = "contacts")
data class Contact(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    
    val name: String,
    val phone: String,
    val email: String,
    val createdAt: Long = System.currentTimeMillis()
)
