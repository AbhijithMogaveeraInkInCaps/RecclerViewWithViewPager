package com.abhijith.myapplication.ui.view.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.abhijith.myapplication.R
import com.abhijith.myapplication.ui.statemodel.RecyclerViewStateModel
import com.abhijith.myapplication.ui.view.MySimpleExoPlayer
import com.bumptech.glide.Glide
import com.google.android.exoplayer2.C


class ViewPager2Adapter(
    val model: RecyclerViewStateModel.ViewHolderData
) : RecyclerView.Adapter<ViewPager2Adapter.PostViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        return PostViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.fragment_video, parent, false)
        )
    }

    val viewHolderList: MutableList<PostViewHolder> = mutableListOf()

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        holder.apply {
            viewHolderList.add(this)
            imageView.loadThumbNail("https://wallpapercave.com/wp/wp1817745.jpg")
            myPosition = position
            setData(model.viewPagerData[position])
        }
    }

    override fun getItemCount(): Int {
        return model.viewPagerData.size
    }

    lateinit var currentViewHolder: PostViewHolder

    fun pauseAllOperations() {
//        currentViewHolder.imageView.beVisible()
        currentViewHolder.action(ExtensionInfo(SelectiveAction.ATTACHED_LOST))
    }

    fun resumeAllOperation() {
        currentViewHolder.imageView.beInvisible()
        currentViewHolder.action(ExtensionInfo(SelectiveAction.ATTACHED_WIN))
    }

    class PostViewHolder(v: View) : RecyclerView.ViewHolder(v), ViewHolderExtension {
        private lateinit var vData: RecyclerViewStateModel.SubViewHolderData
        override fun action(extensionInfo: ExtensionInfo) {
            when (extensionInfo.action) {

                SelectiveAction.NONE -> {

                }

                SelectiveAction.ATTACHED_WIN -> {
                    if (this::vData.isInitialized) {
                        Log.e("Viewholder", "WIN${vData.id}")
                        mySimpleExoPlayer.play(vData)
                    }

                }

                SelectiveAction.ATTACHED_LOST -> {
                    if (this::vData.isInitialized) {
                        Log.e("Viewholder", "LOST${vData.id}")
                        mySimpleExoPlayer.pause(vData)
                    }
                }

                SelectiveAction.ATTACHED_CANDIDATE -> {

                }

                SelectiveAction.DETACHED -> {

                }
            }
        }

        fun setData(data: RecyclerViewStateModel.SubViewHolderData) {
            vData = data
            mySimpleExoPlayer.play(vData)
        }

        var myPosition: Int = 0
        var lastPlayedLocation: Long = C.TIME_UNSET
        val imageView: ImageView = v.findViewById(R.id.thumbNail)
        private val mySimpleExoPlayer: MySimpleExoPlayer = v.findViewById(R.id.mySimpleExoPlayer)
    }
}

fun ImageView.loadThumbNail(string: String) {
    Glide.with(RecyclerViewStateModel.applicationContext).load("empty")
        .thumbnail(Glide.with(RecyclerViewStateModel.applicationContext).load(string))
        .into(this);
}


/*
*    override fun onViewAttachedToWindow(holder: PostViewHolder) {
        super.onViewAttachedToWindow(holder)
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)

    }

    override fun onViewDetachedFromWindow(holder: PostViewHolder) {
        super.onViewDetachedFromWindow(holder)
    }

    fun abortAllOperation() {
//        currentViewHolder.imageView.beVisible()
//        currentViewHolder.mySimpleExoPlayer.abort(model.viewPagerData[currentViewHolder.myPosition])
    }*/