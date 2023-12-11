package com.hegunhee.newsimplememoapp.memoTest

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hegunhee.newsimplememoapp.data.entity.MemoEntity
import com.hegunhee.newsimplememoapp.domain.memoUsecase.*
import com.hegunhee.newsimplememoapp.domain.usecase.memo.DeleteAllMemoUseCase
import com.hegunhee.newsimplememoapp.domain.usecase.memo.DeleteMemoUseCase
import com.hegunhee.newsimplememoapp.domain.usecase.memo.InsertMemoListUseCase
import com.hegunhee.newsimplememoapp.domain.usecase.memo.InsertMemoUseCase
import kotlinx.coroutines.launch

class MemoTestViewModel(
    private val addMemoUseCase: InsertMemoUseCase,
    private val deleteMemoUseCase: DeleteMemoUseCase,
    private val getAllMemoUseCase: GetAllMemoUseCase,
    private val deleteAllMemoUseCase: DeleteAllMemoUseCase,
    private val insertMemoListUseCase: InsertMemoListUseCase,
    private val getMemoSortedByYearAndMonthUseCase: GetMemoSortedByYearAndMonthUseCase
) : ViewModel() {
    var memoEntities = listOf<MemoEntity>()

    fun getAllMemo()  = viewModelScope.launch{
        memoEntities = getAllMemoUseCase()
    }

    fun getMemoSortedByYearAndMonth(year : Int,month : Int) = viewModelScope.launch {
        memoEntities = getMemoSortedByYearAndMonthUseCase(year,month)
    }
    fun addMemo(memoEntity : MemoEntity) = viewModelScope.launch{
        addMemoUseCase(memoEntity)
    }

    fun deleteAllMemo() = viewModelScope.launch {
        deleteAllMemoUseCase()
    }

    fun deleteMemo(memoEntity : MemoEntity) = viewModelScope.launch {
        deleteMemoUseCase(memoEntity)
    }

    fun addMemos(memoEntity : List<MemoEntity>) = viewModelScope.launch {
        insertMemoListUseCase(memoEntity)
    }


}