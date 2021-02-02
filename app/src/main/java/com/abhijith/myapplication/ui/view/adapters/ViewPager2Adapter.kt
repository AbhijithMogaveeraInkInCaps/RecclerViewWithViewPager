package com.abhijith.myapplication.ui.view.adapters

import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.abhijith.myapplication.R
import com.abhijith.myapplication.ui.view.MySimpleExoPlayer


class ViewPager2Adapter() : RecyclerView.Adapter<ViewPager2Adapter.PostViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        return PostViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.fragment_video, parent, false)
        )
    }

    val viewholderList :MutableList<PostViewHolder> = mutableListOf()
    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        holder.apply {
            viewholderList.add(this)
            myPosition = position
            val path = "android.resource://" + "com.abhijith.myapplication" + "/" + R.raw.videoplayback
            mySimpleExoPlayer.init(Uri.parse(path))
        }
    }

    override fun getItemCount(): Int {
        return 2
    }

    lateinit var currentViewHolder: PostViewHolder

    override fun onViewAttachedToWindow(holder: PostViewHolder) {
        super.onViewAttachedToWindow(holder)
        Log.e("OnAttach","true")
//        if(this::currentViewHolder.isInitialized){
//            currentViewHolder.mySimpleExoPlayer.pause()
//        }
        currentViewHolder = holder
//        holder.apply {
//            mySimpleExoPlayer.init(Uri.parse(""))
//            mySimpleExoPlayer.play()
//        }
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)

    }

    override fun onViewDetachedFromWindow(holder: PostViewHolder) {
        super.onViewDetachedFromWindow(holder)
        Log.e("OnAttach","false")
//        currentViewHolder.mySimpleExoPlayer.pause()
//        holder.mySimpleExoPlayer.play()
//        holder.apply {
//            mySimpleExoPlayer.pause()
//        }
    }

    fun abortAllOperation() {
        currentViewHolder.mySimpleExoPlayer.abort()
    }

    fun pauseAllOperations(){
        currentViewHolder.mySimpleExoPlayer.pause()
    }

    fun resumeAllOperation() {
        currentViewHolder.mySimpleExoPlayer.play()
    }

    class PostViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        var myPosition: Int = 0
        val mySimpleExoPlayer: MySimpleExoPlayer = v.findViewById(R.id.mySimpleExoPlayer)
    }
}

