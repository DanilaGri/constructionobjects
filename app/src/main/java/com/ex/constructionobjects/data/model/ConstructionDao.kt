package com.ex.constructionobjects.data.model

import androidx.lifecycle.LiveData
import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface ConstructionDao {

    @Query("SELECT * FROM construction")
    fun getAllConstructions(): Flow<List<Construction>>

    @Query("SELECT * FROM construction WHERE id=:id")
    suspend fun getConstructionById(id: Long): Construction?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertConstruction(construction: Construction)

    @Update
    suspend fun updateConstruction(construction: Construction)

    @Delete
    suspend fun deleteConstruction(construction: Construction)

    @Query("SELECT * FROM construction WHERE district=:district")
    fun getConstructionByDistrict(district: String): Flow<List<Construction>>

    @Query("SELECT * FROM construction WHERE price BETWEEN :minPrice AND :maxPrice")
    fun getConstructionByPriceRange(minPrice: Double, maxPrice: Double):
            Flow<List<Construction>>
}