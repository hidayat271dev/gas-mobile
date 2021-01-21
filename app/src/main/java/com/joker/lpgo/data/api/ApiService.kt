package com.joker.lpgo.data.api

import com.joker.lpgo.data.model.User
import retrofit2.Response
import retrofit2.http.*

interface ApiService {

    @GET("users")
    suspend fun getUsers(): Response<List<User>>

}
