package com.example.contactsapp.data.repository

import com.example.contactsapp.data.dao.ContactDao
import com.example.contactsapp.data.model.Contact
import kotlinx.coroutines.flow.Flow

/**
 * Repository pour centraliser l'accès aux données
 * 
 * Avantages:
 * - Abstraction: le ViewModel ne sait pas d'où viennent les données
 * - Peut combiner plusieurs sources (Room + API + Cache)
 * - Facilite les tests unitaires
 * 
 * @property contactDao DAO pour accéder à la base de données Room
 */
class ContactRepository(private val contactDao: ContactDao) {
    
    /**
     * Flux observable de tous les contacts
     * Se met à jour automatiquement quand les données changent
     */
    val allContacts: Flow<List<Contact>> = contactDao.getAllContacts()
    
    /**
     * Insère un nouveau contact dans la base de données
     * 
     * @param contact Contact à insérer
     */
    suspend fun insert(contact: Contact) {
        contactDao.insert(contact)
    }
    
    /**
     * Met à jour un contact existant
     * 
     * @param contact Contact à mettre à jour
     */
    suspend fun update(contact: Contact) {
        contactDao.update(contact)
    }
    
    /**
     * Supprime un contact
     * 
     * @param contact Contact à supprimer
     */
    suspend fun delete(contact: Contact) {
        contactDao.delete(contact)
    }
    
    /**
     * Recherche des contacts par nom
     * 
     * @param query Texte de recherche
     * @return Flow de la liste des contacts correspondants
     */
    fun searchContacts(query: String): Flow<List<Contact>> {
        return contactDao.searchContacts(query)
    }
    
    /**
     * Récupère un contact par son ID
     * 
     * @param id ID du contact
     * @return Contact ou null si non trouvé
     */
    suspend fun getContactById(id: Int): Contact? {
        return contactDao.getContactById(id)
    }
    
    /**
     * Compte le nombre total de contacts
     * 
     * @return Flow du nombre de contacts
     */
    fun getContactCount(): Flow<Int> {
        return contactDao.getContactCount()
    }
}
