package com.hegunhee.simplememoapp.presentation.Memo

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.hegunhee.simplememoapp.data.Entity.accountItemEntity
import com.hegunhee.simplememoapp.domain.product.GetAllMemoUseCase
import com.hegunhee.simplememoapp.domain.product.InsertOneMemoUseCase
import com.hegunhee.simplememoapp.presentation.BaseViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

internal class MemoViewModel(val getAllMemoUseCase: GetAllMemoUseCase, val insertOneMemoUseCase : InsertOneMemoUseCase) : BaseViewModel() {

    private var _liveData = MutableLiveData<MemoState>(MemoState.Uninitalized)
    val liveData = _liveData

    private fun setData(state : MemoState) {
        _liveData.postValue(state)
    }
    override fun fetchData(): Job = viewModelScope.launch {
        setData(MemoState.Loading)
        setData(MemoState.Success(getAllMemoUseCase()))
    }
    fun addEntity(entity : accountItemEntity) : Job = viewModelScope.launch {
        setData(MemoState.Loading)
        insertOneMemoUseCase(entity)
        fetchData()
    }
}