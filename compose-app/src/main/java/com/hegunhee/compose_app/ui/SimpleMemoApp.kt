package com.hegunhee.compose_app.ui

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.hegunhee.copose_memo.MemoNavGraph
import com.hegunhee.copose_memo.memoNavGraph
import com.hegunhee.copose_memo.navigateAddMemo
import com.hegunhee.copose_memo.navigateDetailMemo
import com.hegunhee.newsimplememoapp.core.designsystem.NewSimpleMemoAppTheme
import com.hegunhee.statics.staticsNavGraph
import kotlinx.coroutines.CoroutineScope

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SimpleMemoApp(
    simpleMemoAppScaffoldState: SimpleMemoAppScaffoldState = rememberSimpleMemoAppScaffoldState()
) {
    NewSimpleMemoAppTheme() {
        Scaffold(bottomBar = { AppBottomNavigation(backStackEntry = simpleMemoAppScaffoldState.navController.currentBackStackEntryAsState(), onBottomClick = simpleMemoAppScaffoldState::navigateBottomNavigation) }) { paddingValues ->
            NavHost(navController = simpleMemoAppScaffoldState.navController, startDestination = MemoNavGraph.memoRoute) {

                memoNavGraph(
                    paddingValues = paddingValues,
                    onAddMemoClick = simpleMemoAppScaffoldState::navigateAddMemo,
                    onMemoClick = simpleMemoAppScaffoldState::navigateDetailMemo
                )

                staticsNavGraph(paddingValues)
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
}
