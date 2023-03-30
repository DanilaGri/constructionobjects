package com.ex.constructionobjects.data.model

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface ConstructionDao {

    @Query("SELECT * FROM construction")
    fun getAllConstructions(): Flow<List<ConstructionLocal>>

    @Query("SELECT * FROM construction WHERE id=:id")
    suspend fun getConstructionById(id: Long): ConstructionLocal?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertConstruction(construction: ConstructionLocal)

    @Update
    suspend fun updateConstruction(construction: ConstructionLocal)

    @Query("DELETE FROM construction WHERE id=:id")
    suspend fun deleteConstruction(id: Long)

    @Query("SELECT * FROM construction WHERE district=:district")
    fun getConstructionByDistrict(district: String): Flow<List<ConstructionLocal>>

    @Query("SELECT * FROM construction WHERE price BETWEEN :minPrice AND :maxPrice")
    fun getConstructionByPriceRange(minPrice: Double, maxPrice: Double):
        Flow<List<ConstructionLocal>>
}
