package com.hegunhee.simplememoapp.presentation.Main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.hegunhee.simplememoapp.presentation.BaseViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

internal class MainViewModel : BaseViewModel() {

    private var _liveData = MutableLiveData<MainState>(MainState.Uninitalized)
    val liveData = _liveData

    private fun setData(state : MainState) {
        _liveData.postValue(state)
    }
    override fun fetchData(): Job = viewModelScope.launch {
        setData(MainState.Loading)
    }
}