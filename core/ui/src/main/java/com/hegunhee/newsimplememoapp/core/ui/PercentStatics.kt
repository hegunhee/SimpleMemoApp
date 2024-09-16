package com.hegunhee.newsimplememoapp.core.ui

import android.icu.text.DecimalFormat
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hegunhee.newsimplememoapp.domain.model.memo.StaticsMemo

@Composable
fun PercentStatics(
    staticsData: StaticsMemo,
    onClickStaticsItem : (String) -> Unit,
) {
    val textSize = 20.sp
    Row(modifier = Modifier.clickable { onClickStaticsItem(staticsData.attribute) }.fillMaxWidth().heightIn(30.dp)) {
        Text(text = staticsData.percent.toString() + "%",modifier = Modifier.weight(0.2f), fontSize = textSize)
        Text(text = staticsData.attribute,modifier = Modifier.weight(0.3f), textAlign = TextAlign.Start, fontSize = textSize, maxLines = 1)
        Text(text = staticsData.price.intValueExact().toMoneyFormat() + "원",modifier = Modifier.weight(0.5f),textAlign = TextAlign.End, fontSize = 20.sp, maxLines = 1)
    }
}

fun Int.toMoneyFormat() : String{
    return DecimalFormat("#,###").format(this)
}