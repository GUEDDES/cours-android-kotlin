package com.example.contactsapp.data.dao

import androidx.room.*
import com.example.contactsapp.data.model.Contact
import kotlinx.coroutines.flow.Flow

/**
 * DAO (Data Access Object) - Interface pour accéder à la base de données
 * 
 * Toutes les opérations sont suspend (asynchrones) ou retournent un Flow
 */
@Dao
interface ContactDao {
    
    /**
     * Insérer un contact
     * @Insert avec onConflict = REPLACE pour remplacer si existe déjà
     * suspend = fonction asynchrone (doit être appelée depuis une coroutine)
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(contact: Contact)
    
    /**
     * Mettre à jour un contact
     */
    @Update
    suspend fun update(contact: Contact)
    
    /**
     * Supprimer un contact
     */
    @Delete
    suspend fun delete(contact: Contact)
    
    /**
     * Récupérer tous les contacts
     * Flow = Observable qui émet automatiquement les changements
     * Tri par date de création décroissante
     */
    @Query("SELECT * FROM contacts ORDER BY createdAt DESC")
    fun getAllContacts(): Flow<List<Contact>>
    
    /**
     * Récupérer un contact par son ID
     */
    @Query("SELECT * FROM contacts WHERE id = :contactId")
    suspend fun getContactById(contactId: Int): Contact?
    
    /**
     * Rechercher des contacts par nom
     * LIKE '%' || :query || '%' = contient le texte
     */
    @Query("SELECT * FROM contacts WHERE name LIKE '%' || :query || '%' ORDER BY createdAt DESC")
    fun searchContacts(query: String): Flow<List<Contact>>
    
    /**
     * Supprimer tous les contacts
     */
    @Query("DELETE FROM contacts")
    suspend fun deleteAllContacts()
    
    /**
     * Compter le nombre de contacts
     */
    @Query("SELECT COUNT(*) FROM contacts")
    fun getContactCount(): Flow<Int>
}