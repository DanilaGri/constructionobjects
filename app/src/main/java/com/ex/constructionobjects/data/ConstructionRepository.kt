package com.ex.constructionobjects.data

import com.ex.constructionobjects.data.model.Construction
import com.ex.constructionobjects.data.model.ConstructionDao
import com.ex.constructionobjects.data.model.ConstructionLocal
import com.ex.constructionobjects.data.model.toExternal
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ConstructionRepository @Inject constructor(private val constructionDao: ConstructionDao) {

    fun getAllConstruction(): Flow<List<Construction>> {
        return constructionDao.getAllConstructions().map { it.toExternal() }
    }

    suspend fun getConstructionById(id: Long) = constructionDao.getConstructionById(id)

    suspend fun addNewConstruction(constructionObject: ConstructionLocal) {
        constructionDao.insertConstruction(constructionObject)
    }

    suspend fun updateConstruction(constructionObject: ConstructionLocal) {
        constructionDao.updateConstruction(constructionObject)
    }

    suspend fun deleteConstruction(constructionObject: Construction) {
        constructionDao.deleteConstruction(constructionObject.id)
    }

    fun getConstructionByDistrict(district: String): Flow<List<Construction>> {
        return constructionDao.getConstructionByDistrict(district).map { it.toExternal() }
    }

    fun getConstructionByPriceRange(minPrice: Double, maxPrice: Double): Flow<List<Construction>> {
        return constructionDao.getConstructionByPriceRange(minPrice, maxPrice).map { it.toExternal() }
    }
}
