package com.hegunhee.copose_memo.add

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hegunhee.newsimplememoapp.domain.model.category.CategoryType
import com.hegunhee.newsimplememoapp.domain.model.memo.IncomeExpenseType
import com.hegunhee.newsimplememoapp.domain.model.memo.MemoForm
import com.hegunhee.newsimplememoapp.domain.usecase.category.GetCategoryNamesByType
import com.hegunhee.newsimplememoapp.domain.usecase.memo.InsertServerMemoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalTime
import javax.inject.Inject

@HiltViewModel
class AddMemoViewModel @Inject constructor(
    private val insertMemoUseCase: InsertServerMemoUseCase,
    private val getCategoryNamesByTypeUseCase: GetCategoryNamesByType
) : ViewModel() {

    private val _memoForm: MutableStateFlow<MemoForm> = MutableStateFlow(MemoForm.init())
    val memoForm: StateFlow<MemoForm> = _memoForm.asStateFlow()

    val price: MutableStateFlow<String> = MutableStateFlow("")

    val description: MutableStateFlow<String> = MutableStateFlow("")

    val categoryList: MutableStateFlow<List<String>> = MutableStateFlow(emptyList())
    val categoryType: MutableStateFlow<CategoryType> = MutableStateFlow(CategoryType.EMPTY)

    fun setTime(time: LocalTime) {
        _memoForm.value = memoForm.value.updateTime(time)
    }

    fun setDate(date: LocalDate) {
        _memoForm.value = memoForm.value.updateDate(date)
    }

    fun setPrice(price: String) {
        this.price.value = price
    }

    fun setDescription(description: String) {
        this.description.value = description
    }

    fun setIncomeExpenseType(type: IncomeExpenseType) {
        val currentType = memoForm.value.incomeExpenseType
        if (type != currentType) {
            _memoForm.value = memoForm.value.copy(attribute = "", incomeExpenseType = type)
        }
    }

    fun setCategoryType(type: CategoryType) {
        viewModelScope.launch {
            categoryType.value = type
            getCategoryNamesByTypeUseCase(type).onSuccess { types ->
                categoryList.value = types.names
            }
        }
    }

    fun setSubCategoryItem(categoryType: CategoryType, categoryName: String) {
        when (categoryType) {
            CategoryType.ATTR_EXPENSE, CategoryType.ATTR_INCOME -> {
                _memoForm.value = memoForm.value.copy(attribute = categoryName)
            }

            CategoryType.ASSET -> {
                _memoForm.value = memoForm.value.copy(asset = categoryName)
            }

            else -> {}
        }
        this.categoryType.value = CategoryType.EMPTY
        this.categoryList.value = emptyList()
    }

    fun saveMemo(): Boolean {
        if (price.value.toIntOrNull() != null) {
            _memoForm.value = memoForm.value.updatePrice(price.value.toInt())
        }
        _memoForm.value = memoForm.value.updateDesc(description.value)

        val (isFullForm, emptyType) = memoForm.value.isFullForm()

        if (!isFullForm) {
            return false
        }

        viewModelScope.launch {
            insertMemoUseCase(memoForm.value)
        }
        return true
    }
}