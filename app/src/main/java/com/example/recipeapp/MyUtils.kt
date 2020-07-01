package com.example.recipeapp

import android.view.View

class MyUtils {

    companion object {
        fun changeVisibility(vararg views: View?, visibility: Int) {
            for (view in views) {
                if (view != null) {
                    view.visibility = visibility
                }
            }
        }
    }

}