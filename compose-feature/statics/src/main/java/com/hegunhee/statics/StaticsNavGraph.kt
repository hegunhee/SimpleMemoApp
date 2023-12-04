package com.hegunhee.statics

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.hegunhee.statics.detail.DetailStaticsScreenRoot

fun NavController.navigateDetailStatics(attr : String) {
    navigate(StaticsNavGraph.detailStaticsRoute(attr = attr))
}
fun NavGraphBuilder.staticsNavGraph(
    paddingValues: PaddingValues,
    onBackClick : () -> Unit,
    onDetailStaticsClick : (String) -> Unit,
    onMemoClick : (String) -> Unit
){
    composable(route = StaticsNavGraph.statics) {
        StaticsScreenRoot(
            paddingValues,
            onDetailStaticsClick = onDetailStaticsClick
        )
    }
    composable(
        route = StaticsNavGraph.detailStaticsRoute("{attr}"),
        arguments = listOf(
            navArgument("attr") {
                type = NavType.StringType
            }
        )
    ) { navBackStackEntry ->
        val attr = navBackStackEntry.arguments?.getString("attr")
        if(attr.isNullOrBlank()) {
            onBackClick()
        }else {
            DetailStaticsScreenRoot(
                paddingValues = paddingValues,
                attr = attr,
                onMemoClick = onMemoClick
            )
        }
    }
}

object StaticsNavGraph {
    const val statics = "Statics"

    fun detailStaticsRoute(attr : String) : String {
        return "$statics/$attr"
    }
}