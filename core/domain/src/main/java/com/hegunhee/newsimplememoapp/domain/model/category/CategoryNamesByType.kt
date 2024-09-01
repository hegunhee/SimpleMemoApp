package com.hegunhee.newsimplememoapp.domain.model.category

data class CategoryNamesByType(
    val type : CategoryType,
    val names : List<String>
)
