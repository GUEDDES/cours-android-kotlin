package tn.isitcom.sc.s3api.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import tn.isitcom.sc.s3api.data.model.User
import tn.isitcom.sc.s3api.data.repository.UserRepository
import tn.isitcom.sc.s3api.util.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class UserViewModel(private val repository: UserRepository) : ViewModel() {
    
    // StateFlow prive (modifiable)
    private val _usersState = MutableStateFlow<UiState<List<User>>>(UiState.Loading)
    
    // StateFlow public (lecture seule)
    val usersState: StateFlow<UiState<List<User>>> = _usersState.asStateFlow()
    
    init {
        // Charger les utilisateurs au demarrage
        loadUsers()
    }
    
    fun loadUsers() {
        viewModelScope.launch {
            repository.getUsers().collect { state ->
                _usersState.value = state
            }
        }
    }
    
    fun refreshUsers() {
        loadUsers()
    }
}
