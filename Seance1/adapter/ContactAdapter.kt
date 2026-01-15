package com.example.contactsapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.contactsapp.databinding.ItemContactBinding
import com.example.contactsapp.model.Contact

/**
 * Adapter pour afficher une liste de contacts dans un RecyclerView
 * 
 * @param contacts Liste des contacts à afficher
 * @param onContactClick Lambda appelée lors du clic sur un contact
 */
class ContactAdapter(
    private val contacts: List<Contact>,
    private val onContactClick: (Contact) -> Unit  // Lambda = fonction passée en paramètre
) : RecyclerView.Adapter<ContactAdapter.ContactViewHolder>() {

    /**
     * ViewHolder qui contient les vues d'un item
     * Utilise View Binding pour accéder aux vues de manière type-safe
     */
    class ContactViewHolder(val binding: ItemContactBinding) : 
        RecyclerView.ViewHolder(binding.root)

    /**
     * Crée un nouveau ViewHolder quand le RecyclerView en a besoin
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        // Inflate le layout avec View Binding
        val binding = ItemContactBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ContactViewHolder(binding)
    }

    /**
     * Lie les données d'un contact aux vues du ViewHolder
     * Appelé pour chaque item visible à l'écran
     */
    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        val contact = contacts[position]
        
        // apply = fonction de scope pour configurer plusieurs propriétés
        holder.binding.apply {
            nameTextView.text = contact.name
            phoneTextView.text = contact.phone
            
            // Gestion du clic avec lambda
            root.setOnClickListener {
                onContactClick(contact)  // Appel de la lambda passée en paramètre
            }
        }
    }

    /**
     * Retourne le nombre total d'items
     */
    override fun getItemCount() = contacts.size  // Expression function
}
