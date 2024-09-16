package com.hegunhee.copose_memo.detail

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.hegunhee.newsimplememoapp.core.ui.DetailMemoScreen
import com.hegunhee.newsimplememoapp.core.ui.DetailMemoScreenType

@Composable
fun DetailMemoScreenRoot(
    paddingValues : PaddingValues,
    viewModel : DetailMemoViewModel = hiltViewModel(),
    onBackButtonClick : () -> Unit,
    onAddCategoryClick : (String) -> Unit,
    memoId : Int
) {
    LaunchedEffect(key1 = viewModel.categoryList) {
        viewModel.setCategoryType(viewModel.categoryType.value)
    }
    LaunchedEffect(key1 = memoId) {
        viewModel.fetchMemo(memoId)
    }
    val memoScreenType = remember {
        DetailMemoScreenType.Detail(
            onUpdateMemoClick = viewModel::updateMemo,
            onDeleteMemoClick = viewModel::deleteMemo
    )}
    DetailMemoScreen(
        paddingValues = paddingValues,
        onBackButtonClick = onBackButtonClick,
        memoForm = viewModel.memoForm.collectAsStateWithLifecycle().value,
        price = viewModel.price.collectAsStateWithLifecycle().value,
        description = viewModel.description.collectAsStateWithLifecycle().value,
        selectedCategoryType = viewModel.categoryType.collectAsStateWithLifecycle().value,
        categoryList = viewModel.categoryList.collectAsStateWithLifecycle().value,
        onIncomeExpenseTypeClick = viewModel::setIncomeExpenseType,
        onSelectDateClick = viewModel::setDate,
        onSelectTimeClick = viewModel::setTime,
        onCategoryClick = viewModel::setCategoryType,
        onCategoryItemClick = viewModel::setSubCategoryItem,
        onAddSubCategoryClick = onAddCategoryClick,
        onPriceValueChanged = viewModel::setPrice,
        onDescriptionValueChanged = viewModel::setDescription,
        memoScreenType = memoScreenType,
    )
}
