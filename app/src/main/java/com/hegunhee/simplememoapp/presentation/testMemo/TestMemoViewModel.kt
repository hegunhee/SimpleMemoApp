package com.hegunhee.simplememoapp.presentation.testMemo

import androidx.lifecycle.viewModelScope
import com.hegunhee.simplememoapp.data.Entity.accountItemEntity
import com.hegunhee.simplememoapp.domain.product.DeleteOneMemoUseCase
import com.hegunhee.simplememoapp.presentation.BaseViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class TestMemoViewModel(val deleteOneMemoUseCase: DeleteOneMemoUseCase) : BaseViewModel() {
    lateinit var replaceModeData : accountItemEntity
    lateinit var type : String
    override fun fetchData(): Job = viewModelScope.launch{
    }

    fun deleteData() = viewModelScope.launch {
        deleteOneMemoUseCase(replaceModeData)
    }
}