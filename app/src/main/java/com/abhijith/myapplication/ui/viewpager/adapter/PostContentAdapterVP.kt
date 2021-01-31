package com.abhijith.myapplication.ui.viewpager.adapter

import android.util.Log
import android.util.SparseArray
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.abhijith.myapplication.fragments.VideoFragment

class PostContentAdapterVP(fragmentActivity: FragmentActivity)
    : FragmentStateAdapter(fragmentActivity) {

    val list: MutableList<Fragment> = mutableListOf()

    private val titles = arrayOf("Video", "Photo")

    override fun getItemCount(): Int {
        return titles.size
    }


    override fun createFragment(position: Int): Fragment {
        val fragment: Fragment = when (position) {
            0 -> {
                VideoFragment.newInstance(titles[0])
            }
            1 -> {
                VideoFragment.newInstance(titles[1])
            }
            else -> {
                VideoFragment.newInstance("")
            }
        }
        list.add(fragment)

        return fragment
    }

    fun abortAllOperation() {
        list.forEach {
            if(it.lifecycle.currentState==Lifecycle.State.RESUMED){
                if(it is VideoFragment){
                    it.abortAllOperation()
                }
            }
        }
    }

    fun resumeAllOperation(){
        list.forEach {
            if(it.lifecycle.currentState==Lifecycle.State.RESUMED){
                if(it is VideoFragment){
                    it.resumeAllOperation()
                }
            }
        }
    }
}