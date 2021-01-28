package com.joker.lpgo.data.api

import com.joker.lpgo.data.api.response.ProductListResponse
import com.joker.lpgo.data.model.User
import javax.inject.Inject
import retrofit2.Response

class ApiHelperImpl @Inject constructor(private val apiService: ApiService) : ApiHelper {

    override suspend fun getUsers(): Response<List<User>> = apiService.getUsers()

    // Product
    override suspend fun getProducts(): Response<ProductListResponse> = apiService.getProductList()

}
