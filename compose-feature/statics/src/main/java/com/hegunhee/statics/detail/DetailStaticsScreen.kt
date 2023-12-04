package com.hegunhee.statics.detail

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun DetailStaticsScreenRoot(
    paddingValues: PaddingValues,
    attr : String
) {
    DetailStaticsScreen(paddingValues = paddingValues,attr = attr)
}

@Composable
fun DetailStaticsScreen(
    paddingValues: PaddingValues,
    attr : String
) {
    Text(text = attr)
}