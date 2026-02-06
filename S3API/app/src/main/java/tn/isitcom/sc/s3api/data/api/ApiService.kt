package tn.isitcom.sc.s3api.data.api

import tn.isitcom.sc.s3api.data.model.User
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    
    // GET tous les utilisateurs
    @GET("users")
    suspend fun getUsers(): Response<List<User>>
    
    // GET un utilisateur par ID
    @GET("users/{id}")
    suspend fun getUserById(@Path("id") userId: Int): Response<User>
    
    companion object {
        const val BASE_URL = "https://jsonplaceholder.typicode.com/"
    }
}
