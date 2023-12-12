package com.hegunhee.compose_app.ui

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.hegunhee.category.categoryNavGraph
import com.hegunhee.category.navigateAddCategory
import com.hegunhee.copose_memo.MemoNavGraph
import com.hegunhee.copose_memo.memoNavGraph
import com.hegunhee.copose_memo.navigateAddMemo
import com.hegunhee.copose_memo.navigateDetailMemo
import com.hegunhee.newsimplememoapp.core.designsystem.NewSimpleMemoAppTheme
import com.hegunhee.statics.navigateDetailStatics
import com.hegunhee.statics.staticsNavGraph
import kotlinx.coroutines.CoroutineScope

@Composable
fun SimpleMemoApp(
    simpleMemoAppScaffoldState: SimpleMemoAppScaffoldState = rememberSimpleMemoAppScaffoldState()
) {
    NewSimpleMemoAppTheme() {
        Scaffold(bottomBar = { AppBottomNavigation(visible = simpleMemoAppScaffoldState.shouldShowBottomNavigation(),currentItem = simpleMemoAppScaffoldState.currentTab, onBottomClick = simpleMemoAppScaffoldState::navigateBottomNavigation) }) { paddingValues ->
            NavHost(navController = simpleMemoAppScaffoldState.navController, startDestination = MemoNavGraph.memoRoute) {

                memoNavGraph(
                    paddingValues = paddingValues,
                    onBackClick = simpleMemoAppScaffoldState::popBackStack,
                    onAddMemoClick = simpleMemoAppScaffoldState::navigateAddMemo,
                    onMemoClick = simpleMemoAppScaffoldState::navigateDetailMemo,
                    onAddCategoryClick = simpleMemoAppScaffoldState::navigateAddCategory
                )

                staticsNavGraph(
                    paddingValues = paddingValues,
                    onBackClick = simpleMemoAppScaffoldState::popBackStack,
                    onDetailStaticsClick = simpleMemoAppScaffoldState::navigateDetailStatics,
                    onMemoClick = simpleMemoAppScaffoldState::navigateDetailMemo
                )

                categoryNavGraph(
                    paddingValues = paddingValues,
                    onBackClick = simpleMemoAppScaffoldState::popBackStack,
                )
            }
        }
    }
}


@Composable
fun rememberSimpleMemoAppScaffoldState(
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    navController : NavHostController = rememberNavController()
) : SimpleMemoAppScaffoldState {
    return remember(key1 = coroutineScope, key2 = navController) {
        SimpleMemoAppScaffoldState(
            coroutineScope,
            navController
        )
    }
}

class SimpleMemoAppScaffoldState(
    val coroutineScope : CoroutineScope,
    val navController : NavHostController
) {
    private val currentDestination: NavDestination?
        @Composable get() = navController
            .currentBackStackEntryAsState().value?.destination

    val currentTab : BottomNavItem?
        @Composable get() = currentDestination
            ?.route
            ?.let { bottomNavItems.find { item -> item.screenRoute == it } }

    fun popBackStack() {
        navController.popBackStack()
    }

    fun navigateBottomNavigation(index : Int) {
        navController.navigate(bottomNavItems[index].screenRoute) {
            navController.graph.startDestinationRoute?.let {
                popUpTo(it) { saveState = true}
            }
            launchSingleTop = true
            restoreState = true
        }
    }

    fun navigateAddMemo() {
        navController.navigateAddMemo()
    }

    fun navigateDetailMemo(memoId : String) {
        navController.navigateDetailMemo(memoId)
    }

    fun navigateAddCategory(categoryId : String) {
        navController.navigateAddCategory(categoryId)
    }

    fun navigateDetailStatics(attr : String) {
        navController.navigateDetailStatics(attr)
    }

    @Composable
    fun shouldShowBottomNavigation() : Boolean {
        val currentRoute = currentDestination?.route ?: return false
        return BottomNavItem.contains(currentRoute)
    }
}
