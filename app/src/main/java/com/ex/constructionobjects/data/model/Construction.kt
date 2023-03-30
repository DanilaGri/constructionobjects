package com.ex.constructionobjects.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey


//External for UI layer
data class Construction(
    val id: Long,
    val name: String,
    val preview: String
)

@Entity(tableName = "construction")
data class ConstructionLocal(
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

// Local to External
fun ConstructionLocal.toExternal() = Construction(
    id = id,
    name = name,
    preview = preview,
)

fun List<ConstructionLocal>.toExternal() = map(ConstructionLocal::toExternal)
