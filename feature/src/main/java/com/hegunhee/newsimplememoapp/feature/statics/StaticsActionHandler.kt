package com.hegunhee.newsimplememoapp.feature.statics

import com.hegunhee.newsimplememoapp.feature.common.DateSelectorActionHandler

interface StaticsActionHandler : DateSelectorActionHandler{

    fun onStaticsDetailClick(attr : String,year : Int,month : Int)

}