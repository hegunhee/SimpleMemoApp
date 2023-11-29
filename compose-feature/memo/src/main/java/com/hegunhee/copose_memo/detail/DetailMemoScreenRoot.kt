package com.hegunhee.copose_memo.detail

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.hegunhee.newsimplememoapp.core.ui.DetailMemoScreenType

@Composable
fun DetailMemoScreenRoot(
    paddingValues : PaddingValues,
    onBackButtonClick : () -> Unit,
    memoId : Int
) {
    val (category, setCategory) = remember{ mutableStateOf("지출") }
    val memoScreenType = remember {
        DetailMemoScreenType.Detail(
            onUpdateMemoClick = {},
            onDeleteMemoClick = {}
        )
    }
//    DetailMemoScreen(
//        paddingValues = paddingValues,
//        onBackButtonClick = onBackButtonClick,
//        category,
//        setCategory,
//        {}
//        memoScreenType,
//    )

}
