package com.example.recipeapp.ui.favorite

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.recipeapp.R
import com.example.recipeapp.databinding.FragmentFavoriteBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class FavoriteFragment : Fragment() {

    lateinit var favoriteViewModel: FavoriteViewModel
    private lateinit var listAdapter: FavoriteListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding: FragmentFavoriteBinding = DataBindingUtil.inflate(inflater,
            R.layout.fragment_favorite, container, false)

        favoriteViewModel = ViewModelProvider(this).get(FavoriteViewModel::class.java)

        // RecyclerViewを整える
        val recyclerView = binding.recyclerview
        recyclerView.layoutManager = LinearLayoutManager(this.context)
        val topSpacingItemDecoration = TopSpacingItemDecoration(30)
        recyclerView.addItemDecoration(topSpacingItemDecoration)

        listAdapter = FavoriteListAdapter(
            FavoriteListener(

                // 削除アイコンのアクション
                { savedRecipe ->
                    //　ダイアログ表示
                    MaterialAlertDialogBuilder(context)
                        .setMessage("レシピ\n${savedRecipe.recipeTitle}\n\nを削除しますか？")
                        // 削除をやめる
                        .setNegativeButton(resources.getString(R.string.cancel)) { _, _ -> }
                        // 削除する
                        .setPositiveButton(resources.getString(R.string.accept)) { _, _ ->
                            // 削除のアクション
                            Toast.makeText(
                                context,
                                "${savedRecipe.recipeTitle}\n\nを削除しました",
                                Toast.LENGTH_LONG
                            )
                                .show()
                            favoriteViewModel.deleteSavedRecipe(savedRecipe)
                        }
                        .show()

                },

                // 詳細画面への遷移アクション
                { savedRecipe ->
                    val list: Array<String> = arrayOf(savedRecipe.recipeTitle, savedRecipe.materialList, savedRecipe.procedureList)
                    val action = FavoriteFragmentDirections.showDetail(list)
                    view?.findNavController()?.navigate(action)
                }
            )
        )


        recyclerView.adapter = listAdapter

        favoriteViewModel.allSavedRecipe.observe(
            viewLifecycleOwner,
            Observer { recipes ->
                recipes?.let {
                    listAdapter.submitList(it)
                    recyclerView.adapter = listAdapter
                }
            })

        return binding.root
    }
}
