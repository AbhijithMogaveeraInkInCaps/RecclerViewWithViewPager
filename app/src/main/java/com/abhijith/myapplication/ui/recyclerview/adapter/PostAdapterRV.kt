package com.abhijith.myapplication.ui.recyclerview.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.abhijith.myapplication.R
import com.abhijith.myapplication.ui.viewpager.adapter.PostContentAdapterVP

class PostAdapterRV(private val fragmentActivity: FragmentActivity) : RecyclerView.Adapter<PostAdapterRV.PostViewHolder>() {

    inner class PostViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        val vp:ViewPager2 = v.findViewById(R.id.vp_post_media)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        return PostViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.layour_post, parent, false)
        )
    }


    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        holder.apply {
            vp.apply {
                adapter = PostContentAdapterVP(fragmentActivity)
            }
        }
    }

    override fun getItemCount(): Int {
        return 3
    }

}