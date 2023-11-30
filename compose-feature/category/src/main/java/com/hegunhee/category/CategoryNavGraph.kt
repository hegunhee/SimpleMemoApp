package com.hegunhee.category

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument

fun NavController.navigateAddCategory(categoryId : String) {
    navigate(CategoryNavGraph.detailCategoryRoute(categoryId = categoryId))
}

fun NavGraphBuilder.categoryNavGraph(
    paddingValues: PaddingValues,
    onBackClick : () -> Unit
) {
    composable(
        route = CategoryNavGraph.detailCategoryRoute("{categoryId}"),
        arguments = listOf(
            navArgument("categoryId") {
                type = NavType.StringType
            }
        )
    ) { navBackStackEntry ->
        val categoryId = navBackStackEntry.arguments?.getString("categoryId")?.toIntOrNull() ?: -1
        CategoryScreenRoot(
            paddingValues = paddingValues,
            onBackButtonClick = onBackClick,
            categoryId = categoryId
        )
    }
}

object CategoryNavGraph {

    fun detailCategoryRoute(categoryId : String) : String {
        return "Category/${categoryId}"
    }
}