package com.abhijith.myapplication.ui.viewpager.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.abhijith.myapplication.fragments.VideoFragment

class PostContentAdapterVP(fragmentActivity: FragmentActivity) :FragmentStateAdapter(fragmentActivity) {

    private val titles = arrayOf("Video", "Photo")

    override fun getItemCount(): Int {
        return titles.size
    }

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0->{
                VideoFragment.newInstance(titles[0])
            }
            1->{
                VideoFragment.newInstance(titles[1])
            }
            else->{
                VideoFragment.newInstance("")
            }
        }
    }
}