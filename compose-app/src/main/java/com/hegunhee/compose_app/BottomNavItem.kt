package com.hegunhee.compose_app

import com.hegunhee.copose_memo.MemoNavGraph
import com.hegunhee.statics.StaticsNavGraph

sealed class BottomNavItem(
    val title : String,val icon : Int, val screenRoute : String
) {
    object Memo : BottomNavItem("메모",com.hegunhee.common_resource.R.drawable.ic_memo,MemoNavGraph.memoRoute)
    object Statics : BottomNavItem("통계",com.hegunhee.common_resource.R.drawable.ic_statistics,StaticsNavGraph.statics)

}
val bottomNavItems = listOf<BottomNavItem>(BottomNavItem.Memo,BottomNavItem.Statics)