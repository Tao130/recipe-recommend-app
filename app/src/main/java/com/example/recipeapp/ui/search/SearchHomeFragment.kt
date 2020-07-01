package com.example.recipeapp.ui.search

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.recipeapp.MyUtils
import com.example.recipeapp.R
import com.seesaa.kyt.reciperecommender.Recipes
import kotlinx.android.synthetic.main.fragment_search_home.*
import kotlinx.coroutines.runBlocking

/**
 * A simple [Fragment] subclass.
 */
class SearchHomeFragment : Fragment() {

    private lateinit var searchHomeViewModel: SearchHomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val searchViewModel = ViewModelProvider(this).get(SearchViewModel::class.java)
        runBlocking { searchViewModel.deleteAllSearchedRecipe() }

        return inflater.inflate(R.layout.fragment_search_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        searchHomeViewModel = ViewModelProvider(this).get(SearchHomeViewModel::class.java)

        searchHomeViewModel.isSearching.observe(
            viewLifecycleOwner,
            Observer { isSearching ->
                isSearching?.let {
                    val loadingText: View? = activity?.findViewById(R.id.now_loading)
                    val progressbar: View? = activity?.findViewById(R.id.progress_bar)
                    val searchButton: View? = view.findViewById(R.id.search_button)
                    if (isSearching) {
                        //　検索ボタン非表示
                        MyUtils.changeVisibility(
                            searchButton,
                            visibility = View.GONE
                        )
                        // 検索中の文字表示
                        MyUtils.changeVisibility(
                            loadingText,
                            progressbar,
                            visibility = View.VISIBLE
                        )
                    } else {
                        //　検索ボタン表示
                        MyUtils.changeVisibility(
                            searchButton,
                            visibility = View.VISIBLE
                        )
                        // 検索中の文字非表示
                        MyUtils.changeVisibility(
                            loadingText,
                            progressbar,
                            visibility = View.INVISIBLE
                        )
                    }
                }

            }
        )
        // インターネットに接続されているかどうかの確認
        val cm = context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: NetworkInfo? = cm.activeNetworkInfo
        val isConnected: Boolean = activeNetwork?.isConnectedOrConnecting == true
        MyUtils.changeVisibility(network_error_message, visibility = View.INVISIBLE)

        if (isConnected) {

            searchHomeViewModel.startSearching()

            // アプリ内DBへアクセスしてgRPC通信
            searchHomeViewModel.allIngredient.observe(
                viewLifecycleOwner,
                Observer { ingredients ->
                    ingredients?.let {

                        // サーバーと通信し、レスポンス取得
                        // その後SearchedRecipeインスタンスを作りDBに保存 まとめすぎか？
                        searchHomeViewModel.getResponse(ingredients)

                        // レスポンスを元にFragment描画
                        searchHomeViewModel.recipes.observe(
                            viewLifecycleOwner,
                            Observer { liveRecipes ->
                                adaptRecipePages(liveRecipes, view)
                            }
                        )

                    }
                })
        } else {
            searchHomeViewModel.finishSearching()
            network_error_message.apply {
                text = "インターネットに接続されていません"
                visibility = View.VISIBLE
            }
        }

        search_button.setOnClickListener {

            val action = SearchHomeFragmentDirections.actionSearchHomeFragmentSelf()
            view.findNavController().navigate(action)

        }


    }

    private fun adaptRecipePages(
        liveRecipes: Recipes,
        view: View
    ) {
        // 通信結果で場合分け
        when (liveRecipes.resultCode.toString()) {
            "SUCCESS" -> {
                searchHomeViewModel.adaptPage(
                    liveRecipes,
                    view,
                    childFragmentManager
                )
            }
            "ERROR_NO_RECIPE" -> {
                network_error_message.apply {
                    text = "オススメできるレシピがありませんでした"
                    visibility = View.VISIBLE
                }

            }
            "ERROR_INVALID_PARAMS" -> {
                network_error_message.apply {
                    text = "サーバーエラー"
                    visibility = View.VISIBLE
                }
            }
            "ERROR_INTERNAL" -> {
                network_error_message.apply {
                    text = "サーバーエラー"
                    visibility = View.VISIBLE
                }
            }
            else -> {
                network_error_message.apply {
                    text = "サーバーエラー"
                    visibility = View.VISIBLE
                }
            }
        }

        searchHomeViewModel.finishSearching()
    }

    // Fragmentが破棄された時点でprogressbarと検索中メッセージ非表示
    override fun onDestroy() {
        super.onDestroy()
        val loadingText: View? = activity?.findViewById(R.id.now_loading)
        val progressbar: View? = activity?.findViewById(R.id.progress_bar)
        MyUtils.changeVisibility(loadingText, progressbar, visibility = View.GONE)
    }

}
