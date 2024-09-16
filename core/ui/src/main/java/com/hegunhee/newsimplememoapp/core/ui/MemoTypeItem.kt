package com.hegunhee.newsimplememoapp.core.ui

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hegunhee.newsimplememoapp.core.designsystem.Purple80
import com.hegunhee.newsimplememoapp.domain.model.memo.IncomeExpenseType
import com.hegunhee.newsimplememoapp.domain.model.memo.MemoType
import java.time.LocalDate
import java.time.LocalDateTime

@Composable
fun MemoDateItem(memoDate: MemoType.MemoDate) {
    val dayOfWeekColor = when (memoDate.dayOfWeek()) {
        "토" -> Color.Blue
        "일" -> Color.Red
        else -> Color.Black
    }
    val dateInfo = memoDate.dateStamp()
    Column {
        Divider(
            color = Color.Black, modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
        )
        Row(
            modifier = Modifier
                .padding(horizontal = 10.dp, vertical = 5.dp)
                .fillMaxWidth(), horizontalArrangement = Arrangement.End
        ) {
            val baselineModifier = Modifier.alignByBaseline()
            Text(
                modifier = baselineModifier,
                text = memoDate.date.dayOfMonth.toString(),
                fontSize = 20.sp
            )
            Text(
                modifier = Modifier
                    .padding(3.dp)
                    .background(dayOfWeekColor)
                    .then(baselineModifier),
                text = memoDate.dayOfWeek() + "요일",
                fontSize = 15.sp,
                color = Color.White
            )
            Text(
                modifier = Modifier
                    .padding(5.dp)
                    .then(baselineModifier), text = dateInfo
            )
            Text(
                modifier = Modifier
                    .weight(3.5f)
                    .then(baselineModifier),
                text = "${memoDate.incomeSum}원",
                color = Color.Blue,
                style = TextStyle(textAlign = TextAlign.End),
                maxLines = 1
            )
            Text(
                modifier = Modifier
                    .weight(3.5f)
                    .then(baselineModifier),
                text = "${memoDate.expenseSum}원",
                color = Color.Red,
                style = TextStyle(textAlign = TextAlign.End),
                maxLines = 1
            )
        }
        Divider(
            color = Color.Black, modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
        )
    }

}

@Composable
fun MemoItem(memo: MemoType.Memo, onMemoClick: (String) -> Unit) {
    val description = memo.desc.ifEmpty { memo.asset }
    val descriptionColor = if (memo.desc.isEmpty()) {
        Color.Gray
    } else {
        Color.Black
    }
    val priceColor = if (memo.incomeExpenseType.name == "EXPENSE") {
        Color.Red
    } else {
        Color.Blue
    }
    val timeInfo = memo.timeStamp()
    Row(modifier = Modifier
        .clickable { onMemoClick(memo.id.toString()) }
        .background(Purple80)
        .padding(horizontal = 15.dp)
        .fillMaxWidth()
        .requiredHeight(50.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = Modifier.weight(1f),
            text = memo.attr,
            textAlign = TextAlign.Center,
            fontSize = 13.sp,
            color = Color.Black
        )
        Column(
            modifier = Modifier
                .weight(4f)
                .padding(start = 5.dp),
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                modifier = Modifier.padding(top = 5.dp),
                text = description,
                color = descriptionColor,
                fontSize = 17.sp,
                maxLines = 1
            )
            Text(text = timeInfo, color = Color.Gray, fontSize = 13.sp)
        }
        Text(
            modifier = Modifier.weight(5f),
            text = "${memo.price}원",
            textAlign = TextAlign.End,
            fontSize = 15.sp,
            maxLines = 1,
            color = priceColor
        )
    }
}

@Preview
@Composable
private fun MemoDateItemPreview() {
    val memoDate = MemoType.MemoDate(
        date = LocalDate.now(),
        incomeSum = 0,
        expenseSum = 123123
    )
    MemoDateItem(memoDate = memoDate)
}

@Preview
@Composable
private fun MemoItemPreview() {
    val context = LocalContext.current
    val onMemoClick = { id: String ->
        Toast.makeText(context, id, Toast.LENGTH_SHORT).show()
    }
    val memo = MemoType.Memo(
        id = 5,
        incomeExpenseType = IncomeExpenseType.INCOME,
        date = LocalDateTime.now(),
        asset = "자산",
        attr = "분류",
        price = 10000,
        desc = "설명임당"
    )
    MemoItem(memo = memo, onMemoClick = onMemoClick)
}

@Preview
@Composable
private fun MemoListPreview() {
    val memoList = getTestMemoList()
    LazyColumn() {
        itemsIndexed(memoList) { index, item ->
            when (item) {
                is MemoType.MemoDate -> {
                    if (index != 0) {
                        Spacer(Modifier.size(10.dp))
                    }
                    MemoDateItem(item)
                }

                is MemoType.Memo -> {
                    MemoItem(item) { id: String -> }
                }
            }
        }
    }
}

private fun getTestMemoList(): List<MemoType> {
    val memo = MemoType.Memo(
        id = 5,
        incomeExpenseType = IncomeExpenseType.INCOME,
        date = LocalDateTime.now(),
        asset = "자산",
        attr = "분류",
        price = 10000,
        desc = "설명임당"
    )
    val memoDate = MemoType.MemoDate(
        date = LocalDate.now(),
        incomeSum = 0,
        expenseSum = 123123
    )
    return listOf(
        memoDate,
        memo,
        memo,
        memoDate,
        memo
    )
} 