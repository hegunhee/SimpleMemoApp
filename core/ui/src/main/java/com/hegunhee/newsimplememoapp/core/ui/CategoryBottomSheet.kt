package com.hegunhee.newsimplememoapp.core.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hegunhee.newsimplememoapp.domain.model.CategoryType
import com.holix.android.bottomsheetdialog.compose.BottomSheetDialog

@Composable
fun CategoryBottomSheet(
    onDismissRequest : () -> Unit,
    selectedCategoryType : CategoryType,
    categoryList : List<String>,
    onSubCategoryClick : (CategoryType) -> Unit,
    onSubCategoryItemClick : (CategoryType, String) -> Unit,
    onAddSubCategoryClick : (String) -> Unit
) {

    BottomSheetDialog(onDismissRequest = {
        onDismissRequest()
        onSubCategoryClick(CategoryType.Empty)
    }) {
        Surface {
            Column(modifier = Modifier.fillMaxWidth()) {
                Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                    Text(text = selectedCategoryType.title,modifier = Modifier.weight(0.8f).padding(start = 10.dp), fontSize = 20.sp)
                    IconButton(onClick = {
                        onAddSubCategoryClick(selectedCategoryType.code.toString())
                    }) {
                        Icon(painter = painterResource(id = R.drawable.ic_draw),contentDescription = null)
                    }
                    IconButton(onClick = {
                        onDismissRequest()
                        onSubCategoryClick(CategoryType.Empty)
                    }) {
                        Icon(painter = painterResource(id = R.drawable.ic_exit),contentDescription = null)
                    }
                }
                LazyVerticalGrid(
                    columns = GridCells.Fixed(4)
                ) {
                    items(categoryList, key = { it }) { name ->
                        Text(text = name,modifier = Modifier.clickable {
                            onSubCategoryItemClick(selectedCategoryType,name)
                            onDismissRequest()
                        }.padding(vertical = 10.dp),fontSize = 20.sp, textAlign = TextAlign.Center)
                    }
                }
            }
        }
    }
}