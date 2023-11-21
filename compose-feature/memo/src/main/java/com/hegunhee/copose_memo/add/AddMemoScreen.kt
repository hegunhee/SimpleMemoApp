package com.hegunhee.copose_memo.add

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.hegunhee.newsimplememoapp.core.ui.DetailMemoScreen
import com.hegunhee.newsimplememoapp.core.ui.DetailMemoScreenType

@Composable
fun AddMemoScreenRoot(
    paddingValues : PaddingValues,
    onBackButtonClick : () -> Unit
) {
    val (category, setCategory) = remember{mutableStateOf("지출")}
    val memoScreenType = remember { DetailMemoScreenType.Add(onSaveMemoClick =  { })}
    DetailMemoScreen(
        paddingValues = paddingValues,
        onBackButtonClick = onBackButtonClick,
        category,
        setCategory,
        memoScreenType
    )

}