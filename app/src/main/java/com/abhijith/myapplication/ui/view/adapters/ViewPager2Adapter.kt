package com.abhijith.myapplication.ui.view.adapters

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.abhijith.myapplication.R
import com.abhijith.myapplication.ui.view.MySimpleExoPlayer
import com.bumptech.glide.Glide


class ViewPager2Adapter(val context:Context) : RecyclerView.Adapter<ViewPager2Adapter.PostViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        return PostViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.fragment_video, parent, false)
        )
    }

    val viewHolderList :MutableList<PostViewHolder> = mutableListOf()
    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        holder.apply {
            viewHolderList.add(this)
            var str:String="https://wallpapercave.com/wp/wp1817745.jpg"
            Glide.with(context).load("empty")
                .thumbnail(Glide.with(context).load(str))
                .into(imageView);
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
        currentViewHolder = holder
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)

    }

    override fun onViewDetachedFromWindow(holder: PostViewHolder) {
        super.onViewDetachedFromWindow(holder)
    }

    fun abortAllOperation() {
        currentViewHolder.imageView.beVisible()
        currentViewHolder.mySimpleExoPlayer.abort()
    }

    fun pauseAllOperations(){
        currentViewHolder.imageView.beVisible()
        currentViewHolder.mySimpleExoPlayer.pause()
    }

    fun resumeAllOperation() {
        currentViewHolder.imageView.beInvisible()
        currentViewHolder.mySimpleExoPlayer.play()
    }

    class PostViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        var myPosition: Int = 0
        val imageView:ImageView = v.findViewById(R.id.thumbNail)
        val mySimpleExoPlayer: MySimpleExoPlayer = v.findViewById(R.id.mySimpleExoPlayer)
    }
}

