package com.milan.jpmorganchase.models


import com.google.gson.annotations.SerializedName

data class AlbumResponseItem(
    @SerializedName("id")
    val id: Int,
    @SerializedName("title")
    val title: String,
    @SerializedName("userId")
    val userId: Int
)