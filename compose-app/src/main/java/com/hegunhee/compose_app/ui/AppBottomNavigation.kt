package com.hegunhee.compose_app.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.sp

@Composable
fun AppBottomNavigation(visible : Boolean, currentItem : BottomNavItem?, onBottomClick : (Int) -> Unit) {
    AnimatedVisibility(visible = visible) {
        BottomNavigation(
            backgroundColor = Color.White,
            contentColor = Color.Black
        ) {
            bottomNavItems.forEachIndexed{ index, item ->
                BottomNavigationItem(
                    label = { Text(text = item.title,fontSize = 9.sp)},
                    icon = { Icon(painter = painterResource(id = item.icon), contentDescription = item.title)},
                    selectedContentColor = Color.Magenta,
                    unselectedContentColor = Color.Black.copy(0.4f),
                    alwaysShowLabel = true,
                    selected = item == currentItem,
                    onClick = {onBottomClick(index)}
                )
            }
        }
    }
}