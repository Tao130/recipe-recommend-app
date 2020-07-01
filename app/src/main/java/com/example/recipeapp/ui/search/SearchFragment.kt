package com.example.recipeapp.ui.search

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.recipeapp.R
import com.example.recipeapp.RecipeConverters
import com.example.recipeapp.databinding.FragmentSearchBinding
import com.example.recipeapp.setOnSafeClickListener
import com.example.recipeapp.ui.favorite.FavoriteViewModel
import com.seesaa.kyt.reciperecommender.Recipe

/**
 * A simple [Fragment] subclass.
 */
class SearchFragment(private val recipe: Recipe) : Fragment() {

    lateinit var favoriteViewModel: FavoriteViewModel
    lateinit var searchViewModel: SearchViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentSearchBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_search, container, false
        )

        favoriteViewModel = ViewModelProvider(this).get(FavoriteViewModel::class.java)
        searchViewModel = ViewModelProvider(this).get(SearchViewModel::class.java)

        // searchedRecipeを作り、保存
        val searchedRecipe = RecipeConverters.fromRecipeToSearchedRecipe(recipe)

        // タイトル設定
        val recipeTitle = binding.recipeTitle
        recipeTitle.text = searchedRecipe?.recipeTitle

        // 材料リスト設定
        val materialListView = binding.materialList
        materialListView.text = searchedRecipe?.materialList

        // 料理手順設定
        val procedureListView = binding.procedureList
        procedureListView.text = searchedRecipe?.procedureList

        // FABが押された時動く
        searchViewModel.hasSavedRecipe.observe(viewLifecycleOwner, Observer { hasSavedRecipe ->
            if (hasSavedRecipe) {
                Toast.makeText(
                    context,
                    "このレシピは保存済みです",
                    Toast.LENGTH_LONG
                ).show()
            } else {
                // SavedRecipeインスタンスをTextViewから作る
                val recipeToSave = RecipeConverters.fromRecipeToSavedRecipe(recipe)
                // DBに保存
                if (recipeToSave != null) {
                    favoriteViewModel.insertSavedRecipe(recipeToSave)
                }

                Toast.makeText(
                    context,
                    "レシピをお気に入りに保存しました",
                    Toast.LENGTH_LONG
                ).show()

            }
        })

        // フローティングアクションボタンのクリックリスナー
        val fab = binding.floatingActionButton
        fab.setOnSafeClickListener {
            // 同じタイトルのレシピがお気に入りにあるかどうかチェックしてLiveData更新
            searchViewModel.hasSavedOrNewByTitle(recipe.title)
        }

        return binding.root
    }
}
