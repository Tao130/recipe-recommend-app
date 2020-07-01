package com.example.recipeapp

import com.example.recipeapp.database.SavedRecipe
import com.example.recipeapp.database.SearchedRecipe
import com.seesaa.kyt.reciperecommender.Recipe

class RecipeConverters {

    companion object {

        fun fromRecipeToSearchedRecipe(recipe: Recipe?): SearchedRecipe? {
            return recipe?.let {
                SearchedRecipe(
                    makeRecipeTitle(it),
                    makeMaterialText(it),
                    makeProcedureText(it.proceduresList)
                )
            }
        }

        fun fromRecipeToSavedRecipe(recipe: Recipe?): SavedRecipe? {
            return recipe?.let {
                SavedRecipe(
                    makeRecipeTitle(it),
                    makeMaterialText(it),
                    makeProcedureText(it.proceduresList)
                )
            }
        }

        private fun makeRecipeTitle(recipe: Recipe): String {
            return recipe.title.toString()
        }

        private fun makeMaterialText(recipe: Recipe): String {
            val materialList: MutableList<String> = mutableListOf()
            for (material in recipe.requiredMaterialsList) {
                materialList.add(material.name + "\t" + material.unit)
            }
            var text = ""
            for (value in materialList) {
                text = text + value + "\n"
            }
            return text
        }

        private fun makeProcedureText(list: MutableList<String>): String {
            var text = ""
            for ((index, value) in list.withIndex()) {
                text = text + (index + 1).toString() + "\t" + value + "\n\n"
            }
            return text
        }

    }

}