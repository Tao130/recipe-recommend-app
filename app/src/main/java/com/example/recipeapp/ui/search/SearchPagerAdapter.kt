package com.example.recipeapp.ui.search

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter

class SearchPagerAdapter(fm: FragmentManager, private var fragmentList: List<Fragment>) :
    FragmentStatePagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    init {
        getItem(0)
    }
    // どのFragmentを表示させるか
    override fun getItem(position: Int): Fragment {
        return fragmentList[position]
    }

    // viewPagerにセットするコンテンツ(フラグメントリスト)のサイズ
    override fun getCount(): Int {
        return 3
    }

    // ページのタイトルを取得
    override fun getPageTitle(position: Int): CharSequence {
        return when(position) {
            0 -> "レシピ１"
            1 -> "レシピ２"
            2 -> "レシピ３"
            else -> ""
        }
    }
}