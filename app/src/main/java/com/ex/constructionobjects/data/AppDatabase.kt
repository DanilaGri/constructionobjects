package com.ex.constructionobjects.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ex.constructionobjects.data.model.Construction
import com.ex.constructionobjects.data.model.ConstructionDao

@Database(entities = [Construction::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun constructionDao(): ConstructionDao
}
