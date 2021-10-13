package com.hegunhee.simplememoapp.presentation.showMemo

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.hegunhee.simplememoapp.data.Entity.accountItemEntity
import com.hegunhee.simplememoapp.presentation.BaseViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class ShowMemoViewModel() : BaseViewModel() {



    override fun fetchData(): Job =viewModelScope.launch {

    }

}