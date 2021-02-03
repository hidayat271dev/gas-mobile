package com.joker.lpgo.mobile.data.remote.endpoint

import com.joker.lpgo.mobile.data.remote.response.*
import io.reactivex.Observable
import retrofit2.Response
import retrofit2.http.*

interface AddressApi {

    @GET("api/v1/user_address")
    fun getAddress(): Observable<Response<AddressListResponse>>

    @FormUrlEncoded
    @POST("api/v1/user_address/{uuid}")
    fun updatePrimaryAddress(
            @Field("is_primary") is_primary: Int = 1,
            @Path("uuid") uuid: String,
    ): Observable<Response<AddressDetailResponse>>

    @FormUrlEncoded
    @POST("api/v1/user_address")
    fun saveAddress(
            @Field("name") name: String,
            @Field("lat") lat: Double,
            @Field("long") long: Double,
            @Field("address_meta") addressMeta: String
    ): Observable<Response<AddressDetailResponse>>

    @DELETE("api/v1/user_address/{uuid}")
    fun deleteAddress(
        @Path("uuid") uuid: String
    ): Observable<Response<AddressDetailResponse>>

}