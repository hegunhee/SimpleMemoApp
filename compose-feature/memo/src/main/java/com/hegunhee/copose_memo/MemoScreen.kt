package com.hegunhee.copose_memo

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun MemoScreenRoot(
    paddingValues: PaddingValues
) {
    MemoScreen(paddingValues = paddingValues)
    Text(text = "Text 화면입니다.")
}

@Composable
fun MemoScreen(
    paddingValues: PaddingValues
) {
    
}