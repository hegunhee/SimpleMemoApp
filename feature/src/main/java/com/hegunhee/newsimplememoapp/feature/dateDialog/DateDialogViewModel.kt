package com.hegunhee.newsimplememoapp.feature.dateDialog

import androidx.lifecycle.ViewModel
import com.hegunhee.newsimplememoapp.feature.util.DateUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class DateDialogViewModel @Inject constructor() : ViewModel(){

    val year = MutableStateFlow(DateUtil.getYear().toString())

}