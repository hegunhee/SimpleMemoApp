package com.hegunhee.category

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.hegunhee.newsimplememoapp.domain.model.category.CategoryType
import com.hegunhee.newsimplememoapp.domain.model.category.toCategoryType

@Composable
fun CategoryScreenRoot(
    paddingValues: PaddingValues,
    viewModel : CategoryViewModel = hiltViewModel(),
    onBackButtonClick : () -> Unit,
    categoryId : Int,
) {
    val categoryType = categoryId.toCategoryType()
    if(categoryType == CategoryType.Empty) {
        onBackButtonClick()
    }
    viewModel.fetchCategoryList(categoryId.toCategoryType())
    val uiState = viewModel.uiState.collectAsStateWithLifecycle().value
    val (searchQuery, onValueChanged) = rememberSaveable { mutableStateOf("") }
    when(uiState) {
        is CategoryUiState.Loading -> {

        }
        is CategoryUiState.Success -> {
            CategoryScreen(
                paddingValues = paddingValues,
                categoryType = uiState.categoryType,
                categoryList = uiState.categoryList,
                searchQuery = searchQuery,
                onValueChanged = onValueChanged,
                onBackButtonClick = onBackButtonClick,
                onAddCategoryClick = viewModel::insertCategory,
                onDeleteCategoryClick = viewModel::deleteCategory
            )
        }
    }
}

@Composable
fun CategoryScreen(
    paddingValues: PaddingValues,
    categoryType: CategoryType,
    categoryList : List<String>,
    searchQuery : String,
    onValueChanged : (String) -> Unit,
    onBackButtonClick: () -> Unit,
    onAddCategoryClick : (CategoryType, String) -> Unit,
    onDeleteCategoryClick : (CategoryType, String) -> Unit
) {
    Column(modifier = Modifier
        .padding(10.dp)
        .padding(paddingValues)) {
        Text(text = categoryType.subTitle, fontSize = 40.sp)
        OutlinedTextField(
            value = searchQuery,
            onValueChange = onValueChanged,
            label = { Text("추가할 카테고리를 입력해주세요")},
            singleLine = true,
            maxLines = 1,
            trailingIcon = {
                Icon(
                    painter = painterResource(R.drawable.ic_add),
                    contentDescription = null,
                    modifier = Modifier.clickable {
                        if(searchQuery.isNotBlank()) {
                            onAddCategoryClick(categoryType, searchQuery)
                        }

                    }
                )
            },
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(onDone = {
                if(searchQuery.isNotBlank()) {
                    onAddCategoryClick(categoryType, searchQuery)
                }
            }),
            modifier = Modifier.fillMaxWidth()
        )
        LazyColumn {
            items(categoryList) { category ->
                Category(categoryType = categoryType ,categoryName = category, onDeleteCategoryClick = onDeleteCategoryClick)
            }
        }
    }
}

@Composable
private fun Category(
    categoryType : CategoryType,
    categoryName : String,
    onDeleteCategoryClick: (CategoryType, String) -> Unit
) {
    Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Start) {
        IconButton(onClick = { onDeleteCategoryClick(categoryType, categoryName) },modifier = Modifier.weight(0.2f)) {
            Icon(painter = painterResource(id = R.drawable.ic_remove_circle_24), contentDescription = null,tint = Color.Red)
        }
        Text(text = categoryName,modifier = Modifier.weight(0.8f), fontSize = 20.sp)
    }
}