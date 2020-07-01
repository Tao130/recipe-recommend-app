package com.example.recipeapp.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "searched_recipe_table")
data class SearchedRecipe(
    @ColumnInfo(name = "recipeTitle") val recipeTitle: String,
    @ColumnInfo(name = "materialList") val materialList: String,
    @ColumnInfo(name = "procedureList") val procedureList: String,
    @PrimaryKey(autoGenerate = true) val searchedRecipeId: Int = 0
)