package com.ex.constructionobjects.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "construction")
data class Construction(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String,
    val description: String,
    val area: String,
    val district: String,
    val floors: Int,
    val price: Double,
    val type: String,
    val preview: String
)
