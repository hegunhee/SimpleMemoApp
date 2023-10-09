package com.hegunhee.copose_memo

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable

fun NavGraphBuilder.memoNavGraph(
    paddingValues : PaddingValues,
) {
    composable(route = MemoNavGraph.memoRoute) {
        MemoScreenRoot()
    }
}

object MemoNavGraph {
    const val memoRoute = "Memo"
}