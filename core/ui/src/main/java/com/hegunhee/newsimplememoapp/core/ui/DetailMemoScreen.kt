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
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import com.hegunhee.newsimplememoapp.domain.model.category.CategoryType
import com.hegunhee.newsimplememoapp.domain.model.TimeInfo
import com.hegunhee.newsimplememoapp.domain.model.getDateStamp
import com.hegunhee.newsimplememoapp.domain.model.getTimeStamp
import com.hegunhee.newsimplememoapp.domain.model.memo.IncomeExpenseType
import com.hegunhee.newsimplememoapp.domain.model.memo.MemoForm
import java.time.LocalDate
import java.time.LocalTime

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailMemoScreen(
    paddingValues: PaddingValues,
    onBackButtonClick : () -> Unit,
    memoForm : MemoForm,
    price : String,
    description : String,
    selectedCategoryType : CategoryType,
    subCategoryList : List<String>,
    onCategoryClick : (IncomeExpenseType) -> Unit,
    onSelectDateClick : (LocalDate) -> Unit,
    onSelectTimeClick : (LocalTime) -> Unit,
    onSubCategoryClick : (CategoryType) -> Unit,
    onSubCategoryItemClick : (CategoryType, String) -> Unit,
    onAddSubCategoryClick : (String) -> Unit,
    onPriceValueChanged : (String) -> Unit,
    onDescriptionValueChanged : (String) -> Unit,
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

    val timePickerState = rememberTimePickerState(initialHour = memoForm.memoDate.hour, initialMinute = memoForm.memoDate.minute)
    var showTimePicker by remember { mutableStateOf(false) }
    if(showTimePicker) {
        MemoTimePickerDialog(
            onDismissDialog = { showTimePicker = false },
            timePickerState = timePickerState,
            onSelectTimeClick =   onSelectTimeClick
        )
    }

    var showCategoryBottomSheet by rememberSaveable { mutableStateOf(false) }
    if(showCategoryBottomSheet && (selectedCategoryType != CategoryType.EMPTY)) {
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
            DetailMemoTopBar(onBackButtonClick,memoForm.incomeExpenseType.toString())
        }
    ) { padding ->
        Column(modifier = Modifier.padding(padding)) {
            IncomeExpenseTypeSelector(
                selectedCategory = memoForm.incomeExpenseType,
                onCategoryClick = onCategoryClick
            )
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
            ) {
                Text(text = "날짜",modifier = Modifier.weight(0.2f), fontSize = 20.sp)
                Text(
                    text = memoForm.memoDate.getDateStamp(),
                    modifier = Modifier
                        .padding(end = 10.dp)
                        .weight(0.4f)
                        .clickable { showDatePicker = true }, fontSize = 20.sp,
                    maxLines = 1)
                Text(text = memoForm.memoDate.getTimeStamp(),modifier = Modifier
                    .weight(0.4f)
                    .clickable { showTimePicker = true }, fontSize = 20.sp)
            }

            SubCategory(
                categoryType = CategoryType.ASSET,
                selectedCategory = memoForm.asset,
                onSubCategoryClick = onSubCategoryClick,
                showCategoryBottomSheet = { showCategoryBottomSheet = true }
            )

            val attrType = if(memoForm.incomeExpenseType == IncomeExpenseType.INCOME) {
                CategoryType.ATTR_INCOME
            }else {
                CategoryType.ATTR_EXPENSE
            }
            SubCategory(
                categoryType = attrType,
                selectedCategory = memoForm.attribute,
                onSubCategoryClick = onSubCategoryClick,
                showCategoryBottomSheet = { showCategoryBottomSheet = true }
            )

            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "가격",modifier = Modifier.weight(0.2f), fontSize = 20.sp)
                OutlinedTextField(value = price, onValueChange = onPriceValueChanged, singleLine = true,keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number))
            }

            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
            ) {
                Text(text = "설명",modifier = Modifier.weight(0.2f), fontSize = 20.sp)
                OutlinedTextField(value = description, onValueChange = onDescriptionValueChanged, singleLine = true)
            }



            when(memoScreenType) {
                is DetailMemoScreenType.Add -> {
                    AddMemo(memoScreenType.onSaveMemoClick,onBackButtonClick)
                }
                is DetailMemoScreenType.Detail -> {
                    DetailMemo(
                        memoScreenType.onUpdateMemoClick,
                        memoScreenType.onDeleteMemoClick,
                        onBackButtonClick
                    )
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

@Composable
private fun AddMemo(onSaveMemoClick : () -> Boolean,onBackButtonClick: () -> Unit) {
    Button(onClick = {
        if(onSaveMemoClick()) {
            onBackButtonClick()
        }
    },modifier = Modifier.fillMaxWidth().padding(10.dp)) {
        Text(text = "저장하기")
    }
}

@Composable
private fun DetailMemo(
    onUpdateMemoClick : () -> Boolean,
    onDeleteMemoClick : () -> Unit,
    onBackButtonClick: () -> Unit
) {
    Row( modifier = Modifier.fillMaxWidth().padding(10.dp)) {
        Button(onClick = {
            if(onUpdateMemoClick()) {
                onBackButtonClick()
            }
        },modifier = Modifier.weight(0.5f).padding(end = 10.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Blue
            )) {
            Text(text = "수정하기")
        }
        Button(onClick = {
            onDeleteMemoClick()
            onBackButtonClick()
         },
            modifier = Modifier.weight(0.5f),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Red
            )

        ) {
            Text(text = "삭제하기")
        }
    }
}
sealed class DetailMemoScreenType {

    data class Add(
        val onSaveMemoClick : () -> Boolean
    ) : DetailMemoScreenType()

    data class Detail(
        val onUpdateMemoClick : () -> Boolean,
        val onDeleteMemoClick : () -> Unit
    ) : DetailMemoScreenType()
}