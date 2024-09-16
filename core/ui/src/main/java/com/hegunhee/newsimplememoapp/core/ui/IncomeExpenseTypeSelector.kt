package com.hegunhee.newsimplememoapp.core.ui

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hegunhee.newsimplememoapp.domain.model.memo.IncomeExpenseType

@Composable
fun IncomeExpenseTypeSelector(
    selectedCategory: IncomeExpenseType,
    onCategoryClick: (IncomeExpenseType) -> Unit,
    categoryColor : List<Color> = listOf(Color.Blue,Color.Red)
) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(10.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        IncomeExpenseType.values().toList().zip(categoryColor) {  category, selectedColor ->
            val fontColor = if(category == selectedCategory) {
                selectedColor
            }else {
                Color.Gray
            }
            CategoryButton(onClick = onCategoryClick, category =category, fontColor = fontColor)
        }
    }
}

@Composable
fun RowScope.CategoryButton(
    onClick : (IncomeExpenseType) -> Unit,
    category : IncomeExpenseType,
    fontColor : Color
) {
    Button(
        onClick = {onClick(category)},
        modifier = Modifier.weight(1f).border(width = 1.dp, color = fontColor),
        shape = RectangleShape,
        colors = ButtonDefaults.buttonColors(containerColor = Color.White,contentColor = fontColor)
    ) {
        Text(text = category.toString())
    }
}

@Preview
@Composable
private fun IncomeExpenseTypeSelector() {
    val (category, setCategory) = remember { mutableStateOf(IncomeExpenseType.INCOME) }
    IncomeExpenseTypeSelector(
        selectedCategory = category,
        onCategoryClick = setCategory
    )
}