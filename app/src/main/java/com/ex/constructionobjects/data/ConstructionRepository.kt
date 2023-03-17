package com.ex.constructionobjects.data

import com.ex.constructionobjects.data.model.Construction
import com.ex.constructionobjects.data.model.ConstructionDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ConstructionRepository @Inject constructor(private val constructionDao: ConstructionDao) {

    fun getAllConstruction(): Flow<List<Construction>> {
        return constructionDao.getAllConstructions()
    }

    suspend fun getConstructionById(id: Long) = constructionDao.getConstructionById(id)


    suspend fun addNewConstruction(constructionObject: Construction) {
        constructionDao.insertConstruction(constructionObject)
    }

    suspend fun updateConstruction(constructionObject: Construction) {
        constructionDao.updateConstruction(constructionObject)
    }

    suspend fun deleteConstruction(constructionObject: Construction) {
        constructionDao.deleteConstruction(constructionObject)
    }

    fun getConstructionByDistrict(district: String): Flow<List<Construction>> {
        return constructionDao.getConstructionByDistrict(district)
    }

    fun getConstructionByPriceRange(minPrice: Double, maxPrice: Double): Flow<List<Construction>> {
        return constructionDao.getConstructionByPriceRange(minPrice, maxPrice)
    }

    companion object {

        // For Singleton instantiation
        @Volatile private var instance: ConstructionRepository? = null

        fun getInstance(constructionDao: ConstructionDao) =
            instance ?: synchronized(this) {
                instance ?: ConstructionRepository(constructionDao).also { instance = it }
            }
    }
}