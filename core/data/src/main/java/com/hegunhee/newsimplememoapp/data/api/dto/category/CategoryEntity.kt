package com.hegunhee.newsimplememoapp.data.api.dto.category

import com.hegunhee.newsimplememoapp.domain.model.category.CategoryType
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CategoryEntity(
    @SerialName("type") val type: CategoryType,
    @SerialName("name") val name: String
)