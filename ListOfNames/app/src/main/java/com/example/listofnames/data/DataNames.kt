package com.example.listofnames.data

import com.google.gson.annotations.SerializedName

data class DataNames(
    @SerializedName("id")
    val id: Int,

    @SerializedName("name")
    val name: String
)
