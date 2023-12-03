package com.hegunhee.statics

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun StaticsScreenRoot(paddingValues: PaddingValues) {
    StaticsScreen(paddingValues)
}

@Composable
fun StaticsScreen(
    paddingValues: PaddingValues
) {
    Text(text = "Statics 화면입니다.")
}