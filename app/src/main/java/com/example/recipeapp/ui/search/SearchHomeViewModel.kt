package com.example.recipeapp.ui.search

import android.app.Application
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.viewpager.widget.ViewPager
import com.example.recipeapp.R
import com.example.recipeapp.RecipeConverters
import com.example.recipeapp.database.AppDatabase
import com.example.recipeapp.database.Ingredient
import com.example.recipeapp.database.IngredientRepository
import com.example.recipeapp.database.SearchedRecipeRepository
import com.google.android.material.tabs.TabLayout
import com.seesaa.kyt.reciperecommender.Query
import com.seesaa.kyt.reciperecommender.Recipes
import com.seesaa.kyt.reciperecommender.SeekerGrpc
import io.grpc.ManagedChannelBuilder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SearchHomeViewModel(application: Application) : AndroidViewModel(application) {

    private val ingredientRepository: IngredientRepository
    val allIngredient: LiveData<List<Ingredient>>
    private val searchedRecipeRepository: SearchedRecipeRepository

    init {
        val ingredientDao = AppDatabase.getDatabase(
            application,
            viewModelScope
        ).ingredientDao()
        val searchedRecipeDao = AppDatabase.getDatabase(
            application,
            viewModelScope
        ).searchedRecipeDao()
        ingredientRepository = IngredientRepository(ingredientDao)
        allIngredient = ingredientRepository.allIngredient

        searchedRecipeRepository = SearchedRecipeRepository(searchedRecipeDao)
    }

    private val _recipes = MutableLiveData<Recipes>()
    val recipes: LiveData<Recipes>
        get() = _recipes

    private val _map = MutableLiveData<Map<String, MutableList<String>>>()
    private val map: LiveData<Map<String, MutableList<String>>>
        get() = _map

    private var _isSearching = MutableLiveData<Boolean>()
    val isSearching: LiveData<Boolean>
        get() = _isSearching

    fun startSearching() {
        _isSearching.value = true
    }

    fun finishSearching() {
        _isSearching.value = false
    }

    fun getResponse(ingredients: List<Ingredient>) {
        // ViewModelにアクセスしてDBに保存された材料のマップを作る
        getIngredientMap(ingredients)

        // LiveDataを引っ張ってきてクエリビルド、レスポンス取得
        val map = map.value
        if (map != null) {
            val query = Query.newBuilder()
                .addAllFavouriteFoods(map["material"])
                .addAllDislikeFoods(map["unlike"])
                .addAllStoredSpices(map["spice"])
                .setN(3).build()

            // 通信
            getRecipes(query)
        }
    }


    fun adaptPage(
        recipes: Recipes,
        view: View,
        childFragmentManager: FragmentManager
    ) {
        val pagerAdapter = SearchPagerAdapter(
            childFragmentManager,
            getFragmentsList(recipes)
        )

        val viewPager = view.findViewById<ViewPager>(R.id.pager)
        viewPager.adapter = pagerAdapter

        val tabLayout = view.findViewById<TabLayout>(R.id.tab_layout)
        tabLayout.setupWithViewPager(viewPager)
    }


    private fun getRecipes(query: Query) {
        viewModelScope.launch {
            // チャンネルとスタブのビルド
            val chan =
                ManagedChannelBuilder.forAddress("address-name", "port-number").build()
            val seekerService: SeekerGrpc.SeekerBlockingStub = SeekerGrpc.newBlockingStub(chan)

            try {
                val recipes =
                    withContext(Dispatchers.IO) {
                        seekerService.getRecommendedRecipe(query)
                    }
                _recipes.value = recipes

                for (recipe in recipes.recipeList) {
                    val searchedRecipe = RecipeConverters.fromRecipeToSearchedRecipe(recipe)
                    if (searchedRecipe != null) {
                        searchedRecipeRepository.insertSearchedRecipe(searchedRecipe)
                    }
                }
        } finally {
            chan.shutdown()
        }
    }
}

private fun getIngredientMap(ingredients: List<Ingredient>) {
    viewModelScope.launch {
        var materialList = mutableListOf<String>()
        var unlikeList = mutableListOf<String>()
        var spiceList = mutableListOf<String>()

        // Ingredientの振り分け
        for (ingredient in ingredients) {
            when (ingredient.ingredientType) {
                "material" -> materialList.add(ingredient.ingredientName)
                "unlike" -> unlikeList.add(ingredient.ingredientName)
                else -> spiceList.add(ingredient.ingredientName)
            }
        }

        materialList = insertBlank(materialList)
        unlikeList = insertBlank(unlikeList)
        spiceList = insertBlank(spiceList)

        _map.value =
            mapOf("material" to materialList, "unlike" to unlikeList, "spice" to spiceList)
    }
}

// 受け取ったレシピごとのフラグメントを作成
private fun getFragmentsList(recipes: Recipes): MutableList<Fragment> {
    val fragmentList: MutableList<Fragment> = mutableListOf()
    for (recipe in recipes.recipeList) {
        fragmentList.add(SearchFragment(recipe))
    }
    return fragmentList
}

private fun insertBlank(list: MutableList<String>): MutableList<String> {
    return if (list.size == 0) {
        list.add("")
        list
    } else {
        list
    }
}

}