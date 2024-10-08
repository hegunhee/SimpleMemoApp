package com.hegunhee.statics

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.hegunhee.newsimplememoapp.core.ui.DatePickerDialog
import com.hegunhee.newsimplememoapp.core.ui.DateSelector
import com.hegunhee.newsimplememoapp.core.ui.IncomeExpenseTypeSelector
import com.hegunhee.newsimplememoapp.core.ui.PercentStatics
import com.hegunhee.newsimplememoapp.domain.model.memo.IncomeExpenseType
import com.hegunhee.newsimplememoapp.domain.model.memo.StaticsMemo

@Composable
fun StaticsScreenRoot(
    paddingValues: PaddingValues,
    viewModel : StaticsViewModel = hiltViewModel(),
    onDetailStaticsClick : (String) -> Unit
) {
    val uiState = viewModel.uiState.collectAsStateWithLifecycle().value


    // 년도와 날짜만 분리해서 상단에 고정시켜놓을까 고민중
    when(uiState) {
        is StaticsUiState.Loading -> { }
        is StaticsUiState.Success -> {
            StaticsScreen(
                paddingValues = paddingValues,
                year = uiState.year,
                month = uiState.month,
                incomeExpenseType = uiState.incomeExpenseType,
                staticsList = uiState.staticsList,
                onPreviousMonthCLick = viewModel::onPreviousMonthClick,
                onNextMonthClick = viewModel::onNextMonthClick,
                onDatePickerCurrentMonthClick = viewModel::onDatePickerCurrentMonthClick,
                onDatePickerMonthClick =viewModel::onDatePickerMonthClick,
                onIncomeExpenseTypeClick = viewModel::setIncomeExpenseType,
                onDetailStaticsClick = onDetailStaticsClick
            )
        }
    }
}

@Composable
fun StaticsScreen(
    paddingValues: PaddingValues,
    year : Int,
    month : Int,
    incomeExpenseType: IncomeExpenseType,
    staticsList : List<StaticsMemo>,
    onPreviousMonthCLick : () -> Unit,
    onNextMonthClick : () -> Unit,
    onDatePickerCurrentMonthClick : () -> Unit,
    onDatePickerMonthClick : (Int, Int) -> Unit,
    onIncomeExpenseTypeClick : (IncomeExpenseType) -> Unit,
    onDetailStaticsClick: (String) -> Unit
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
        topBar = { DateSelector(
            year = year,
            month = month,
            onPreviousMonthClick = onPreviousMonthCLick,
            onNextMonthClick = onNextMonthClick ,
            onSelectorClick = showDatePickerDialog
        ) }
    ) { padding ->
        Column(modifier = Modifier.fillMaxWidth().padding(padding).padding(horizontal = 10.dp)) {
            IncomeExpenseTypeSelector(
                selectedCategory = incomeExpenseType,
                onCategoryClick = onIncomeExpenseTypeClick
            )
            LazyColumn() {
                items(staticsList,key = {it.attribute}) {
                    PercentStatics(it,onDetailStaticsClick)
                }
            }
        }
    }
}