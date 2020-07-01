package com.example.recipeapp.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "ingredient_table")
class Ingredient(
    @ColumnInfo(name = "type") val ingredientType: String,
    @ColumnInfo(name = "name") val ingredientName: String,
    @PrimaryKey(autoGenerate = true) val ingredientId: Int = 0
)