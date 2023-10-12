package com.hegunhee.newsimplememoapp.core.ui

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.hegunhee.newsimplememoapp.core.designsystem.DarkBlue

@Composable
fun DatePickerDialog(
    initialYear : Int,
    onDismissDialog : () -> Unit,
    onCurrentMonthClick : () -> Unit,
    onMonthClick : (Int, Int) -> Unit,
    monthList : List<Int> = listOf(1,2,3,4,5,6,7,8,9,10,11,12)
) {
    var year by remember{ mutableIntStateOf(initialYear) }
    val onNextYearButtonClick = {year++}
    val onPreviousYearButtonClick = {year--}
    val defaultModifier = Modifier.fillMaxWidth().padding(10.dp)
    Dialog(onDismissRequest = onDismissDialog) {
        Surface(modifier = Modifier.fillMaxWidth(0.9f)) {
            Column {
                Row(modifier = Modifier.background(DarkBlue).then(defaultModifier), verticalAlignment = Alignment.CenterVertically) {
                    Text(text = "날짜",color = Color.White, fontSize = 20.sp,textAlign = TextAlign.Center)
                    Spacer(modifier = Modifier.weight(0.7f))
                    Text(modifier = Modifier.clickable { onCurrentMonthClick();onDismissDialog() }.padding(10.dp),text = "이번 달",color = Color.White,fontSize = 20.sp,textAlign = TextAlign.End)
                    IconButton( onClick = {onDismissDialog()}) {
                        Icon(modifier = Modifier.size(30.dp),painter = painterResource(id = R.drawable.ic_exit),contentDescription = null,tint = Color.White)
                    }
                }
                Row(modifier = Modifier.background(Color.Black).then(defaultModifier), verticalAlignment = Alignment.CenterVertically) {
                    IconButton( onClick = {onPreviousYearButtonClick()} ) {
                        Icon(modifier = Modifier.size(48.dp),painter = painterResource(id = R.drawable.ic_left),contentDescription = null,tint = Color.White)
                    }
                    Text(modifier = Modifier.weight(0.6f),text = year.toString(), fontSize = 20.sp,color = Color.White,textAlign = TextAlign.Center)
                    IconButton( onClick = {onNextYearButtonClick()} ) {
                        Icon(modifier = Modifier.size(48.dp),painter = painterResource(id = R.drawable.ic_right),contentDescription = null,tint = Color.White)
                    }
                }
                LazyVerticalGrid(columns = GridCells.Fixed(4)) {
                    items(items = monthList,key = { index -> index}) { month ->
                        Text(modifier = Modifier.clickable { onMonthClick(year,month);onDismissDialog() }.background(Color.Black).padding(vertical = 10.dp),text = "${month}월",textAlign = TextAlign.Center,color = Color.White)
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun DatePickerDialog() {
    val context = LocalContext.current
    var dialogState by remember { mutableStateOf(false) }
    val openDialog = { dialogState = true }
    val dismissDialog = { dialogState = false }

    val onCurrentMonthClick = {
        val year = 2023
        val month = 10
        Toast.makeText(context,"지금의 날짜는${year}년 ${month}월",Toast.LENGTH_SHORT).show()
    }

    val onMonthClick : (Int,Int) -> Unit =  { year, month ->
        Toast.makeText(context,"${year}년 ${month}월",Toast.LENGTH_SHORT).show()
    }

    if(dialogState) {
        DatePickerDialog(
            initialYear = 2023,
            onDismissDialog = dismissDialog,
            onCurrentMonthClick = onCurrentMonthClick,
            onMonthClick = onMonthClick
        )
    }
    Button(onClick = openDialog) {
        Text(text = "DatePicker 열기")
    }
}