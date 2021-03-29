package com.jvmartinez.marvelinfo.ui.detailCharacter.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.jvmartinez.marvelinfo.ui.detailCharacter.fragment.comic.ComicsFragment
import com.jvmartinez.marvelinfo.ui.detailCharacter.fragment.events.EventsFragment
import com.jvmartinez.marvelinfo.ui.detailCharacter.fragment.serie.SerieFragment

class AdapterCharacter(private val countItems: Int, private val characterId: Int, fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount(): Int {
        return countItems
    }

    override fun createFragment(position: Int): Fragment {
       return when (position) {
            0 -> {
                 ComicsFragment.newInstance(characterId)
            }
            1 -> {
                 SerieFragment.newInstance("", "")
            }
            2 -> {
                 EventsFragment.newInstance("", "")
            }
           else -> {
               ComicsFragment.newInstance(characterId)
           }
       }
    }


}