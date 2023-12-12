package com.hegunhee.newsimplememoapp.feature.dateDialog

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hegunhee.newsimplememoapp.util.DateUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DateDialogViewModel @Inject constructor() : ViewModel(), DateDialogActionHandler {

    val year = MutableStateFlow(DateUtil.getYear().toString())

    private val _dateDialogNavigation : MutableSharedFlow<DateDialogNavigation> = MutableSharedFlow()
    val dateDialogNavigation : SharedFlow<DateDialogNavigation> = _dateDialogNavigation.asSharedFlow()

    override fun onPreviousYearClick() {
        year.value = (year.value.toInt() - 1).toString()
    }

    override fun onNextYearClick() {
        year.value = (year.value.toInt() + 1).toString()
    }

    override fun onCurrentMonthClick() {
        val currentMonth = DateUtil.getMonth()
        viewModelScope.launch {
            _dateDialogNavigation.emit(DateDialogNavigation.DisMissWithDate(year.value.toInt(),currentMonth))
        }
    }

    override fun onMonthClick(month: Int) {
        viewModelScope.launch {
            _dateDialogNavigation.emit(DateDialogNavigation.DisMissWithDate(year.value.toInt(),month))
        }
    }

    override fun onDismissButtonClick() {
        viewModelScope.launch {
            _dateDialogNavigation.emit(DateDialogNavigation.Dismiss)
        }
    }

}