package com.hegunhee.newsimplememoapp.data.api.dto.category.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CategoryNameResponse(
    @SerialName("name") val name : String
)