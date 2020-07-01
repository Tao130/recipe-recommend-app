package com.example.recipeapp.ui.favorite

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.navArgs

import com.example.recipeapp.R
import com.example.recipeapp.databinding.FragmentFavoriteDetailBinding

class FavoriteDetailFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentFavoriteDetailBinding = DataBindingUtil.inflate(inflater,
            R.layout.fragment_favorite_detail, container, false)

        val safeArgs: FavoriteDetailFragmentArgs by navArgs()
        val recipe: Array<String> = safeArgs.recipe

        binding.recipeTitle.text = recipe[0]
        binding.materialList.text = recipe[1]
        binding.procedureList.text = recipe[2]

        return binding.root
    }
}
