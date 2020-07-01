package com.example.recipeapp.ui.favorite

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.recipeapp.database.SavedRecipe
import com.example.recipeapp.databinding.FavoritelistItemBinding

class FavoriteListAdapter(
    private val clickListener: FavoriteListener
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var items: List<SavedRecipe> = mutableListOf()

    class FavoriteViewHolder constructor(val binding: FavoritelistItemBinding) : RecyclerView.ViewHolder(binding.root) {


        fun bind(recipe: SavedRecipe, clickListener: FavoriteListener) {
            binding.savedRecipe = recipe
            binding.clickListener = clickListener
            binding.executePendingBindings()

        }

        companion object {
            fun from(parent: ViewGroup): FavoriteViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = FavoritelistItemBinding.inflate(layoutInflater, parent, false)
                return FavoriteViewHolder(
                    binding
                )
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return FavoriteViewHolder.from(
            parent
        )
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun submitList(favoriteList: List<SavedRecipe>) {
        items = favoriteList
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder) {
            is FavoriteViewHolder -> {
                holder.bind(items[position], clickListener)
            }
        }
    }

}

class FavoriteListener(
    val clickListener1: (recipe: SavedRecipe) -> Unit,
    val clickListener2: (recipe: SavedRecipe) -> Unit
) {
    fun onClickDelete(savedRecipe: SavedRecipe) = clickListener1(savedRecipe)
    fun onClickDetail(savedRecipe: SavedRecipe) = clickListener2(savedRecipe)
}