package com.example.contactsapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.contactsapp.data.model.Contact
import com.example.contactsapp.data.repository.ContactRepository
import kotlinx.coroutines.launch

/**
 * ContactViewModel - Gère la logique métier et l'état de l'application
 * 
 * Avantages:
 * - Survit aux changements de configuration (rotation d'écran)
 * - Sépare la logique métier de l'UI
 * - viewModelScope gère automatiquement les coroutines
 */
class ContactViewModel(private val repository: ContactRepository) : ViewModel() {
    
    /**
     * Observer la liste des contacts
     * asLiveData() convertit Flow en LiveData pour l'observer depuis l'Activity
     */
    val allContacts = repository.allContacts.asLiveData()
    
    /**
     * Observer le nombre de contacts
     */
    val contactCount = repository.contactCount.asLiveData()
    
    /**
     * Insérer un contact
     * viewModelScope.launch = lancer une coroutine liée au cycle de vie du ViewModel
     * La coroutine est annulée automatiquement quand le ViewModel est détruit
     */
    fun insert(contact: Contact) {
        viewModelScope.launch {
            repository.insert(contact)
        }
    }
    
    /**
     * Mettre à jour un contact
     */
    fun update(contact: Contact) {
        viewModelScope.launch {
            repository.update(contact)
        }
    }
    
    /**
     * Supprimer un contact
     */
    fun delete(contact: Contact) {
        viewModelScope.launch {
            repository.delete(contact)
        }
    }
    
    /**
     * Rechercher des contacts
     */
    fun searchContacts(query: String) = repository.searchContacts(query).asLiveData()
    
    /**
     * Supprimer tous les contacts
     */
    fun deleteAllContacts() {
        viewModelScope.launch {
            repository.deleteAllContacts()
        }
    }
}

/**
 * Factory pour créer le ViewModel avec des paramètres
 * 
 * Nécessaire car ViewModel a besoin du Repository en paramètre
 * Android ne peut pas créer directement un ViewModel avec paramètres
 */
class ContactViewModelFactory(private val repository: ContactRepository) : 
    ViewModelProvider.Factory {
    
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ContactViewModel::class.java)) {
            return ContactViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}