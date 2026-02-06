package tn.isitcom.sc.s3api.data.repository

import tn.isitcom.sc.s3api.data.api.ApiService
import tn.isitcom.sc.s3api.data.model.User
import tn.isitcom.sc.s3api.util.UiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class UserRepository(private val apiService: ApiService) {
    
    fun getUsers(): Flow<UiState<List<User>>> = flow {
        try {
            // Emettre l'etat Loading
            emit(UiState.Loading)
            
            // Faire la requete API
            val response = apiService.getUsers()
            
            if (response.isSuccessful && response.body() != null) {
                // Succes: emettre les donnees
                emit(UiState.Success(response.body()!!))
            } else {
                // Erreur HTTP (404, 500, etc.)
                emit(UiState.Error("Erreur ${response.code()}: ${response.message()}"))
            }
        } catch (e: Exception) {
            // Erreur reseau (pas de connexion, timeout, etc.)
            emit(UiState.Error("Erreur reseau: ${e.localizedMessage}"))
        }
    }.flowOn(Dispatchers.IO)
    
    fun getUserById(userId: Int): Flow<UiState<User>> = flow {
        try {
            emit(UiState.Loading)
            
            val response = apiService.getUserById(userId)
            
            if (response.isSuccessful && response.body() != null) {
                emit(UiState.Success(response.body()!!))
            } else {
                emit(UiState.Error("Utilisateur non trouve"))
            }
        } catch (e: Exception) {
            emit(UiState.Error("Erreur: ${e.localizedMessage}"))
        }
    }.flowOn(Dispatchers.IO)
}
