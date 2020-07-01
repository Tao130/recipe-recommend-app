package com.example.recipeapp.ui.home

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter

class HomePagerAdapter(fm: FragmentManager, private var fragmentList: List<Fragment>) :
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
        return fragmentList.size
    }

    // ページのタイトルを取得
    override fun getPageTitle(position: Int): CharSequence {
        return when(position) {
            0 -> "食材"
            1 -> "苦手なもの"
            2 -> "調味料"
            else -> ""
        }
    }
}