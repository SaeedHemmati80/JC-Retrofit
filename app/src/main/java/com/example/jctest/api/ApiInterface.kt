package com.example.jctest.api

import com.example.jctest.models.User
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiInterface {

    @POST("posts")
    suspend fun createUser(@Body user: User): Response<User>


}