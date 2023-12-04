package com.hegunhee.statics

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable

fun NavGraphBuilder.staticsNavGraph(
    paddingValues: PaddingValues
){
    composable(route = StaticsNavGraph.statics) {
        StaticsScreenRoot(paddingValues)
    }
}

object StaticsNavGraph {
    const val statics = "Statics"
}