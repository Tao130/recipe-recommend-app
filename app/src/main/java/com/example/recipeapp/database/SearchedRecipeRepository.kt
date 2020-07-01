package com.example.recipeapp.database

class SearchedRecipeRepository(private val searchedRecipeDao: SearchedRecipeDao) {
    val allSearchedRecipe = searchedRecipeDao.getAllSearchedRecipe()

    suspend fun deleteAllSearchedRecipe() = searchedRecipeDao.deleteAll()
    suspend fun insertSearchedRecipe(searchedRecipe: SearchedRecipe) = searchedRecipeDao.insertSearchedRecipe(searchedRecipe)

}