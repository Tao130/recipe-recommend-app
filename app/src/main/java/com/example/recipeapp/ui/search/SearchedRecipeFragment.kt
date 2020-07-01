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
import com.example.recipeapp.database.SavedRecipe
import com.example.recipeapp.databinding.FragmentSearchBinding
import com.example.recipeapp.setOnSafeClickListener
import com.example.recipeapp.ui.favorite.FavoriteViewModel

class SearchedRecipeFragment : Fragment() {

    fun newInstance(
        recipeTitle: String,
        materialList: String,
        procedureList: String
    ): SearchedRecipeFragment {
        val bundle = Bundle()
        bundle.putString("recipeTitle", recipeTitle)
        bundle.putString("materialList", materialList)
        bundle.putString("procedureList", procedureList)

        val fragment = SearchedRecipeFragment()
        fragment.arguments = bundle
        return fragment
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentSearchBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_search, container, false
        )

        val searchViewModel = ViewModelProvider(this).get(SearchViewModel::class.java)
        val favoriteViewModel = ViewModelProvider(this).get(FavoriteViewModel::class.java)

        val recipeTitle = arguments?.getString("recipeTitle")
        val materialList = arguments?.getString("materialList")
        val procedureList = arguments?.getString("procedureList")

        binding.recipeTitle.text = recipeTitle
        binding.materialList.text = materialList
        binding.procedureList.text = procedureList

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
                if (recipeTitle != null && materialList != null && procedureList != null) {
                    val recipeToSave = SavedRecipe(recipeTitle, materialList, procedureList)
                    // DBに保存
                    favoriteViewModel.insertSavedRecipe(recipeToSave)
                }
                Toast.makeText(
                    context,
                    "レシピをお気に入りに保存しました",
                    Toast.LENGTH_LONG
                ).show()
            }

        })

        val fab = binding.floatingActionButton
        fab.setOnSafeClickListener {
            // 同じタイトルのレシピがお気に入りにあるかどうかチェックしてLiveData更新
            if (recipeTitle != null) {
                searchViewModel.hasSavedOrNewByTitle(recipeTitle)
            }

        }

        return binding.root
    }
}
