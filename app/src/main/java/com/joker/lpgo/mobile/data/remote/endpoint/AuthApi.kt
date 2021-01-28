package com.joker.lpgo.mobile.data.remote.endpoint

import com.joker.lpgo.mobile.data.remote.response.LoginResponse
import com.joker.lpgo.mobile.data.remote.response.RegisterResponse
import io.reactivex.Observable
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST
import retrofit2.Response
import retrofit2.http.*

interface AuthApi {

    @FormUrlEncoded
    @POST("api/v1/login")
    fun postLogin(
        @Field("username") username: String,
        @Field("password") password: String,
    ): Observable<Response<LoginResponse>>

    @FormUrlEncoded
    @POST("api/v1/register")
    fun postRegister(
        @Field("fullname") fullname: String,
        @Field("email") email: String,
        @Field("phone") phone: String,
        @Field("password") password: String,
    ): Observable<Response<RegisterResponse>>

    @FormUrlEncoded
    @POST("api/v1/forgot")
    fun postForgot(
        @Field("email") email: String,
    ): Observable<Response<Any>>

}