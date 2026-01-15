package com.example.contactsapp.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.contactsapp.data.dao.ContactDao
import com.example.contactsapp.data.model.Contact

/**
 * Base de données Room de l'application
 * 
 * Utilise le pattern Singleton pour garantir une seule instance
 * 
 * @property contactDao Retourne le DAO pour accéder aux contacts
 */
@Database(
    entities = [Contact::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    
    abstract fun contactDao(): ContactDao
    
    companion object {
        /**
         * Instance unique de la base de données
         * @Volatile assure que la valeur est toujours à jour pour tous les threads
         */
        @Volatile
        private var INSTANCE: AppDatabase? = null
        
        /**
         * Récupère l'instance de la base de données
         * Crée une nouvelle instance si elle n'existe pas encore
         * 
         * @param context Context de l'application
         * @return Instance unique de AppDatabase
         */
        fun getDatabase(context: Context): AppDatabase {
            // Si l'instance existe déjà, la retourner
            return INSTANCE ?: synchronized(this) {
                // Double-check pour éviter les races conditions
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "contacts_database"
                )
                    // En cas de changement de version, détruit et recrée la DB
                    // En production, utiliser des migrations!
                    .fallbackToDestructiveMigration()
                    .build()
                
                INSTANCE = instance
                instance
            }
        }
    }
}
