package com.hegunhee.copose_memo

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hegunhee.compose_feature.memo.R
import com.hegunhee.newsimplememoapp.core.ui.DateSelector

@Composable
fun MemoScreenRoot(
    paddingValues: PaddingValues
) {
    MemoScreen(
        paddingValues = paddingValues,
        year = 2023,
        month =  10,
        onPreviousMonthClick = {},
        onNextMonthClick = {},
        onSelectorClick = {}
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MemoScreen(
    paddingValues: PaddingValues,
    year : Int,
    month : Int,
    onPreviousMonthClick : () -> Unit,
    onNextMonthClick : () -> Unit,
    onSelectorClick : () -> Unit
) {
    Scaffold(
        modifier = Modifier
            .padding(paddingValues)
            .padding(top = 10.dp),
        topBar = { DateSelector(
            year = year,
            month = month,
            onPreviousMonthClick = onPreviousMonthClick,
            onNextMonthClick = onNextMonthClick,
            onSelectorClick = onSelectorClick
        )},
        floatingActionButton = {
            FloatingActionButton(onClick = {  }) {
                Icon(painter = painterResource(id = R.drawable.ic_add), contentDescription = "설명")
            }
        }
    ) { padding ->
        Column(modifier = Modifier.padding(padding)) {
            TotalPriceGraph()

        }
    }
}

@Composable
private fun TotalPriceGraph() {
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
        Text(text = "123123", fontSize = 15.sp)
        Text(text = "22222", fontSize = 15.sp)
        Text(text = "222222", fontSize = 15.sp)
    }
}