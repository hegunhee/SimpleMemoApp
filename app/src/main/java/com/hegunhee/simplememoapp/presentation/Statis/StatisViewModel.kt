package com.hegunhee.simplememoapp.presentation.Statis

import androidx.lifecycle.viewModelScope
import com.hegunhee.simplememoapp.presentation.BaseViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class StatisViewModel : BaseViewModel() {
    override fun fetchData(): Job = viewModelScope.launch{

    }
}