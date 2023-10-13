package com.hegunhee.newsimplememoapp.core.ui

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun DetailMemoTopBar(
    onBackClick : () -> Unit,
    category : String
) {
    Row(verticalAlignment = Alignment.Bottom) {
        IconButton(onClick = onBackClick) {
            Icon(modifier = Modifier.size(50.dp),painter = painterResource(id = R.drawable.ic_back), contentDescription = null,tint = Color.Black)
        }
        Text(text = category, fontSize = 30.sp)
    }
}

@Preview
@Composable
private fun DetailMemoTopBar() {
    DetailMemoTopBar(onBackClick = {}, category = "지출")
}