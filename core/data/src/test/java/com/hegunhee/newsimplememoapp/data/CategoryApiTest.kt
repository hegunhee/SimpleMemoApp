package com.hegunhee.newsimplememoapp.data

import com.hegunhee.newsimplememoapp.data.api.CategoryApi
import com.hegunhee.newsimplememoapp.data.api.dto.category.CategoryEntity
import com.hegunhee.newsimplememoapp.domain.model.category.CategoryType
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test

class CategoryApiTest {

    private val categoryApi : CategoryApi = provideCategoryApi()

    @Test
    fun `save category`() {
        runBlocking {
            val categoryEntity = CategoryEntity(CategoryType.ASSET, "식비")
            runCatching {
                categoryApi.save(categoryEntity)
            }.onSuccess { result ->
                println("name = " + result.name)
                Assert.assertEquals(categoryEntity.name,result.name)
            }.onFailure {
                println(it.message)
            }
            categoryApi.delete(categoryEntity.name)
        }
    }

    @Test
    fun `find category`() {
        runBlocking {
            val categoryEntity = CategoryEntity(CategoryType.ASSET, "식비")
            runCatching {
                categoryApi.save(categoryEntity)
                categoryApi.findOne(categoryEntity.name)
            }.onSuccess { result ->
                println("result = $result")
                Assert.assertEquals(categoryEntity,result)
            }.onFailure {
                println(it.message)
            }
            categoryApi.delete(categoryEntity.name)
        }
    }

    @Test
    fun `find empty category`() {
        runBlocking {
            val notSavedCategory = CategoryEntity(CategoryType.ASSET, "식비")
            runCatching {
                categoryApi.findOne(notSavedCategory.name)
            }.onSuccess { result ->

            }.onFailure {
                println(it.message)
                assert(true)
            }
        }
    }

    @Test
    fun `is category exists`() {
        runBlocking {
            val category = CategoryEntity(CategoryType.ASSET, "식비")
            runCatching {
                categoryApi.save(category)
                categoryApi.existsBy(category.name)
            }.onSuccess { result ->
                assert(result)
            }.onFailure {
                println(it.message)
                assert(true)
            }
            categoryApi.delete(category.name)
        }
    }

    @Test
    fun `is category not exists`() {
        runBlocking {
            val category = CategoryEntity(CategoryType.ASSET, "식비")
            runCatching {
                categoryApi.save(category)
                categoryApi.delete(category.name)
                categoryApi.existsBy(category.name)
            }.onSuccess { result ->
                assert(!result)
            }.onFailure {
                println(it.message)
                assert(true)
            }
        }
    }

    @Test
    fun `find categoryType ASSET categoryList`() {
        runBlocking {
            val category1 = CategoryEntity(CategoryType.ASSET, "식비")
            val category2 = CategoryEntity(CategoryType.ASSET, "용돈")
            val category3 = CategoryEntity(CategoryType.ATTR_INCOME, "소득")
            val categories = listOf(category1, category2, category3)
            runCatching {
                categories.forEach { categoryApi.save(it) }
                categoryApi.findAllBy(CategoryType.ASSET)
            }.onSuccess { result ->
                Assert.assertEquals(CategoryType.ASSET,result.type)
                Assert.assertEquals(2,result.names.size)
                println(result.names)
            }.onFailure {
                println(it.message)
                assert(true)
            }
            categories.forEach { categoryApi.delete(it.name) }
        }
    }
}