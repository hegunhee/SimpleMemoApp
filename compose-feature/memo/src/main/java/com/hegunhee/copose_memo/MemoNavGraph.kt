package com.hegunhee.copose_memo

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.hegunhee.copose_memo.add.AddMemoScreenRoot
import com.hegunhee.copose_memo.detail.DetailMemoScreenRoot

fun NavController.navigateAddMemo() {
    navigate(MemoNavGraph.addMemoRoute)
}

fun NavController.navigateDetailMemo(memoId : String) {
    navigate(MemoNavGraph.detailMemoRoute(memoId = memoId))
}

fun NavGraphBuilder.memoNavGraph(
    paddingValues : PaddingValues,
    onBackClick : () -> Unit,
    onAddMemoClick : () -> Unit,
    onMemoClick : (String) -> Unit
) {
    composable(route = MemoNavGraph.memoRoute) {
        MemoScreenRoot(paddingValues,onAddMemoClick = onAddMemoClick,onMemoClick = onMemoClick)
    }
    composable(route = MemoNavGraph.addMemoRoute) {
        AddMemoScreenRoot(
            paddingValues,
            onBackButtonClick = onBackClick
        )
    }
    composable(
        route = MemoNavGraph.detailMemoRoute("{id}"),
        arguments = listOf(
            navArgument("id") {
                type = NavType.StringType
            }
        )
    ) { navBackStackEntry ->
        val memoId = navBackStackEntry.arguments?.getString("id")?.toIntOrNull() ?: -1
        DetailMemoScreenRoot(
            paddingValues,
            onBackButtonClick = onBackClick,
            memoId = memoId
        )
    }
}

object MemoNavGraph {
    const val memoRoute = "Memo"

    const val addMemoRoute = "AddMemo"
    
    fun detailMemoRoute(memoId : String) : String{
        return "$memoRoute/$memoId"
    }
}