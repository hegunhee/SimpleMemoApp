package com.hegunhee.simplememoapp.presentation.testMemo

import androidx.lifecycle.viewModelScope
import com.hegunhee.simplememoapp.data.Entity.accountItemEntity
import com.hegunhee.simplememoapp.domain.product.DeleteOneMemoUseCase
import com.hegunhee.simplememoapp.domain.product.InsertOneMemoUseCase
import com.hegunhee.simplememoapp.presentation.BaseViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class TestMemoViewModel(
    val deleteOneMemoUseCase: DeleteOneMemoUseCase,
    val insertOneMemoUseCase: InsertOneMemoUseCase
) : BaseViewModel() {
    lateinit var replaceModeData: accountItemEntity
    lateinit var type: String
    override fun fetchData(): Job = viewModelScope.launch {
    }

    fun deleteData() = viewModelScope.launch {
        deleteOneMemoUseCase(replaceModeData)
    }

    fun addData(accountItemEntity: accountItemEntity) = viewModelScope.launch {
        insertOneMemoUseCase(accountItemEntity)
    }

    fun replaceData(accountItemEntity: accountItemEntity) = viewModelScope.launch {
        accountItemEntity?.let {
            insertOneMemoUseCase(
                replaceModeData.copy(
                    it.category,
                    it.day,
                    it.time,
                    it.asset,
                    it.attr,
                    it.price,
                    it.description
                )
            )
        }
    }
}