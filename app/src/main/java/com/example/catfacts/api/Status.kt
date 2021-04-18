package com.example.catfacts.api


import com.google.gson.annotations.SerializedName

data class Status(
    @SerializedName("sentCount")
    val sentCount: Int,
    @SerializedName("verified")
    val verified: Boolean
)