package com.example.jctest.api

import com.example.jctest.models.User
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.PATCH
import retrofit2.http.PUT
import retrofit2.http.Path

interface ApiInterface {

@PUT("posts/{id}")
suspend fun updateUserByPut(@Path("id") id: Int, @Body user: User): Response<User>

@PATCH("posts/{id}")
suspend fun updateUserByPatch(@Path("id") id: Int, @Body user: User): Response<User>




}