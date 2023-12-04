package com.hegunhee.statics

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.hegunhee.newsimplememoapp.core.ui.CategorySelector
import com.hegunhee.newsimplememoapp.core.ui.DatePickerDialog
import com.hegunhee.newsimplememoapp.core.ui.DateSelector
import com.hegunhee.newsimplememoapp.domain.model.StaticsData

@Composable
fun StaticsScreenRoot(
    paddingValues: PaddingValues,
    viewModel : StaticsViewModel = hiltViewModel()
) {
    val uiState = viewModel.uiState.collectAsStateWithLifecycle().value

    when(uiState) {
        is StaticsUiState.Loading -> { }
        is StaticsUiState.Success -> {
            StaticsScreen(
                paddingValues = paddingValues,
                year = uiState.year,
                month = uiState.month,
                category = uiState.category,
                staticsList = uiState.staticsList,
                onPreviousMonthCLick = viewModel::onPreviousMonthClick,
                onNextMonthClick = viewModel::onNextMonthClick,
                onDatePickerCurrentMonthClick = viewModel::onDatePickerCurrentMonthClick,
                onDatePickerMonthClick =viewModel::onDatePickerMonthClick,
                onCategoryClick = viewModel::setCategory
            )
        }
    }
}

@Composable
fun StaticsScreen(
    paddingValues: PaddingValues,
    year : Int,
    month : Int,
    category: String,
    staticsList : List<StaticsData>,
    onPreviousMonthCLick : () -> Unit,
    onNextMonthClick : () -> Unit,
    onDatePickerCurrentMonthClick : () -> Unit,
    onDatePickerMonthClick : (Int, Int) -> Unit,
    onCategoryClick : (String) -> Unit,
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
        Column(modifier = Modifier.fillMaxWidth().padding(padding)) {
            CategorySelector(
                selectedCategory = category,
                onCategoryClick = onCategoryClick
            )
            Text(text = staticsList.toString(),modifier = Modifier.padding(padding))
        }
    }
}