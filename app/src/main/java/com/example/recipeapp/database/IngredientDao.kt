package com.example.recipeapp.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface IngredientDao {

    @Query("SELECT * from ingredient_table")
    fun getAllIngredients(): LiveData<List<Ingredient>>

    @Query("SELECT * from ingredient_table WHERE type == 'material'")
    fun getAllMaterials(): LiveData<List<Ingredient>>

    @Query("SELECT * from ingredient_table WHERE type == 'unlike'")
    fun getAllUnlikes(): LiveData<List<Ingredient>>

    @Query("SELECT * from ingredient_table WHERE type == 'spice'")
    fun getAllSpices(): LiveData<List<Ingredient>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(ingredient: Ingredient)

    @Delete
    suspend fun deleteIngredient(vararg ingredients: Ingredient)

    @Query("DELETE FROM ingredient_table")
    suspend fun deleteAll()

    @Query("DELETE FROM ingredient_table WHERE type == 'material'")
    suspend fun deleteAllMaterials()

    @Query("DELETE FROM ingredient_table WHERE type == 'unlike'")
    suspend fun deleteAllUnlikes()

    @Query("DELETE FROM ingredient_table WHERE type == 'spice'")
    suspend fun deleteAllSpices()

    @Query("SELECT * FROM ingredient_table WHERE type == :type AND name == :name ")
    suspend fun getIngredient(type: String, name: String): Ingredient?
}