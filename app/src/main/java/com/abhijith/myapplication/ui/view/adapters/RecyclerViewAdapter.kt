package com.abhijith.myapplication.ui.view.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.abhijith.myapplication.R
import com.abhijith.myapplication.ui.PlayerFlags
import com.abhijith.myapplication.ui.PlayerManager
import com.abhijith.myapplication.ui.data.ViewHolderData
import com.google.android.material.textview.MaterialTextView
import com.tbuonomo.viewpagerdotsindicator.WormDotsIndicator


class RecyclerViewAdapter(private val dataList:List<ViewHolderData>) :
    RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.layour_post, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, pos: Int) {
        holder.myPosition = pos
        holder.also { VH ->
            VH.vp.also { vp2 ->
                vp2.adapter = ViewPager2Adapter(dataList[pos])
                vp2.registerOnPageChangeCallback(object :
                    ViewPager2.OnPageChangeCallback() {
                    override fun onPageSelected(position: Int) {
                        super.onPageSelected(position)
                        val viewPager2Adapter = vp2.adapter as ViewPager2Adapter
                        viewPager2Adapter.also { vp2Adapter ->
                            vp2Adapter.currentVideoViewHolder =
                                vp2Adapter.videoViewHolderList[position]
                        }
                        viewPager2Adapter
                            .videoViewHolderList
                            .forEach {
                                if (it is ViewPager2Adapter.PostVideoViewHolder) {
                                    if (it.myPosition == position) {
                                        it.imageView.beInvisible()
                                        it.action(ExtensionInfo(SelectiveAction.ATTACHED_WIN))
                                    } else {
                                        it.imageView.beInvisible()
                                        it.action(ExtensionInfo(SelectiveAction.ATTACHED_LOST))
                                    }
                                }

                                if(it is ViewPager2Adapter.PostImageViewHolder){

                                }
                            }

                    }
                })

                VH.btnOne.setOnClickListener { btn ->
                    PlayerFlags.isMute = !PlayerFlags.isMute;
                    PlayerManager.currentMySimpleExoPlayer?.mute()
                }
            }
            VH.dotsIndicator.setViewPager2(VH.vp)
        }
    }


    override fun getItemCount(): Int {
        return dataList.size
    }

    inner class ViewHolder(v: View) : RecyclerView.ViewHolder(v), ViewHolderExtension {

        var myPosition: Int = -2;
        var mList: List<ViewHolder> = listOf()
        val vp: ViewPager2 = v.findViewById(R.id.vp_post_media)
        private val mtvUserName: MaterialTextView = v.findViewById(R.id.mtvLocation)
        val btnOne: ImageView = v.findViewById(R.id.btnVolume)
        val dotsIndicator: WormDotsIndicator = v.findViewById(R.id.dots_indicator)

        @SuppressLint("SetTextI18n")
        override fun action(extensionInfo: ExtensionInfo) {
            when (extensionInfo.action) {

                SelectiveAction.NONE -> {

                }

                SelectiveAction.ATTACHED_WIN -> {
                    mtvUserName.text = "ATTACHED_WIN"
                    vp.isUserInputEnabled = true
                    (vp.adapter as ViewPager2Adapter?)!!.resumeAllOperation()
                }

                SelectiveAction.ATTACHED_LOST -> {
                    mtvUserName.text = "ATTACHED_LOST"
                    vp.isUserInputEnabled = false
                    (vp.adapter as ViewPager2Adapter?)!!.pauseAllOperations()
                }

                SelectiveAction.ATTACHED_CANDIDATE -> {
                    (vp.adapter as ViewPager2Adapter?)!!.initAndSeekToOne()
                }

                SelectiveAction.DETACHED -> {
                    (vp.adapter as ViewPager2Adapter?)!!.freeMemory()
                }
            }
        }
    }


}

fun View.beInvisible() {
    visibility = View.INVISIBLE
}

fun View.beVisible() {
    visibility = View.INVISIBLE
}

interface ViewHolderExtension {
    fun action(extensionInfo: ExtensionInfo) {

    }
}

data class ExtensionInfo(
    var action: SelectiveAction = SelectiveAction.NONE
)

enum class SelectiveAction {
    NONE,
    ATTACHED_WIN,
    ATTACHED_LOST,
    ATTACHED_CANDIDATE,
    DETACHED
}