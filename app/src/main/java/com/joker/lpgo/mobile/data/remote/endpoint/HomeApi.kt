package com.joker.lpgo.mobile.data.remote.endpoint

import com.joker.lpgo.mobile.data.remote.response.CategoryResponse
import com.joker.lpgo.mobile.data.remote.response.NearbyResponse
import com.joker.lpgo.mobile.data.remote.response.ProductDetailResponse
import com.joker.lpgo.mobile.data.remote.response.RecommendedResponse
import io.reactivex.Observable
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface HomeApi {

    @GET("api/v1/products")
    fun getNearBy(
    ): Observable<Response<NearbyResponse>>

    @GET("api/v1/products")
    fun getRecommended(
    ): Observable<Response<RecommendedResponse>>

    @GET("api/v1/categories")
    fun getCategories(
    ): Observable<Response<CategoryResponse>>

    @GET("api/v1/products/{uuid}")
    fun getDetailProduct(
        @Path("uuid") uuid: String
    ): Observable<Response<ProductDetailResponse>>

}