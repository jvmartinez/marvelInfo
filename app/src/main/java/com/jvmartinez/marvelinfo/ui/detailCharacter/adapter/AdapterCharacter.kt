package com.jvmartinez.marvelinfo.ui.detailCharacter.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.jvmartinez.marvelinfo.ui.detailCharacter.fragment.info.InfoFragment

class AdapterCharacter(private val countItems: Int, private val characterId: Int, fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount(): Int {
        return countItems
    }

    override fun createFragment(position: Int): Fragment {
       return when (position) {
            0 -> {
                 InfoFragment.newInstance(characterId, 0)
            }
            1 -> {
                InfoFragment.newInstance(characterId, 1)
            }
           else -> {
               InfoFragment.newInstance(characterId, 0)
           }
       }
    }


}