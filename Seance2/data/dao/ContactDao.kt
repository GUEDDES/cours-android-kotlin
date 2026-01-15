package com.example.contactsapp.data.dao

import androidx.room.*
import com.example.contactsapp.data.model.Contact
import kotlinx.coroutines.flow.Flow

/**
 * DAO (Data Access Object) pour les opérations sur la table contacts
 * 
 * Toutes les fonctions suspend s'exécutent sur un thread en arrière-plan
 * Les fonctions retournant Flow observent les changements en temps réel
 */
@Dao
interface ContactDao {
    
    /**
     * Insère un nouveau contact
     * Si un contact avec le même ID existe, il est remplacé
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(contact: Contact)
    
    /**
     * Met à jour un contact existant
     */
    @Update
    suspend fun update(contact: Contact)
    
    /**
     * Supprime un contact
     */
    @Delete
    suspend fun delete(contact: Contact)
    
    /**
     * Récupère tous les contacts triés par date de création (plus récent en premier)
     * Retourne un Flow qui émet automatiquement quand les données changent
     */
    @Query("SELECT * FROM contacts ORDER BY createdAt DESC")
    fun getAllContacts(): Flow<List<Contact>>
    
    /**
     * Récupère un contact par son ID
     */
    @Query("SELECT * FROM contacts WHERE id = :contactId")
    suspend fun getContactById(contactId: Int): Contact?
    
    /**
     * Recherche des contacts par nom
     * Utilise LIKE pour une recherche partielle (insensible à la casse)
     */
    @Query("SELECT * FROM contacts WHERE name LIKE '%' || :query || '%' ORDER BY name ASC")
    fun searchContacts(query: String): Flow<List<Contact>>
    
    /**
     * Supprime tous les contacts
     */
    @Query("DELETE FROM contacts")
    suspend fun deleteAllContacts()
    
    /**
     * Compte le nombre total de contacts
     */
    @Query("SELECT COUNT(*) FROM contacts")
    fun getContactCount(): Flow<Int>
}
