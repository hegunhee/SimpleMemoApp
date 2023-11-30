package com.hegunhee.newsimplememoapp.core.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.ui.graphics.Color
import com.hegunhee.newsimplememoapp.domain.model.CategoryType
import com.hegunhee.newsimplememoapp.domain.model.TimeInfo

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailMemoScreen(
    paddingValues: PaddingValues,
    onBackButtonClick : () -> Unit,
    category : String,
    dateInfo : String,
    timeInfo : TimeInfo,
    asset : String,
    attr : String,
    selectedCategoryType : CategoryType,
    subCategoryList : List<String>,
    onCategoryClick : (String) -> Unit,
    onSelectDateClick : (Int,Int,Int) -> Unit,
    onSelectTimeClick : (Int,Int,String) -> Unit,
    onSubCategoryClick : (CategoryType) -> Unit,
    onSubCategoryItemClick : (CategoryType, String) -> Unit,
    onAddSubCategoryClick : (String) -> Unit,
    memoScreenType : DetailMemoScreenType
) {
    val datePickerState = rememberDatePickerState()
    var showDatePicker by remember { mutableStateOf(false) }
    if(showDatePicker) {
        MemoDatePickerDialog(
            onDismissDialog = { showDatePicker = false },
            datePickerState = datePickerState,
            onSelectDateClick = onSelectDateClick
        )
    }

    val timePickerState = rememberTimePickerState(initialHour = timeInfo.hour, initialMinute = timeInfo.minute)
    var showTimePicker by remember { mutableStateOf(false) }
    if(showTimePicker) {
        MemoTimePickerDialog(
            onDismissDialog = { showTimePicker = false },
            timePickerState = timePickerState,
            onSelectTimeClick =   onSelectTimeClick
        )
    }

    var showCategoryBottomSheet by remember { mutableStateOf(false) }
    if(showCategoryBottomSheet && (selectedCategoryType!is CategoryType.Empty)) {
        CategoryBottomSheet(
            onDismissRequest = { showCategoryBottomSheet = false},
            selectedCategoryType = selectedCategoryType,
            categoryList = subCategoryList,
            onSubCategoryClick = onSubCategoryClick,
            onSubCategoryItemClick = onSubCategoryItemClick,
            onAddSubCategoryClick = onAddSubCategoryClick,
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
                selectedCategory = asset,
                onSubCategoryClick = onSubCategoryClick,
                showCategoryBottomSheet = { showCategoryBottomSheet = true }
            )

            val attrType = if(category == "수입") {
                CategoryType.AttrIncome
            }else {
                CategoryType.AttrExpenses
            }
            SubCategory(
                categoryType = attrType,
                selectedCategory = attr,
                onSubCategoryClick = onSubCategoryClick,
                showCategoryBottomSheet = { showCategoryBottomSheet = true }
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

sealed class DetailMemoScreenType {

    data class Add(
        val onSaveMemoClick : () -> Unit
    ) : DetailMemoScreenType()

    data class Detail(
        val onUpdateMemoClick : () -> Unit,
        val onDeleteMemoClick : () -> Unit
    ) : DetailMemoScreenType()
}