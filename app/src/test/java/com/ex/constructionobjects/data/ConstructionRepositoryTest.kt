package com.ex.constructionobjects.data

import com.ex.constructionobjects.data.model.ConstructionDao
import com.ex.constructionobjects.data.model.ConstructionLocal
import com.ex.constructionobjects.data.model.toExternal
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.IsEqual
import org.junit.Test
import org.mockito.Mockito.*

@OptIn(ExperimentalCoroutinesApi::class)
class ConstructionRepositoryTest {

    private val testDispatcher = StandardTestDispatcher()

    private val constructionDao: ConstructionDao = mock(ConstructionDao::class.java)

    private val constructionRepository = ConstructionRepository(constructionDao)

    // Тестовые данные
    private val constructionList = listOf(
        ConstructionLocal(1, "Construction 1", "Description 1", "area 1", "District 1", 1,100.0,"Type", "Preview"),
        ConstructionLocal(2, "Construction 2", "Description 2", "area 2", "District 2", 2,200.0,"Type", "Preview"),
        ConstructionLocal(3, "Construction 3", "Description 3", "area 3", "District 3", 3,300.0,"Type", "Preview")
    )


    @Test
    fun `test getAllConstruction returns correct data`() = runTest  {
        // Устанавливаем поведение mock объекта
        `when`(constructionDao.getAllConstructions()).thenReturn(flowOf(constructionList))

        // Вызываем функцию, которую нужно протестировать
        val constructions = constructionRepository.getAllConstruction().first()

        // Проверяем результат
        assertThat(constructions,IsEqual(constructionList.map { it.toExternal() }))
    }

    @Test
    fun `test getConstructionById returns correct data`() = runTest(testDispatcher) {
        // Устанавливаем поведение mock объекта
        val construction = constructionList[0]
        `when`(constructionDao.getConstructionById(construction.id)).thenReturn(construction)

        // Вызываем функцию, которую нужно протестировать
        val result = constructionRepository.getConstructionById(construction.id)

        // Проверяем результат
        assertThat(result, IsEqual(construction))
    }

    @Test
    fun `test addNewConstruction inserts data correctly`() = runTest {
        // Вызываем функцию, которую нужно протестировать
        constructionRepository.addNewConstruction(constructionList[0])

        // Проверяем, что была вызвана соответствующая функция в mock объекте
        verify(constructionDao).insertConstruction(constructionList[0])
    }

    @Test
    fun `test updateConstruction updates data correctly`() = runTest {
        // Вызываем функцию, которую нужно протестировать
        constructionRepository.updateConstruction(constructionList[0])

        // Проверяем, что была вызвана соответствующая функция в mock объекте
        verify(constructionDao).updateConstruction(constructionList[0])
    }

    @Test
    fun `test deleteConstruction deletes data correctly`() =  runTest(testDispatcher)  {
        // Вызываем функцию, которую нужно протестировать
        constructionRepository.deleteConstruction(constructionList[0].toExternal())

        // Проверяем, что была вызвана соответствующая функция в mock объекте
        verify(constructionDao).deleteConstruction(constructionList[0].id)
    }

    @Test
    fun `test getConstructionByDistrict returns correct data`() = runTest {
        // Устанавливаем поведение mock объекта
        val district = "District 1"
        `when`(constructionDao.getConstructionByDistrict(district))
            .thenReturn(flowOf(constructionList.filter { it.district == district }))

        // Вызываем функцию, которую нужно протестировать
        val constructions = constructionRepository.getConstructionByDistrict(district).first()

        // Проверяем результат
        assertThat(constructions,
            IsEqual(constructionList.filter { it.district == district }.map { it.toExternal() }))
    }

    @Test
    fun `test getConstructionByPriceRange returns correct data`() = runTest {
        // Устанавливаем поведение mock объекта
        val minPrice = 150.0
        val maxPrice = 300.0
        `when`(constructionDao.getConstructionByPriceRange(minPrice, maxPrice))
            .thenReturn(flowOf(constructionList.filter { it.price in minPrice..maxPrice }))

        // Вызываем функцию, которую нужно протестировать
        val constructions = constructionRepository.getConstructionByPriceRange(minPrice, maxPrice).first()

        // Проверяем результат
        assertThat(constructions,IsEqual(constructionList
            .filter { it.price in minPrice..maxPrice }.map { it.toExternal() }))
    }

}