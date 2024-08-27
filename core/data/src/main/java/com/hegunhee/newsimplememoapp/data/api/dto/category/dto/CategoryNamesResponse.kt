package com.hegunhee.newsimplememoapp.data.api.dto.category.dto

import com.hegunhee.newsimplememoapp.domain.model.category.CategoryType
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CategoryNamesResponse(
    @SerialName("type") val type : CategoryType,
    @SerialName("names") val names : List<String>
)