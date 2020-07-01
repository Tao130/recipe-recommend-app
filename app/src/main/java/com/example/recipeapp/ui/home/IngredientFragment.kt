package com.example.recipeapp.ui.home

import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.recipeapp.R
import com.example.recipeapp.database.Ingredient
import com.example.recipeapp.databinding.FragmentIngredientBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.android.synthetic.main.fragment_ingredient.*

/**
 * A simple [Fragment] subclass.
 */
class IngredientFragment : Fragment() {

    private lateinit var ingredientViewModel: IngredientViewModel

    fun newInstance(type: String) : IngredientFragment {
        val bundle = Bundle()
        bundle.putString("type", type)
        val fragment = IngredientFragment()
            fragment.arguments = bundle
        return fragment
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding: FragmentIngredientBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_ingredient, container, false
        )

        val adapter =
            IngredientListAdapter(
                IngredientListener { ingredient ->
                    Toast.makeText(
                        context,
                        "${ingredient.ingredientName}を削除しました",
                        Toast.LENGTH_SHORT
                    )
                        .show()
                    ingredientViewModel.deleteIngredient(ingredient)
                }
            )

        val recyclerView = binding.recyclerview
        recyclerView.layoutManager = LinearLayoutManager(this.context)
        recyclerView.adapter = adapter

        ingredientViewModel = ViewModelProvider(this).get(IngredientViewModel::class.java)

        when (arguments?.getString("type")) {

            "material" -> {
                ingredientViewModel.allMaterials.observe(
                    viewLifecycleOwner,
                    Observer { ingredients ->
                        ingredients?.let { adapter.setIngredients(it) }
                    })
            }

            "unlike" -> {
                ingredientViewModel.allUnlikes.observe(
                    viewLifecycleOwner,
                    Observer { ingredients ->
                        ingredients?.let { adapter.setIngredients(it) }
                    })
            }
            else -> {
                ingredientViewModel.allSpices.observe(
                    viewLifecycleOwner,
                    Observer { ingredients ->
                        ingredients?.let { adapter.setIngredients(it) }
                    })
            }
        }

        // リストへの追加(追加ボタン)
        binding.inputButton.setOnClickListener {
            val type = arguments?.getString("type")
            addIngredient(type) // EditTextからIngredientインスタンスを作り、リストに追加
            editText.text.clear()
        }

        // リストへの追加(エンターキー)
        binding.editText.setOnKeyListener { _, keyCode, event ->
            if (event.action == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_ENTER) {
                val type = arguments?.getString("type")
                addIngredient(type) // EditTextからIngredientインスタンスを作り、リストに追加
                editText.text.clear()
                return@setOnKeyListener true
            }
            return@setOnKeyListener false
        }

        // リストから全てのレコードを削除
        binding.clearButton.setOnClickListener {
            val type = arguments?.getString("type")
            MaterialAlertDialogBuilder(context)
                .setMessage(resources.getString(R.string.alert_text))
                .setNegativeButton(resources.getString(R.string.cancel)) { _, _ ->
                }
                .setPositiveButton(resources.getString(R.string.accept)) { _, _ ->
                    when (type) {
                        "material" -> ingredientViewModel.deleteAllMaterials()
                        "unlike" -> ingredientViewModel.deleteAllUnlikes()
                        else -> ingredientViewModel.deleteAllSpices()
                    }
                }
                .show()
        }
        return binding.root
    }

    private fun addIngredient(type: String?) {
        val ingredientName = editText.text.toString()

        // 空白じゃないときのみDBに追加
        if(ingredientName != "") {
            val ingredient = type?.let { Ingredient(it, ingredientName) }
            // レコードを追加
            if (ingredient != null) {
                ingredientViewModel.insert(ingredient)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i("IngredientFragment" + arguments?.getString("type"), "onCreate called")
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        Log.i("IngredientFragment" + arguments?.getString("type"), "onActivityCreated called")
    }
    override fun onStart() {
        super.onStart()
        Log.i("IngredientFragment" + arguments?.getString("type"), "onStart called")
    }
    override fun onResume() {
        super.onResume()
        Log.i("IngredientFragment" + arguments?.getString("type"), "onResume called")
    }
    override fun onPause() {
        super.onPause()
        Log.i("IngredientFragment" + arguments?.getString("type"), "onPause called")
    }
    override fun onStop() {
        super.onStop()
        Log.i("IngredientFragment" + arguments?.getString("type"), "onStop called")
    }
    override fun onDestroyView() {
        super.onDestroyView()
        Log.i("IngredientFragment" + arguments?.getString("type"), "onDestroyView called")
    }
    override fun onDetach() {
        super.onDetach()
        Log.i("IngredientFragment" + arguments?.getString("type"), "onDetach called")
    }
}
