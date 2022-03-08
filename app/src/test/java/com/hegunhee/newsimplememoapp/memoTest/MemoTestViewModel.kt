package com.hegunhee.newsimplememoapp.memoTest

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hegunhee.newsimplememoapp.data.Entity.Memo
import com.hegunhee.newsimplememoapp.domain.memoUsecase.*
import kotlinx.coroutines.launch

class MemoTestViewModel(
    private val addMemoUseCase: AddMemoUseCase,
    private val deleteMemoUseCase: DeleteMemoUseCase,
    private val getAllMemoUseCase: GetAllMemoUseCase,
    private val deleteAllMemoUseCase: DeleteAllMemoUseCase,
    private val addMemoListUseCase: AddMemoListUseCase
) : ViewModel() {
    var memos = listOf<Memo>()

    fun getAllMemo()  = viewModelScope.launch{
        memos = getAllMemoUseCase()
    }
    fun addMemo(memo : Memo) = viewModelScope.launch{
        addMemoUseCase(memo)
    }

    fun deleteAllMemo() = viewModelScope.launch {
        deleteAllMemoUseCase()
    }

    fun deleteMemo(memo : Memo) = viewModelScope.launch {
        deleteMemoUseCase(memo)
    }

    fun addMemos(memo : List<Memo>) = viewModelScope.launch {
        addMemoListUseCase(memo)
    }


}