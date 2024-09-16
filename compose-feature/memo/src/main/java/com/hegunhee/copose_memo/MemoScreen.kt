package com.hegunhee.copose_memo

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.hegunhee.compose_feature.memo.R
import com.hegunhee.newsimplememoapp.util.DateUtil
import com.hegunhee.newsimplememoapp.core.ui.DatePickerDialog
import com.hegunhee.newsimplememoapp.core.ui.DateSelector
import com.hegunhee.newsimplememoapp.core.ui.MemoDateItem
import com.hegunhee.newsimplememoapp.core.ui.MemoItem
import com.hegunhee.newsimplememoapp.domain.model.TotalSum
import com.hegunhee.newsimplememoapp.domain.model.memo.MemoType
import java.math.BigDecimal

@Composable
fun MemoScreenRoot(
    paddingValues: PaddingValues,
    viewModel : MemoViewModel = hiltViewModel(),
    onAddMemoClick : () -> Unit,
    onMemoClick : (String) -> Unit,
) {
    val uiState = (viewModel.uiState.collectAsStateWithLifecycle().value as? MemoUiState.Success) ?: MemoUiState.Success(
        year = DateUtil.getYear(),
        month = DateUtil.getMonth(),
        memoTypeList = emptyList(),
        totalSum = TotalSum(BigDecimal.ZERO,BigDecimal.ZERO,BigDecimal.ZERO)
    )
    MemoScreen(
        paddingValues = paddingValues,
        year = uiState.year,
        month =  uiState.month,
        totalSum = uiState.totalSum,
        memoTypeList = uiState.memoTypeList,
        onPreviousMonthClick = viewModel::onPreviousMonthClick,
        onNextMonthClick = viewModel::onNextMonthClick,
        onDatePickerCurrentMonthClick = viewModel::onDatePickerCurrentMonthClick,
        onDatePickerMonthClick = viewModel::onDatePickerMonthClick,
        onAddMemoClick = onAddMemoClick,
        onMemoClick = onMemoClick
    )
}

@Composable
fun MemoScreen(
    paddingValues: PaddingValues,
    year : Int,
    month : Int,
    totalSum : TotalSum,
    memoTypeList : List<MemoType>,
    onPreviousMonthClick : () -> Unit,
    onNextMonthClick : () -> Unit,
    onDatePickerCurrentMonthClick : () -> Unit,
    onDatePickerMonthClick : (Int, Int) -> Unit,
    onAddMemoClick : () -> Unit,
    onMemoClick : (String) -> Unit,
) {
    var datePickerDialogState by remember{mutableStateOf(false)}
    val showDatePickerDialog = { datePickerDialogState = true}
    val dismissDatePickerDialog = { datePickerDialogState = false}

    if(datePickerDialogState) {
        DatePickerDialog(
            initialYear = year,
            onDismissDialog = dismissDatePickerDialog,
            onCurrentMonthClick = onDatePickerCurrentMonthClick,
            onMonthClick = onDatePickerMonthClick,
        )
    }
    
    Scaffold(
        modifier = Modifier
            .padding(paddingValues)
            .padding(top = 10.dp),
        topBar = { DateSelector(
            year = year,
            month = month,
            onPreviousMonthClick = onPreviousMonthClick,
            onNextMonthClick = onNextMonthClick,
            onSelectorClick = showDatePickerDialog
        )},
        floatingActionButton = {
            FloatingActionButton(onClick = onAddMemoClick) {
                Icon(painter = painterResource(id = R.drawable.ic_add), contentDescription = "설명")
            }
        }
    ) { padding ->
        Column(modifier = Modifier.padding(padding)) {
            TotalPriceTable(totalSum)
            LazyColumn() {
                itemsIndexed(memoTypeList) {index, item ->
                    when(item) {
                        is MemoType.MemoDate -> {
                            if(index != 0) {
                                Spacer(Modifier.size(10.dp))
                            }
                            MemoDateItem(memoDate = item)
                        }
                        is MemoType.Memo -> {
                            MemoItem(memo = item, onMemoClick = onMemoClick)
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun TotalPriceTable(totalSum : TotalSum) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly,
    ) {
        Text(text = "수입", fontSize = 15.sp)
        Text(text = "지출", fontSize = 15.sp)
        Text(text = "합계", fontSize = 15.sp)
    }
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly,
    ) {
        Text(text = totalSum.incomeSum.toString(), fontSize = 15.sp)
        Text(text = totalSum.expenseSum.toString(), fontSize = 15.sp)
        Text(text = totalSum.totalSum.toString(), fontSize = 15.sp)
    }
}

@Composable
@Preview
private fun TotalPriceTablePreview() {
    val totalSum = TotalSum(incomeSum = BigDecimal.valueOf(32121), expenseSum = BigDecimal.valueOf(44433), totalSum = BigDecimal.valueOf(-10000))
    Column {
        TotalPriceTable(totalSum = totalSum)
    }
}