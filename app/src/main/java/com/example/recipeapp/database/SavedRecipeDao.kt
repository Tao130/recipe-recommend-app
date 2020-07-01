package com.example.recipeapp.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface SavedRecipeDao {

    @Insert
    suspend fun insertSavedRecipe(savedRecipe: SavedRecipe)

    @Delete
    suspend fun deleteSavedRecipe(vararg savedRecipe: SavedRecipe)

    @Query("SELECT * from saved_recipe_table")
    fun getAllSavedRecipe(): LiveData<List<SavedRecipe>>

    @Query("SELECT * FROM saved_recipe_table WHERE savedRecipeId == :recipeId")
    fun getSavedRecipe(recipeId: Int): SavedRecipe

    @Query("SELECT * FROM saved_recipe_table WHERE recipeTitle == :recipeTitle")
    suspend fun getSavedRecipeByTitle(recipeTitle: String): SavedRecipe?

}