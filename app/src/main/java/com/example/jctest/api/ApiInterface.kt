package com.example.jctest.api

import com.example.jctest.models.User
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiInterface {

    @GET("posts/{ID}")
    suspend fun getUserById(@Path("ID") id: Int): Response<User>

    @GET("posts")
    suspend fun getAllUsers(): Response<List<User>>

}