package com.example.khraw.Ui.FragmentUi

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

    @Suppress("DEPRECATION")

    class MyPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

        private val fragments = listOf(
            Fragment1(),
            Fragment2(),
            Fragment3()
        )

        override fun getCount(): Int {
            return fragments.size
        }

        override fun getItem(position: Int): Fragment {
            return fragments[position]
        }

        override fun getPageTitle(position: Int): CharSequence? {
            return when (position) {
                0 -> "Fragment 1"
                1 -> "Fragment 2"
                2 -> "Fragment 3"
                else -> null
            }
        }
    }