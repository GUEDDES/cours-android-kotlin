package com.example.contactsapp.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.contactsapp.data.dao.ContactDao
import com.example.contactsapp.data.model.Contact

/**
 * AppDatabase - Classe abstraite qui représente la base de données Room
 * 
 * @Database annotation avec:
 * - entities: liste des Entity (tables)
 * - version: numéro de version (incrémenter lors de changements)
 * - exportSchema: pour exporter le schéma SQL
 */
@Database(
    entities = [Contact::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    
    /**
     * Fonction abstraite pour obtenir le DAO
     */
    abstract fun contactDao(): ContactDao
    
    companion object {
        /**
         * @Volatile = visible par tous les threads
         * INSTANCE unique de la base de données (Singleton Pattern)
         */
        @Volatile
        private var INSTANCE: AppDatabase? = null
        
        /**
         * Récupérer ou créer l'instance de la base de données
         * synchronized = un seul thread à la fois
         */
        fun getDatabase(context: Context): AppDatabase {
            // Retourner l'instance si elle existe déjà
            return INSTANCE ?: synchronized(this) {
                // Créer la base de données
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "contacts_database"
                )
                    // En cas de changement de version, supprimer et recréer
                    .fallbackToDestructiveMigration()
                    .build()
                
                INSTANCE = instance
                instance
            }
        }
    }
}