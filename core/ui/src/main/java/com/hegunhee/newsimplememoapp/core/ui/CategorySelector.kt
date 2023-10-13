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

@Composable
fun CategorySelector(
    selectedCategory: String,
    onCategoryClick: (String) -> Unit,
    categoryList : List<String> = listOf("수입","지출"),
    categoryColor : List<Color> = listOf(Color.Blue,Color.Red)
) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(10.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        categoryList.zip(categoryColor) {  category, selectedColor ->
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
    onClick : (String) -> Unit,
    category : String,
    fontColor : Color
) {
    Button(
        onClick = {onClick(category)},
        modifier = Modifier.weight(1f).border(width = 1.dp, color = fontColor),
        shape = RectangleShape,
        colors = ButtonDefaults.buttonColors(containerColor = Color.White,contentColor = fontColor)
    ) {
        Text(text = category)
    }
}

@Preview
@Composable
private fun CategorySelector() {
    val (category, setCategory) = remember { mutableStateOf("") }
    CategorySelector(
        selectedCategory = category,
        onCategoryClick = setCategory
    )
}