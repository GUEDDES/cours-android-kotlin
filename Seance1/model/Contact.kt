package com.example.contactsapp.model

/**
 * Data class représentant un contact
 * 
 * Les data classes en Kotlin génèrent automatiquement:
 * - getters et setters
 * - toString()
 * - equals() et hashCode()
 * - copy()
 * - componentN() pour la déstructuration
 */
data class Contact(
    val id: Int,
    val name: String,
    val phone: String,
    val email: String
) {
    /**
     * Companion object = équivalent des méthodes static en Java
     * Utilisé ici pour fournir des données de test
     */
    companion object {
        /**
         * Retourne une liste de contacts d'exemple pour les tests
         * @return MutableList de contacts
         */
        fun getSampleContacts(): MutableList<Contact> {
            return mutableListOf(
                Contact(
                    id = 1,
                    name = "Ahmed Ben Ali",
                    phone = "98765432",
                    email = "ahmed@email.com"
                ),
                Contact(
                    id = 2,
                    name = "Fatma Trabelsi",
                    phone = "97654321",
                    email = "fatma@email.com"
                ),
                Contact(
                    id = 3,
                    name = "Mohamed Gharbi",
                    phone = "96543210",
                    email = "mohamed@email.com"
                ),
                Contact(
                    id = 4,
                    name = "Salma Hammami",
                    phone = "95432109",
                    email = "salma@email.com"
                ),
                Contact(
                    id = 5,
                    name = "Youssef Jebali",
                    phone = "94321098",
                    email = "youssef@email.com"
                )
            )
        }
    }
}
