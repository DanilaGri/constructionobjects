package com.ex.constructionobjects.di

import android.content.Context
import androidx.room.Room
import com.ex.constructionobjects.data.AppDatabase
import com.ex.constructionobjects.data.model.ConstructionDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DatabaseModule {

    @Provides
    fun provideConstructionDao(database: AppDatabase): ConstructionDao {
        return database.constructionDao()
    }

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext appContext: Context): AppDatabase {
        return Room.databaseBuilder(
            appContext,
            AppDatabase::class.java,
            "construction.db"
        ).build()
    }
}
