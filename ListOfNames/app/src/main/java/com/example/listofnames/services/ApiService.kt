package com.example.listofnames.services

import com.example.listofnames.data.DataNames
import retrofit2.Call
import retrofit2.http.GET

interface ApiService {

    @GET("all")
    fun getAll(): Call<List<DataNames>>
}