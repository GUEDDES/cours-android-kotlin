package com.example.contactsapp.util

import android.content.Context
import android.content.SharedPreferences

/**
 * PreferencesManager - Gère les préférences de l'application avec SharedPreferences
 * 
 * SharedPreferences = stockage clé-valeur pour données simples:
 * - Paramètres de l'app
 * - Token d'authentification
 * - Préférences utilisateur
 */
class PreferencesManager(context: Context) {
    
    /**
     * Instance SharedPreferences
     * MODE_PRIVATE = accessible uniquement par cette application
     */
    private val sharedPreferences: SharedPreferences = 
        context.getSharedPreferences("app_preferences", Context.MODE_PRIVATE)
    
    companion object {
        // Clés pour les préférences
        private const val KEY_DARK_MODE = "dark_mode"
        private const val KEY_USER_NAME = "user_name"
        private const val KEY_SORT_ORDER = "sort_order"
        private const val KEY_FIRST_LAUNCH = "first_launch"
    }
    
    /**
     * Propriété pour le mode sombre
     * get() = lecture
     * set() = écriture
     */
    var isDarkModeEnabled: Boolean
        get() = sharedPreferences.getBoolean(KEY_DARK_MODE, false)
        set(value) {
            sharedPreferences.edit().putBoolean(KEY_DARK_MODE, value).apply()
        }
    
    /**
     * Nom d'utilisateur
     */
    var userName: String?
        get() = sharedPreferences.getString(KEY_USER_NAME, null)
        set(value) {
            sharedPreferences.edit().putString(KEY_USER_NAME, value).apply()
        }
    
    /**
     * Ordre de tri ("name" ou "date")
     */
    var sortOrder: String
        get() = sharedPreferences.getString(KEY_SORT_ORDER, "date") ?: "date"
        set(value) {
            sharedPreferences.edit().putString(KEY_SORT_ORDER, value).apply()
        }
    
    /**
     * Vérifier si c'est le premier lancement
     */
    var isFirstLaunch: Boolean
        get() = sharedPreferences.getBoolean(KEY_FIRST_LAUNCH, true)
        set(value) {
            sharedPreferences.edit().putBoolean(KEY_FIRST_LAUNCH, value).apply()
        }
    
    /**
     * Effacer toutes les préférences
     */
    fun clearAll() {
        sharedPreferences.edit().clear().apply()
    }
    
    /**
     * Sauvegarder plusieurs préférences en une fois
     */
    fun saveSettings(darkMode: Boolean, userName: String?, sortOrder: String) {
        sharedPreferences.edit().apply {
            putBoolean(KEY_DARK_MODE, darkMode)
            putString(KEY_USER_NAME, userName)
            putString(KEY_SORT_ORDER, sortOrder)
            apply() // Sauvegarde asynchrone
        }
    }
}