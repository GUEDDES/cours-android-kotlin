package com.example.contactsapp.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Entity Contact - Représente une table dans la base de données Room
 * 
 * @Entity indique que cette classe est une table
 * @PrimaryKey(autoGenerate = true) génère automatiquement l'ID
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