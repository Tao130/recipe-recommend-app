package com.example.recipeapp.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.ViewPager
import com.example.recipeapp.*
import com.google.android.material.tabs.TabLayout

class HomeFragment : Fragment() {

    private lateinit var pagerAdapter: HomePagerAdapter
    private lateinit var viewPager: ViewPager

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val fragmentList = listOf(
            IngredientFragment().newInstance("material"),
            IngredientFragment().newInstance("unlike"),
            IngredientFragment().newInstance("spice")
        )
        pagerAdapter = HomePagerAdapter(
            childFragmentManager,
            fragmentList
        )
        viewPager = view.findViewById(R.id.pager)
        viewPager.adapter = pagerAdapter

        val tabLayout = view.findViewById<TabLayout>(R.id.tab_layout)
        tabLayout.setupWithViewPager(viewPager)
    }

}


