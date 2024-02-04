package com.example.jctest.api

import com.example.jctest.models.User
import retrofit2.Response
import retrofit2.http.DELETE
import retrofit2.http.Path

interface ApiInterface {

@DELETE("posts/{id}")
suspend fun deleteUser(@Path("id") id: Int): Response<User>




}