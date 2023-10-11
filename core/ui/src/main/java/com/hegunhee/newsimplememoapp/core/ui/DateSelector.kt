package com.hegunhee.newsimplememoapp.core.ui

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun DateSelector(
    modifier : Modifier = Modifier,
    year : Int,
    month : Int,
    onPreviousMonthClick : () -> Unit,
    onNextMonthClick : () -> Unit,
    onSelectorClick : () -> Unit
) {
    Row(modifier = modifier
        .padding(start = 20.dp)
        .clickable(onClick = onSelectorClick), verticalAlignment = Alignment.CenterVertically) {
        IconButton(
            modifier = Modifier.size(30.dp),
            onClick = onPreviousMonthClick
        ) {
            Icon(painter = painterResource(id = R.drawable.ic_left), contentDescription = "",modifier = Modifier.size(30.dp))
        }
        Text("${year}년", fontSize = 20.sp)
        Spacer(Modifier.size(10.dp))
        Text("${month}월",fontSize = 20.sp)
        IconButton(
            modifier = Modifier.size(30.dp),
            onClick = onNextMonthClick
        ) {
            Icon(painter = painterResource(id = R.drawable.ic_right), contentDescription = "",modifier = Modifier.size(30.dp))
        }
    }
}

@Preview
@Composable
private fun DateSelectorPreview(){
    val context = LocalContext.current
    val onPreviousMonthClick = { Toast.makeText(context, "이전버튼 클릭", Toast.LENGTH_SHORT).show()}
    val onNextMonthClick = { Toast.makeText(context, "다음 클릭", Toast.LENGTH_SHORT).show()}
    val onSelectorClick = { Toast.makeText(context, "셀렉터 클릭", Toast.LENGTH_SHORT).show()}

    DateSelector(
        year = 2023,
        month = 10,
        onPreviousMonthClick = onPreviousMonthClick,
        onNextMonthClick = onNextMonthClick,
        onSelectorClick = onSelectorClick
    )
}