package com.example.contactsapp.data.repository

import com.example.contactsapp.data.dao.ContactDao
import com.example.contactsapp.data.model.Contact
import kotlinx.coroutines.flow.Flow

/**
 * Repository - Couche d'abstraction entre le ViewModel et la source de données
 * 
 * Avantages:
 * - Centralise l'accès aux données
 * - Peut combiner plusieurs sources (Room + API)
 * - Facilite les tests unitaires
 * - Le ViewModel ne sait pas d'où viennent les données
 */
class ContactRepository(private val contactDao: ContactDao) {
    
    /**
     * Observer tous les contacts
     * Flow = stream de données qui se met à jour automatiquement
     */
    val allContacts: Flow<List<Contact>> = contactDao.getAllContacts()
    
    /**
     * Observer le nombre de contacts
     */
    val contactCount: Flow<Int> = contactDao.getContactCount()
    
    /**
     * Insérer un contact
     * suspend = doit être appelée depuis une coroutine
     */
    suspend fun insert(contact: Contact) {
        contactDao.insert(contact)
    }
    
    /**
     * Mettre à jour un contact
     */
    suspend fun update(contact: Contact) {
        contactDao.update(contact)
    }
    
    /**
     * Supprimer un contact
     */
    suspend fun delete(contact: Contact) {
        contactDao.delete(contact)
    }
    
    /**
     * Rechercher des contacts
     */
    fun searchContacts(query: String): Flow<List<Contact>> {
        return contactDao.searchContacts(query)
    }
    
    /**
     * Obtenir un contact par ID
     */
    suspend fun getContactById(id: Int): Contact? {
        return contactDao.getContactById(id)
    }
    
    /**
     * Supprimer tous les contacts
     */
    suspend fun deleteAllContacts() {
        contactDao.deleteAllContacts()
    }
}