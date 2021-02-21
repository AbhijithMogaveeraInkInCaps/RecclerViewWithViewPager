package com.abhijith.myapplication.ui.view.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.abhijith.myapplication.R
import com.abhijith.myapplication.ui.data.DataType
import com.abhijith.myapplication.ui.data.SubViewHolderData
import com.abhijith.myapplication.ui.data.ViewHolderData
import com.abhijith.myapplication.ui.view.MySimpleExoPlayer
import com.bumptech.glide.Glide
import com.google.android.exoplayer2.C


class ViewPager2Adapter(
    private val model: ViewHolderData
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {
        return when (viewType) {

            VIEW_TYPE_VIDEO -> {
                PostVideoViewHolder(
                    LayoutInflater
                        .from(parent.context)
                        .inflate(R.layout.fragment_video, parent, false)
                )
            }

            VIEW_TYPE_IMAGE -> {
                PostImageViewHolder(
                    LayoutInflater
                        .from(parent.context)
                        .inflate(R.layout.fragment_video, parent, false)
                )
            }
            else -> {
                throw Exception("This type of content is not supported yet..!")
            }
        }
    }

    val videoViewHolderList: MutableList<RecyclerView.ViewHolder> = mutableListOf()

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        holder.runWhenVideo {
            videoViewHolderList.add(it)
            it.imageView.loadThumbNail("https://wallpapercave.com/wp/wp1817745.jpg")
            it.myPosition = position
            it.setData(model.viewPagerData[position])
        }

        holder.runWhenImage {

        }

    }



    override fun getItemViewType(position: Int): Int {
        return when (model.viewPagerData[position].dataType) {
            DataType.IMAGE -> {
                VIEW_TYPE_IMAGE
            }
            DataType.VIDEO -> {
                VIEW_TYPE_VIDEO
            }
        }
    }

    override fun getItemCount(): Int {
        return model.viewPagerData.size
    }

    lateinit var currentVideoViewHolder: RecyclerView.ViewHolder

    fun pauseAllOperations() {
        currentVideoViewHolder.runWhenVideo {
            it.imageView.beVisible()
            it.action(ExtensionInfo(SelectiveAction.ATTACHED_LOST))
        }
        currentVideoViewHolder.runWhenImage {

        }
    }

    fun resumeAllOperation() {
        currentVideoViewHolder.runWhenVideo {
            it.imageView.beInvisible()
            it.action(ExtensionInfo(SelectiveAction.ATTACHED_WIN))
        }
    }

    fun freeMemory() {
        videoViewHolderList.forEach { holder ->
            holder.runWhenVideo {
                it.mySimpleExoPlayer.freeMemory()
            }
        }
    }

    fun initAndSeekToOne() {
        videoViewHolderList.forEach { vh ->
            vh.runWhenVideo {
                it.mySimpleExoPlayer.allocateMemoryAndBeReady(it.vData)
            }
            vh.runWhenImage {

            }
        }
    }

    class PostVideoViewHolder(v: View) : RecyclerView.ViewHolder(v), ViewHolderExtension {
        lateinit var vData: SubViewHolderData
        override fun action(extensionInfo: ExtensionInfo) {
            when (extensionInfo.action) {

                SelectiveAction.NONE -> {

                }

                SelectiveAction.ATTACHED_WIN -> {
                    if (this::vData.isInitialized) {
                        mySimpleExoPlayer.play(vData)
                    }

                }

                SelectiveAction.ATTACHED_LOST -> {
                    if (this::vData.isInitialized) {
                        mySimpleExoPlayer.pause(vData)
                    }
                }

                SelectiveAction.ATTACHED_CANDIDATE -> {

                }

                SelectiveAction.DETACHED -> {

                }
            }
        }

        fun setData(data: SubViewHolderData) {
            vData = data
            mySimpleExoPlayer.play(vData)
        }

        var myPosition: Int = 0
        var lastPlayedLocation: Long = C.TIME_UNSET
        val imageView: ImageView = v.findViewById(R.id.thumbNail)
        val mySimpleExoPlayer: MySimpleExoPlayer = v.findViewById(R.id.mySimpleExoPlayer)
    }

    class PostImageViewHolder(v: View) : RecyclerView.ViewHolder(v), ViewHolderExtension {

        lateinit var iData: SubViewHolderData

        override fun action(extensionInfo: ExtensionInfo) {
            when (extensionInfo.action) {

                SelectiveAction.NONE -> {

                }

                SelectiveAction.ATTACHED_WIN -> {

                }

                SelectiveAction.ATTACHED_LOST -> {

                }

                SelectiveAction.ATTACHED_CANDIDATE -> {

                }

                SelectiveAction.DETACHED -> {

                }
            }
        }

        fun setData(data: SubViewHolderData) {

        }


    }

    private fun RecyclerView.ViewHolder.runWhenVideo(callback: (PostVideoViewHolder) -> Unit) {
        if (this is PostVideoViewHolder) {
            callback(this)
        }
    }

    private fun RecyclerView.ViewHolder.runWhenImage(callback: (PostImageViewHolder) -> Unit) {
        if (this is PostImageViewHolder) {
            callback(this)
        }
    }

    companion object {
        private const val VIEW_TYPE_IMAGE: Int = 1
        private const val VIEW_TYPE_VIDEO: Int = 2
    }
}

fun ImageView.loadThumbNail(string: String) {
    Glide.with(this).load("empty")
        .thumbnail(Glide.with(this).load(string))
        .into(this);
}
