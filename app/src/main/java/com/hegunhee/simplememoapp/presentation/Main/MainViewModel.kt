package com.hegunhee.simplememoapp.presentation.Main

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.hegunhee.simplememoapp.data.Entity.accountItemEntity
import com.hegunhee.simplememoapp.domain.product.GetAllMemoUseCase
import com.hegunhee.simplememoapp.domain.product.InsertOneMemoUseCase
import com.hegunhee.simplememoapp.model.Repository
import com.hegunhee.simplememoapp.presentation.BaseViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

internal class MainViewModel(val getAllMemoUseCase: GetAllMemoUseCase,val insertOneMemoUseCase : InsertOneMemoUseCase) : BaseViewModel() {

    private var _liveData = MutableLiveData<MainState>(MainState.Uninitalized)
    val liveData = _liveData

    private fun setData(state : MainState) {
        _liveData.postValue(state)
    }
    override fun fetchData(): Job = viewModelScope.launch {
        setData(MainState.Loading)
        setData(MainState.Success(getAllMemoUseCase()))
    }
    fun addEntity(entity : accountItemEntity) : Job = viewModelScope.launch {
        setData(MainState.Loading)
        insertOneMemoUseCase(entity)
        fetchData()
    }
}