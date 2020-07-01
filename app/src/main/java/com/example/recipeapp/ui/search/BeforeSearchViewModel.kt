package com.example.recipeapp.ui.search

import android.app.Application
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import androidx.viewpager.widget.ViewPager
import com.example.recipeapp.R
import com.example.recipeapp.database.*
import com.google.android.material.tabs.TabLayout

class BeforeSearchViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: SearchedRecipeRepository
    val allSearchedRecipe: LiveData<List<SearchedRecipe>>

    init {
        val searchedRecipeDao = AppDatabase.getDatabase(
            application,
            viewModelScope
        ).searchedRecipeDao()
        repository = SearchedRecipeRepository(searchedRecipeDao)
        allSearchedRecipe = repository.allSearchedRecipe
    }

    fun adaptPage(
        view: View,
        childFragmentManager: FragmentManager,
        fragmentList: List<Fragment>
    ) {
        val pagerAdapter = SearchPagerAdapter(
            childFragmentManager,
            fragmentList
        )

        val viewPager = view.findViewById<ViewPager>(R.id.pager)
        viewPager.adapter = pagerAdapter

        val tabLayout = view.findViewById<TabLayout>(R.id.tab_layout)
        tabLayout.setupWithViewPager(viewPager)
    }

    fun getFragmentFromRecipe(recipeTitle: String, materialList: String, procedureList: String): SearchedRecipeFragment {
        return SearchedRecipeFragment().newInstance(recipeTitle, materialList, procedureList)
    }

}