package com.example.recipeapp.ui.search

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.recipeapp.database.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SearchViewModel(application: Application) : AndroidViewModel(application) {

    private val savedRecipeRepository: SavedRecipeRepository
    private val searchedRecipeRepository: SearchedRecipeRepository

    init {
        val savedRecipeDao = AppDatabase.getDatabase(
            application,
            viewModelScope
        ).savedRecipeDao()

        val searchedRecipeDao = AppDatabase.getDatabase(
            application,
            viewModelScope
        ).searchedRecipeDao()

        savedRecipeRepository = SavedRecipeRepository(savedRecipeDao)
        searchedRecipeRepository = SearchedRecipeRepository(searchedRecipeDao)
    }

    private var _hasSavedRecipe = MutableLiveData<Boolean>()
    val hasSavedRecipe: LiveData<Boolean>
        get() = _hasSavedRecipe

    fun hasSavedOrNewByTitle(recipeTitle: String) {
        viewModelScope.launch(Dispatchers.Main) {
            val savedRecipe = withContext(Dispatchers.IO) {
                savedRecipeRepository.getSavedRecipeByTitle(recipeTitle)
            }
            _hasSavedRecipe.value = savedRecipe != null //savedRecipeがnullならfalse
        }
    }

    fun deleteAllSearchedRecipe() = viewModelScope.launch(Dispatchers.IO) {
        searchedRecipeRepository.deleteAllSearchedRecipe()
    }

}