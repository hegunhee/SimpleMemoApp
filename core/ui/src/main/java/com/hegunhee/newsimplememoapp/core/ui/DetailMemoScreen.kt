package com.hegunhee.newsimplememoapp.core.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import com.hegunhee.newsimplememoapp.domain.model.CategoryType
import com.hegunhee.newsimplememoapp.domain.model.TimeInfo
import com.holix.android.bottomsheetdialog.compose.BottomSheetDialog
import java.text.SimpleDateFormat
import java.util.Date

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailMemoScreen(
    paddingValues: PaddingValues,
    onBackButtonClick : () -> Unit,
    category : String,
    dateInfo : String,
    timeInfo : TimeInfo,
    subCategoryList : List<String>,
    onCategoryClick : (String) -> Unit,
    onSelectDateClick : (Int,Int,Int) -> Unit,
    onSelectTimeClick : (Int,Int,String) -> Unit,
    onSubCategoryClick : (CategoryType) -> Unit,
    memoScreenType : DetailMemoScreenType
) {
    val datePickerState = rememberDatePickerState()
    var showDatePicker by remember { mutableStateOf(false) }
    if(showDatePicker) {
        ShowDatePickerDialog(
            onDismissDialog = { showDatePicker = false },
            datePickerState = datePickerState,
            onSelectDateClick = onSelectDateClick
        )
    }

    val timePickerState = rememberTimePickerState(initialHour = timeInfo.hour, initialMinute = timeInfo.minute)
    var showTimePicker by remember { mutableStateOf(false) }
    if(showTimePicker) {
        ShowTimePickerDialog(
            onDismissDialog = { showTimePicker = false },
            timePickerState = timePickerState,
            onSelectTimeClick =   onSelectTimeClick
        )
    }

    var showBottom by remember { mutableStateOf(false) }
    if(showBottom && subCategoryList.isNotEmpty()) {
        BottomSheetDialog(onDismissRequest = { showBottom = false }) {
            Surface {
                Text(text = "Hi")
                Text(text = subCategoryList.toString())
            }
        }
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
                    .padding(10.dp)
            ) {
                Text(text = "날짜",modifier = Modifier.weight(0.2f), fontSize = 20.sp)
                Text(
                    text = dateInfo,
                    modifier = Modifier
                        .padding(end = 10.dp)
                        .weight(0.4f)
                        .clickable { showDatePicker = true }, fontSize = 20.sp,
                    maxLines = 1)
                Text(text = timeInfo.timeStamp,modifier = Modifier
                    .weight(0.4f)
                    .clickable { showTimePicker = true }, fontSize = 20.sp)
            }

            SubCategory(
                categoryType = CategoryType.Asset,
                selectedCategory = "현금",
                onSubCategoryClick = onSubCategoryClick,
                showCategoryBottomSheet = { showBottom = true }
            )

            val attrType = if(category == "수입") {
                CategoryType.AttrIncome
            }else {
                CategoryType.AttrExpenses
            }
            SubCategory(
                categoryType = attrType,
                selectedCategory = "식비",
                onSubCategoryClick = onSubCategoryClick,
                showCategoryBottomSheet = { showBottom = true }
            )

            when(memoScreenType) {
                is DetailMemoScreenType.Add -> {

                }
                is DetailMemoScreenType.Detail -> {

                }
            }
        }
    }
}

@Composable
private fun SubCategory(
    categoryType : CategoryType,
    selectedCategory : String,
    onSubCategoryClick : (CategoryType) -> Unit,
    showCategoryBottomSheet : () -> Unit
) {
    Row(
        Modifier
            .fillMaxWidth()
            .padding(10.dp)
    ) {
        Text(text = categoryType.title,modifier = Modifier.weight(0.2f),fontSize = 20.sp)
        Column(
            modifier = Modifier
                .padding(end = 10.dp)
                .weight(0.8f)
                .clickable {
                    onSubCategoryClick(categoryType)
                    showCategoryBottomSheet()
                },
            verticalArrangement = Arrangement.Bottom
        ) {
            Text(selectedCategory,fontSize = 20.sp, maxLines = 1)
            Divider(modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(Color.Black))
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ShowDatePickerDialog(
    onDismissDialog : () -> Unit,
    datePickerState : DatePickerState,
    onSelectDateClick : (Int,Int,Int) -> Unit
) {
    DatePickerDialog(
        onDismissRequest = onDismissDialog,
        confirmButton = {
            TextButton(onClick = {
                datePickerState.selectedDateMillis?.let { timeStamp ->
                    SimpleDateFormat("YYYY/MM/dd").let { dateFormat ->
                        val date = Date(timeStamp)
                        val (year, month, day) = dateFormat.format(date).split("/").map { it.toInt() }
                        onSelectDateClick(year,month,day)
                    }
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ShowTimePickerDialog(
    onDismissDialog: () -> Unit,
    timePickerState: TimePickerState,
    onSelectTimeClick : (Int,Int,String) -> Unit)
{
    AlertDialog(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = MaterialTheme.colorScheme.surface,
                shape = RoundedCornerShape(size = 12.dp)
            ),
        onDismissRequest = onDismissDialog
    ) {
        Column(
            modifier = Modifier
                .background(
                    color = Color.LightGray.copy(alpha = 0.3f)
                )
                .padding(top = 28.dp, start = 20.dp, end = 20.dp, bottom = 12.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // time picker
            TimePicker(state = timePickerState)

            // buttons
            Row(
                modifier = Modifier
                    .padding(top = 12.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                // dismiss button
                TextButton(onClick = onDismissDialog) {
                    Text(text = "취소")
                }

                // confirm button
                TextButton(
                    onClick = {
                        val hour = if(timePickerState.hour > 12)  {
                            timePickerState.hour - 12
                        }else {
                            timePickerState.hour
                        }
                        val ampm = if(timePickerState.hour > 12) {
                            "오후"
                        }else {
                            "오전"
                        }
                        onSelectTimeClick(hour,timePickerState.minute,ampm)
                        onDismissDialog()
                    }
                ) {
                    Text(text = "선택")
                }
            }
        }
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