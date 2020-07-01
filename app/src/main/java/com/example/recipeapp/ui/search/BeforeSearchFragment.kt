package com.example.recipeapp.ui.search

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.recipeapp.R

class BeforeSearchFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_before_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val searchButton: View = view.findViewById(R.id.search_button)

        val beforeSearchViewModel = ViewModelProvider(this).get(BeforeSearchViewModel::class.java)

        beforeSearchViewModel.allSearchedRecipe.observe(viewLifecycleOwner, Observer { recipeList ->
            val fragmentList: MutableList<Fragment> = mutableListOf()
            for (recipe in recipeList) {
                fragmentList.add(
                    beforeSearchViewModel.getFragmentFromRecipe(
                        recipe.recipeTitle,
                        recipe.materialList,
                        recipe.procedureList
                    )
                )
            }
            if (fragmentList.size != 0) {
                beforeSearchViewModel.adaptPage(view, childFragmentManager, fragmentList)
            }
        })

        searchButton.setOnClickListener {

            val action = BeforeSearchFragmentDirections.actionNavSearchToSearchHomeFragment()
            findNavController().navigate(action)

        }


    }

}