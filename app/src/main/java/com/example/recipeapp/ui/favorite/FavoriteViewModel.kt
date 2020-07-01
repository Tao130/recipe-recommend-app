package com.example.recipeapp.ui.favorite

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.recipeapp.database.AppDatabase
import com.example.recipeapp.database.SavedRecipe
import com.example.recipeapp.database.SavedRecipeRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FavoriteViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: SavedRecipeRepository
    val allSavedRecipe: LiveData<List<SavedRecipe>>

    init {
        val savedRecipeDao = AppDatabase.getDatabase(
            application,
            viewModelScope
        ).savedRecipeDao()
        repository =
            SavedRecipeRepository(savedRecipeDao)
        allSavedRecipe = repository.allSavedRecipe
    }

    fun insertSavedRecipe(recipe: SavedRecipe) = viewModelScope.launch(Dispatchers.IO) {
        repository.insertSavedRecipe(recipe)
    }

    fun deleteSavedRecipe(recipe: SavedRecipe) = viewModelScope.launch(Dispatchers.IO) {
        repository.deleteSavedRecipe(recipe)
    }

}