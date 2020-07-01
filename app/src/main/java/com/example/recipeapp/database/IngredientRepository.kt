package com.example.recipeapp.database

import androidx.lifecycle.LiveData

class IngredientRepository(private val ingredientDao: IngredientDao) {

    val allMaterials: LiveData<List<Ingredient>> = ingredientDao.getAllMaterials()
    val allUnlikes: LiveData<List<Ingredient>> = ingredientDao.getAllUnlikes()
    val allSpices: LiveData<List<Ingredient>> = ingredientDao.getAllSpices()
    val allIngredient: LiveData<List<Ingredient>> = ingredientDao.getAllIngredients()

    suspend fun insert(ingredient: Ingredient) {
        ingredientDao.insert(ingredient)
    }

     suspend fun deleteAllMaterials() {
        ingredientDao.deleteAllMaterials()
    }

    suspend fun deleteAllUnlikes() {
        ingredientDao.deleteAllUnlikes()
    }

    suspend fun deleteAllSpices() {
        ingredientDao.deleteAllSpices()
    }

    suspend fun deleteIngredient(ingredient: Ingredient) {
        ingredientDao.deleteIngredient(ingredient)
    }

    suspend fun getIngredient(type: String, name: String): Ingredient? {
       return ingredientDao.getIngredient(type, name)
    }

}