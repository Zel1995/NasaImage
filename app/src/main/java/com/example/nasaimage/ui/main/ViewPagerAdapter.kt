package com.example.nasaimage.ui.main

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.nasaimage.ui.main.subfragments.BeforeYesterdayFragment
import com.example.nasaimage.ui.main.subfragments.TodayFragment
import com.example.nasaimage.ui.main.subfragments.YesterdayFragment
import javax.inject.Inject

class ViewPagerAdapter @Inject constructor(private val fragmentManager: FragmentManager) :
    FragmentPagerAdapter(
        fragmentManager,
        BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT
    ) {
    val fragments = mutableListOf(TodayFragment(), YesterdayFragment(), BeforeYesterdayFragment())

    override fun getCount(): Int {
        return fragments.size
    }

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> {
                fragments[0]
            }
            1 -> {
                fragments[1]
            }
            2 -> {
                fragments[2]
            }
            else -> {
                fragments[0]
            }
        }
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return fragments[position]::class.simpleName
    }

}