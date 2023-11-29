package com.hegunhee.newsimplememoapp.core.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.material3.*
import java.text.SimpleDateFormat
import java.util.Date

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailMemoScreen(
    paddingValues: PaddingValues,
    onBackButtonClick : () -> Unit,
    category : String,
    dateInfo : String,
    onCategoryClick : (String) -> Unit,
    onSelectDateClick : (Int,Int,Int) -> Unit,
    memoScreenType : DetailMemoScreenType
) {
    val datePickerState = rememberDatePickerState()
    var showDatePicker by remember { mutableStateOf(false) }
    if(showDatePicker) {
        showDatePickerDialog(
            onDismissDialog = { showDatePicker = false},
            datePickerState = datePickerState,
            onSelectDateClick = onSelectDateClick
        )
    }
    Scaffold(
        modifier = Modifier
            .padding(start = 10.dp, top = 10.dp)
            .padding(paddingValues),
        topBar =  {
            DetailMemoTopBar(onBackButtonClick,category)
        }
    ) { padding ->
        Column(modifier = Modifier.padding(padding)) {
            CategorySelector(
                selectedCategory = category,
                onCategoryClick = onCategoryClick
            )
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(10.dp)) {
                Text(text = "날짜",modifier = Modifier.weight(0.2f), fontSize = 20.sp)
                Text(
                    text = dateInfo,
                    modifier = Modifier
                        .padding(end = 10.dp)
                        .weight(0.4f)
                        .clickable { showDatePicker = true }, fontSize = 20.sp,
                    maxLines = 1)
                Text(text = "오전 11:19",modifier = Modifier
                    .weight(0.4f)
                    .clickable {}, fontSize = 20.sp)
            }

            when(memoScreenType) {
                is DetailMemoScreenType.Add -> {

                }
                is DetailMemoScreenType.Detail -> {

                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun showDatePickerDialog(
    onDismissDialog : () -> Unit,
    datePickerState : DatePickerState,
    onSelectDateClick : (Int,Int,Int) -> Unit
) {
    DatePickerDialog(
        onDismissRequest = onDismissDialog,
        confirmButton = {
            TextButton(onClick = {
                datePickerState.selectedDateMillis?.let { timeStamp ->
                    val sdf = SimpleDateFormat("YYYY/MM/dd")
                    val netDate = Date(timeStamp)
                    val (year, month, day) = sdf.format(netDate).split("/").map { it.toInt() }
                    onSelectDateClick(year,month,day)
                    onDismissDialog()
                }
            }) {
                Text(text = "선택")
            }
        },
    ) {
        DatePicker(datePickerState)
    }
}

sealed class DetailMemoScreenType {

    data class Add(
        val onSaveMemoClick : () -> Unit
    ) : DetailMemoScreenType()

    data class Detail(
        val onUpdateMemoClick : () -> Unit,
        val onDeleteMemoClick : () -> Unit
    ) : DetailMemoScreenType()
}