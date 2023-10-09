package com.hegunhee.compose_app

import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.sp
import androidx.navigation.NavBackStackEntry

@Composable
fun AppBottomNavigation(backStackEntry: State<NavBackStackEntry?>,onBottomClick : (Int) -> Unit) {
    BottomNavigation(
        backgroundColor = Color.White,
        contentColor = Color.Black
    ) {
        val currentRoute = backStackEntry.value?.destination?.route
        bottomNavItems.forEachIndexed{ index, item ->
            BottomNavigationItem(
                label = { Text(text = item.title,fontSize = 9.sp)},
                icon = { Icon(painter = painterResource(id = item.icon), contentDescription = item.title)},
                selectedContentColor = Color.Magenta,
                unselectedContentColor = Color.Black.copy(0.4f),
                alwaysShowLabel = true,
                selected = currentRoute == item.screenRoute,
                onClick = {onBottomClick(index)}
            )
        }
    }
}