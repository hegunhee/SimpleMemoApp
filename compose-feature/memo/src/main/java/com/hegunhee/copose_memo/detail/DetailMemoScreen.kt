package com.hegunhee.copose_memo.detail

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun DetailMemoScreen(memoId : Int) {
    Text(text = "디테일 메모 $memoId")
}