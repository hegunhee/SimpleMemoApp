package com.hegunhee.statics.detail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.hegunhee.newsimplememoapp.core.ui.DatePickerDialog
import com.hegunhee.newsimplememoapp.core.ui.DateSelector
import com.hegunhee.newsimplememoapp.domain.model.MemoType

@Composable
fun DetailStaticsScreenRoot(
    paddingValues: PaddingValues,
    viewModel : DetailStaticsViewModel = hiltViewModel(),
    attr : String
) {
    LaunchedEffect(key1 = attr) {
        viewModel.initAttr(attr)
    }
    val uiState = viewModel.uiState.collectAsStateWithLifecycle().value
    when(uiState) {
        is DetailStaticsUiState.Loading -> {

        }
        is DetailStaticsUiState.Success -> {
            DetailStaticsScreen(
                paddingValues = paddingValues,
                year = uiState.year,
                month = uiState.month,
                attr = uiState.attr,
                memoList = uiState.memoList,
                totalPrice = uiState.totalPrice,
                onPreviousMonthCLick = viewModel::onPreviousMonthClick,
                onNextMonthClick = viewModel::onNextMonthClick,
                onDatePickerCurrentMonthClick = viewModel::onDatePickerCurrentMonthClick,
                onDatePickerMonthClick =viewModel::onDatePickerMonthClick,
            )
        }
    }
}

@Composable
fun DetailStaticsScreen(
    paddingValues: PaddingValues,
    year : Int,
    month : Int,
    attr : String,
    memoList : List<MemoType>,
    totalPrice : String,
    onPreviousMonthCLick : () -> Unit,
    onNextMonthClick : () -> Unit,
    onDatePickerCurrentMonthClick : () -> Unit,
    onDatePickerMonthClick : (Int, Int) -> Unit,
) {
    var datePickerDialogState by remember{ mutableStateOf<Boolean>(false) }
    val showDatePickerDialog = { datePickerDialogState = true}
    val dismissDatePickerDialog = { datePickerDialogState = false}

    if(datePickerDialogState) {
        DatePickerDialog(
            initialYear = year,
            onDismissDialog = dismissDatePickerDialog,
            onCurrentMonthClick = onDatePickerCurrentMonthClick,
            onMonthClick = onDatePickerMonthClick
        )
    }

    Scaffold(
        modifier = Modifier
            .padding(paddingValues)
            .padding(top = 10.dp),
        topBar = {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Start) {
                DateSelector(
                    year = year,
                    month = month,
                    onPreviousMonthClick = onPreviousMonthCLick,
                    onNextMonthClick = onNextMonthClick,
                    onSelectorClick = showDatePickerDialog
                )
                Text(text = attr, fontSize = 20.sp,modifier = Modifier.alignByBaseline())
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier.padding(padding)
        ) {
            Text(text = memoList.toString())
            Text(text = totalPrice)
        }
    }
}