package com.joker.lpgo.mobile.data.remote.endpoint

import com.joker.lpgo.mobile.data.remote.response.CategoryResponse
import io.reactivex.Observable
import retrofit2.Response
import retrofit2.http.GET

interface CategoryApi {

    @GET("api/v1/categories")
    fun getCategories(
    ): Observable<Response<CategoryResponse>>

}