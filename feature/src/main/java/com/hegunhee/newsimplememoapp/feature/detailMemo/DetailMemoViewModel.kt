package com.hegunhee.newsimplememoapp.feature.detailMemo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hegunhee.newsimplememoapp.feature.common.category.CategoryActionHandler
import com.hegunhee.newsimplememoapp.domain.model.category.CategoryType
import com.hegunhee.newsimplememoapp.domain.model.memo.IncomeExpenseType
import com.hegunhee.newsimplememoapp.domain.model.memo.MemoForm
import com.hegunhee.newsimplememoapp.domain.model.memo.Memo
import com.hegunhee.newsimplememoapp.domain.usecase.category.GetCategoryNamesByTypeUseCase
import com.hegunhee.newsimplememoapp.domain.usecase.memo.DeleteMemoUseCase
import com.hegunhee.newsimplememoapp.domain.usecase.memo.GetMemoUseCase
import com.hegunhee.newsimplememoapp.domain.usecase.memo.UpdateMemoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalTime
import javax.inject.Inject

@HiltViewModel
class DetailMemoViewModel @Inject constructor(
    private val getServerMemoUseCase : GetMemoUseCase,
    private val deleteServerMemoUseCase : DeleteMemoUseCase,
    private val updateServerMemoUseCase : UpdateMemoUseCase,
    private val getCategoryNamesByTypeUseCase : GetCategoryNamesByTypeUseCase
) : ViewModel(), CategoryActionHandler {

    private val memoId : MutableStateFlow<Int> = MutableStateFlow(0)

    private val _memoForm : MutableStateFlow<MemoForm> = MutableStateFlow(MemoForm.init())
    val memoForm : StateFlow<MemoForm> = _memoForm.asStateFlow()

    val price = MutableStateFlow("")
    val desc : MutableStateFlow<String> = MutableStateFlow("")

    private val _memoState : MutableSharedFlow<DetailMemoState> = MutableSharedFlow()
    val memoState : SharedFlow<DetailMemoState> = _memoState.asSharedFlow()

    val categoryList : MutableStateFlow<List<String>> = MutableStateFlow(emptyList())
    val categoryType : MutableStateFlow<CategoryType> = MutableStateFlow(CategoryType.EMPTY)

    private val _detailCategoryNavigation : MutableSharedFlow<CategoryType> = MutableSharedFlow()
    val detailCategoryNavigation : SharedFlow<CategoryType> = _detailCategoryNavigation.asSharedFlow()

    fun initMemo(id : Int) {
        if(this.memoId.value != 0) return
        viewModelScope.launch {
            getServerMemoUseCase(id)
                .onSuccess { memo ->
                    memoId.value = memo.id
                    _memoForm.value = memo.toMemoForm()
                    price.value = memo.price.intValueExact().toString()
                    desc.value = memo.description
                }.onFailure {

                }
        }
    }

    private fun Memo.toMemoForm() : MemoForm {
        return MemoForm(memoDate,incomeExpenseType,attribute,asset,description, price)
    }

    fun setTime(time : LocalTime) {
        _memoForm.value = memoForm.value.updateTime(time)
    }

    fun setDate(date : LocalDate) {
        _memoForm.value = memoForm.value.updateDate(date)
    }

    fun setIncomeExpenseType(type : IncomeExpenseType) {
        val currentType = memoForm.value.incomeExpenseType
        if(type != currentType) {
            _memoForm.value = memoForm.value.copy(attribute = "", incomeExpenseType = type)
        }
        dismissBottomSheet()
    }

    fun clickUpdate() = viewModelScope.launch{
        if(memoId.value == 0) {
            return@launch
        }

        if(price.value.toIntOrNull() != null) {
            _memoForm.value = memoForm.value.updatePrice(price.value.toInt())
        }
        _memoForm.value = memoForm.value.updateDesc(desc.value)

        val (isFullForm,emptyType) = memoForm.value.isFullForm()
        if(isFullForm) {
            updateMemo()
        }
        when (emptyType) {
            "asset" -> { clickAsset() }
            "attribute" -> { clickAttr() }
            "price" -> { _memoState.emit(DetailMemoState.SetPrice) }
        }
    }

    private suspend fun updateMemo() {
        updateServerMemoUseCase(memoId.value,memoForm.value)
            .onSuccess {
                _memoState.emit(DetailMemoState.Update)
            }.onFailure {

            }
    }

    fun clickRemove() = viewModelScope.launch{
        if(memoId.value != 0) {
            deleteServerMemoUseCase(memoId.value)
                .onSuccess {
                    _memoState.emit(DetailMemoState.Remove)
                }.onFailure {

                }
        }
    }

    fun clickAsset() = viewModelScope.launch {
        getCategoryNamesByTypeUseCase(CategoryType.ASSET)
            .onSuccess {
                categoryList.value = it.names
                categoryType.value = it.type
            }.onFailure {

            }
    }

    fun clickAttr() = viewModelScope.launch {
        val type = if(memoForm.value.incomeExpenseType == IncomeExpenseType.INCOME) {
            CategoryType.ATTR_INCOME
        }else {
            CategoryType.ATTR_EXPENSE
        }
        getCategoryNamesByTypeUseCase(type)
            .onSuccess {
                categoryList.value = it.names
                categoryType.value = it.type
            }.onFailure {

            }
    }

    override fun onBottomSheetDismiss() {
        dismissBottomSheet()
    }

    override fun onCategoryAdd(categoryType: CategoryType) {
        viewModelScope.launch {
            _detailCategoryNavigation.emit(categoryType)
        }
    }

    override fun onCategoryClick(type: CategoryType, category: String) {
        when(type) {
            CategoryType.EMPTY -> {}
            CategoryType.ASSET -> {
                _memoForm.value = memoForm.value.copy(asset = category)
            }
            CategoryType.ATTR_EXPENSE -> {
                _memoForm.value = memoForm.value.copy(attribute = category)
            }
            CategoryType.ATTR_INCOME -> {
                _memoForm.value = memoForm.value.copy(attribute = category)
            }
        }
        dismissBottomSheet()
    }

    private fun dismissBottomSheet() {
        categoryList.value = emptyList()
        categoryType.value = CategoryType.EMPTY
    }

    fun refreshCategory() = viewModelScope.launch{
        if(categoryType.value !=  CategoryType.EMPTY) {
            getCategoryNamesByTypeUseCase(categoryType.value)
                .onSuccess { categoryNames ->
                    categoryList.value = categoryNames.names
                }
        }
    }
}