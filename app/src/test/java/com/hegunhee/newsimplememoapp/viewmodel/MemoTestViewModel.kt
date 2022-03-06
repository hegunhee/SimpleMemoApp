package com.hegunhee.newsimplememoapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hegunhee.newsimplememoapp.data.Memo
import com.hegunhee.newsimplememoapp.domain.memoTest.AddMemoUseCase
import com.hegunhee.newsimplememoapp.domain.memoTest.DeleteAllMemoUseCase
import com.hegunhee.newsimplememoapp.domain.memoTest.DeleteMemoUseCase
import com.hegunhee.newsimplememoapp.domain.memoTest.GetAllMemoUseCase
import kotlinx.coroutines.joinAll
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