package com.hegunhee.newsimplememoapp.memoTest

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hegunhee.newsimplememoapp.data.Entity.Memo
import com.hegunhee.newsimplememoapp.domain.memoUsecase.AddMemoUseCase
import com.hegunhee.newsimplememoapp.domain.memoUsecase.DeleteAllMemoUseCase
import com.hegunhee.newsimplememoapp.domain.memoUsecase.DeleteMemoUseCase
import com.hegunhee.newsimplememoapp.domain.memoUsecase.GetAllMemoUseCase
import kotlinx.coroutines.launch

class MemoTestViewModel(
    val addMemoUseCase: AddMemoUseCase,
    val deleteMemoUseCase: DeleteMemoUseCase,
    val getAllMemoUseCase: GetAllMemoUseCase,
    val deleteAllMemoUseCase: DeleteAllMemoUseCase
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


}