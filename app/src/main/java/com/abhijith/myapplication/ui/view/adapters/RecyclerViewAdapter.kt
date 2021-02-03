package com.abhijith.myapplication.ui.view.adapters

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.abhijith.myapplication.R
import com.abhijith.myapplication.ui.PlayerFlags
import com.abhijith.myapplication.ui.PlayerManager
import com.google.android.exoplayer2.C
import com.google.android.material.textview.MaterialTextView
import com.tbuonomo.viewpagerdotsindicator.WormDotsIndicator


class RecyclerViewAdapter(var listVideoDataList: List<List<VideoData>>) :
    RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater
                .from(parent.context)
                .inflate(R.layout.layour_post, parent, false)
        )
    }


    private lateinit var lastClickViewHolder: ViewHolder

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.myPosition = position
        holder.also { VH ->
            VH.vp.apply {
                if (adapter == null) {
                    val dataList = listVideoDataList[position]
                    adapter = ViewPager2Adapter(
                        context, dataList
                    )

                    registerOnPageChangeCallback(object :
                        ViewPager2.OnPageChangeCallback() {
                        override fun onPageSelected(position: Int) {
                            super.onPageSelected(position)
                            (adapter as ViewPager2Adapter)
                                .viewHolderList
                                .forEach {
                                    if (it.myPosition == position) {
                                        it.imageView.beInvisible()
                                        it.mySimpleExoPlayer.play(dataList[position])
                                    } else {
                                        it.imageView.beInvisible()
                                        it.mySimpleExoPlayer.pause(dataList[position])
                                    }
                                }
                        }
                    })

                    VH.btnOne.setOnClickListener { btn ->
                        PlayerFlags.isMute = !PlayerFlags.isMute;
                        Log.e("InkInCaps", PlayerFlags.isMute.toString())
                        PlayerManager.currentMySimpleExoPlayer?.let {
                            it.mute()
                        }
                    }

                }

                VH.dotsIndicator.setViewPager2(VH.vp)
            }
        }
    }

    override fun onViewAttachedToWindow(holder: ViewHolder) {
        super.onViewAttachedToWindow(holder)
        lastClickViewHolder = holder
    }


    override fun getItemCount(): Int {
        return listVideoDataList.size
    }

    inner class ViewHolder(v: View) : RecyclerView.ViewHolder(v), ViewHolderExtension {

        var myPosition: Int = -2;
        var mList: List<ViewHolder> = listOf()
        val vp: ViewPager2 = v.findViewById(R.id.vp_post_media)
        private val mtvUserName: MaterialTextView = v.findViewById(R.id.mtvLocation)
        val btnOne: ImageView = v.findViewById(R.id.btnVolume)
        val dotsIndicator: WormDotsIndicator = v.findViewById(R.id.dots_indicator)
        var position: Long = C.TIME_UNSET
        var lastPausedLocation:Long = C.TIME_UNSET
        init {
//            PlayerFlags.isMuteLiveData.observeForever {
//                if (it) {
//                    btnOne.setImageResource(R.drawable.ic_volume_off)
//                } else {
//                    btnOne.setImageResource(R.drawable.ic_volume_up)
//                }
//            }
        }

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

                }

                SelectiveAction.DETACHED -> {
                }
            }
        }
    }


}

fun View.beInvisible() {
    visibility = View.INVISIBLE
}

fun View.beVisible() {
    visibility = View.VISIBLE
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