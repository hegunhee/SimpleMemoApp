package com.hegunhee.newsimplememoapp.core.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailMemoScreen(
    paddingValues: PaddingValues,
    onBackButtonClick : () -> Unit,
    category : String,
    onCategoryClick : (String) -> Unit,
    memoScreenType : DetailMemoScreenType
) {
    Scaffold(
        modifier = Modifier
            .padding(start = 10.dp, top = 10.dp)
            .padding(paddingValues),
        topBar =  {
            DetailMemoTopBar(onBackButtonClick,category)
        }
    ) { padding ->
        Column(modifier = Modifier.padding(padding)) {
            CategorySelector(
                selectedCategory = category,
                onCategoryClick = onCategoryClick
            )
        }

        when(memoScreenType) {
            is DetailMemoScreenType.Add -> {

            }
            is DetailMemoScreenType.Detail -> {

            }
        }
    }
}

sealed class DetailMemoScreenType {

    data class Add(
        val onSaveMemoClick : () -> Unit
    ) : DetailMemoScreenType()

    data class Detail(
        val onUpdateMemoClick : () -> Unit,
        val onDeleteMemoClick : () -> Unit
    ) : DetailMemoScreenType()
}