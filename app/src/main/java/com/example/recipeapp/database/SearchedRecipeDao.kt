package com.example.recipeapp.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface SearchedRecipeDao {
    @Query("SELECT * from searched_recipe_table")
    fun getAllSearchedRecipe(): LiveData<List<SearchedRecipe>>

    @Query("DELETE FROM searched_recipe_table")
    suspend fun deleteAll()

    @Insert
    suspend fun insertSearchedRecipe(searchedRecipe: SearchedRecipe)
}