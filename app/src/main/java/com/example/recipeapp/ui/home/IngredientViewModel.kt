package com.example.recipeapp.ui.home

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.recipeapp.database.AppDatabase
import com.example.recipeapp.database.Ingredient
import com.example.recipeapp.database.IngredientRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class IngredientViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: IngredientRepository

    val allMaterials: LiveData<List<Ingredient>>
    val allUnlikes: LiveData<List<Ingredient>>
    val allSpices: LiveData<List<Ingredient>>

    init {
        val ingredientDao = AppDatabase.getDatabase(
            application,
            viewModelScope
        ).ingredientDao()
        repository =
            IngredientRepository(ingredientDao)
        allMaterials = repository.allMaterials
        allUnlikes = repository.allUnlikes
        allSpices = repository.allSpices
    }

    fun insert(ingredient: Ingredient) = viewModelScope.launch(Dispatchers.IO) {
        val type = ingredient.ingredientType
        val name = ingredient.ingredientName

        // アプリ内DBからIngredientを取ってくる
        val savedIngredient =
            withContext(Dispatchers.Default) {
                repository.getIngredient(type, name)
            }
        // 重複するIngredientがなければ保存
        if (savedIngredient == null) {
            repository.insert(ingredient)
        }
    }

    fun deleteAllMaterials() = viewModelScope.launch(Dispatchers.IO) {
        repository.deleteAllMaterials()
    }

    fun deleteAllUnlikes() = viewModelScope.launch(Dispatchers.IO) {
        repository.deleteAllUnlikes()
    }

    fun deleteAllSpices() = viewModelScope.launch(Dispatchers.IO) {
        repository.deleteAllSpices()
    }

    fun deleteIngredient(ingredient: Ingredient) = viewModelScope.launch(Dispatchers.IO) {
        repository.deleteIngredient(ingredient)
    }

}