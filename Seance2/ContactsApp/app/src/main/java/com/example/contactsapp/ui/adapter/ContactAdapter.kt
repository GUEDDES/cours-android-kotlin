package com.example.contactsapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.contactsapp.data.model.Contact
import com.example.contactsapp.databinding.ItemContactBinding

/**
 * ContactAdapter - Adapter pour afficher la liste des contacts
 * 
 * ListAdapter est plus performant que RecyclerView.Adapter:
 * - Gère automatiquement les changements avec DiffUtil
 * - Pas besoin d'appeler notifyDataSetChanged()
 * - Juste appeler submitList(newList)
 */
class ContactAdapter(
    private val onContactClick: (Contact) -> Unit,
    private val onDeleteClick: (Contact) -> Unit
) : ListAdapter<Contact, ContactAdapter.ContactViewHolder>(ContactDiffCallback()) {

    /**
     * ViewHolder - Contient les vues pour un item
     * ViewBinding évite findViewById()
     */
    class ContactViewHolder(val binding: ItemContactBinding) :
        RecyclerView.ViewHolder(binding.root)

    /**
     * Créer un nouveau ViewHolder
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemContactBinding.inflate(inflater, parent, false)
        return ContactViewHolder(binding)
    }

    /**
     * Lier les données au ViewHolder
     */
    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        val contact = getItem(position)
        
        holder.binding.apply {
            // Afficher le nom
            nameTextView.text = contact.name
            
            // Afficher le téléphone
            phoneTextView.text = contact.phone
            
            // Afficher l'initiale
            val initial = if (contact.name.isNotEmpty()) {
                contact.name.first().uppercase()
            } else {
                "?"
            }
            contactInitialTextView.text = initial
            
            // Click sur l'item
            root.setOnClickListener {
                onContactClick(contact)
            }
            
            // Click sur le bouton supprimer
            deleteButton.setOnClickListener {
                onDeleteClick(contact)
            }
        }
    }
}

/**
 * DiffUtil.ItemCallback - Compare deux contacts pour détecter les changements
 * 
 * Optimise les performances:
 * - Compare uniquement ce qui a changé
 * - Anime automatiquement les changements
 */
class ContactDiffCallback : DiffUtil.ItemCallback<Contact>() {
    
    /**
     * Compare les IDs (même item ?)
     */
    override fun areItemsTheSame(oldItem: Contact, newItem: Contact): Boolean {
        return oldItem.id == newItem.id
    }

    /**
     * Compare le contenu (même données ?)
     * data class génère automatiquement equals()
     */
    override fun areContentsTheSame(oldItem: Contact, newItem: Contact): Boolean {
        return oldItem == newItem
    }
}