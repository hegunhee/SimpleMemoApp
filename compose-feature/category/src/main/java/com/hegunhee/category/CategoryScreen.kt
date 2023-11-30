package com.hegunhee.category

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.hegunhee.newsimplememoapp.domain.model.toCategoryType

@Composable
fun CategoryScreenRoot(
    paddingValues: PaddingValues,
    onBackButtonClick : () -> Unit,
    categoryId : Int
) {
    if(categoryId == -1) {
        onBackButtonClick()
    }
    Text(categoryId.toCategoryType().toString())
}

@Composable
fun CategoryScreen(
    paddingValues: PaddingValues
) {
    Text(text = "Category Screen")
}