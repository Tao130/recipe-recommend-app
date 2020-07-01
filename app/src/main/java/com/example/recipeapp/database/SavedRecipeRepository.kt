package com.example.recipeapp.database

class SavedRecipeRepository(private val savedRecipeDao: SavedRecipeDao) {

    val allSavedRecipe = savedRecipeDao.getAllSavedRecipe()

    suspend fun insertSavedRecipe(recipe: SavedRecipe) {
        savedRecipeDao.insertSavedRecipe(recipe)
    }

    suspend fun deleteSavedRecipe(recipe: SavedRecipe) {
        savedRecipeDao.deleteSavedRecipe(recipe)
    }

    suspend fun getSavedRecipeByTitle(recipeTitle: String): SavedRecipe? {
        return savedRecipeDao.getSavedRecipeByTitle(recipeTitle)
    }

}