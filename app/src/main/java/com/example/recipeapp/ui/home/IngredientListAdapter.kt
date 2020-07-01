package com.example.recipeapp.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.recipeapp.database.Ingredient
import com.example.recipeapp.databinding.RecyclerviewItemBinding

class IngredientListAdapter(
    private val clickListener: IngredientListener
) : RecyclerView.Adapter<IngredientListAdapter.IngredientViewHolder>(){

    private var ingredients = emptyList<Ingredient>() // Cached copy of words

    class IngredientViewHolder(val binding: RecyclerviewItemBinding) : RecyclerView.ViewHolder(binding.root){

        fun bind(item: Ingredient, clickListener: IngredientListener) {
            binding.ingredient = item
            binding.clickListener = clickListener
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): IngredientViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = RecyclerviewItemBinding.inflate(layoutInflater, parent, false)
                return IngredientViewHolder(
                    binding
                )
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IngredientViewHolder {
        return IngredientViewHolder.from(
            parent
        )
    }

    override fun onBindViewHolder(holder: IngredientViewHolder, position: Int) {
        val current = ingredients[position]
        holder.bind(current, clickListener)
    }

    internal fun setIngredients(ingredients: List<Ingredient>) {
        this.ingredients = ingredients
        notifyDataSetChanged()
    }

    override fun getItemCount() = ingredients.size
}

class IngredientListener(val clickListener: (ingredient: Ingredient) -> Unit) {
    fun onClick(ingredient: Ingredient) = clickListener(ingredient)
}