package com.example.jctest.api

import com.example.jctest.models.User
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface ApiInterface {


    // 1)
//    @POST("posts")
//    suspend fun createUser(@Body user: User): Response<User>


    // 2)
    @FormUrlEncoded
    @POST("posts")
    suspend fun createUrlUser(
        @Field("userId") userId: Int,
        @Field("title") title: String,
        @Field("body") body: String,

    ): Response<User>


}