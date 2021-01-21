package com.joker.lpgo.data.api

import com.joker.lpgo.data.model.User
import retrofit2.Response

interface ApiHelper {

    suspend fun getUsers(): Response<List<User>>

}
