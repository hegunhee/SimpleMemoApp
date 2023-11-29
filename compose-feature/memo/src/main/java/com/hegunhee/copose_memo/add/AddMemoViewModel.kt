package com.hegunhee.copose_memo.add

import androidx.lifecycle.ViewModel
import com.hegunhee.compose_feature.util.DateUtil
import com.hegunhee.newsimplememoapp.domain.model.DateInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class AddMemoViewModel @Inject constructor() : ViewModel(){

    // 추후 하나의 UiState로 관리 예정
    private val _dateInfo : MutableStateFlow<DateInfo> = MutableStateFlow(DateInfo.emptyInfo)
    val dateInfo : StateFlow<DateInfo> = _dateInfo.asStateFlow()

    fun onSelectDateClick(year : Int,month : Int,day : Int) {
        val dayOfWeek = DateUtil.getDayOfWeek(year,month,day)
        _dateInfo.value = DateInfo(year,month,day,dayOfWeek)
    }
}