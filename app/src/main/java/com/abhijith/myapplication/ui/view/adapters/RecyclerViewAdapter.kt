package com.abhijith.myapplication.ui.view.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.abhijith.myapplication.R
import com.abhijith.myapplication.ui.PlayerManager
//import com.abhijith.myapplication.ui.viewpager.adapter.PostContentAdapterVP
import com.google.android.material.button.MaterialButton
import com.google.android.material.textview.MaterialTextView
import com.tbuonomo.viewpagerdotsindicator.WormDotsIndicator


class RecyclerViewAdapter :
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
        if (position == 0)
            PlayerManager.isInitialPlay = true

    }

    override fun onViewAttachedToWindow(holder: ViewHolder) {
        super.onViewAttachedToWindow(holder)
        holder.also { VH ->
            VH.vp.apply {
                adapter = ViewPager2Adapter()
                registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                    override fun onPageSelected(position: Int) {
                        super.onPageSelected(position)
                        (adapter as ViewPager2Adapter)
                            .viewholderList
                            .forEach {
                                if (it.myPosition == position) {
                                    it.mySimpleExoPlayer.play()
                                } else {
                                    it.mySimpleExoPlayer.pause()
                                }
                            }
                    }
                })
            }
            VH.dotsIndicator.setViewPager2(VH.vp)

        }
        lastClickViewHolder = holder
    }


    override fun getItemCount(): Int {
        return 7
    }

    inner class ViewHolder(v: View) : RecyclerView.ViewHolder(v), ViewHolderExtension {

        var myPosition: Int = -2;
        var mList: List<ViewHolder> = listOf()
        val vp: ViewPager2 = v.findViewById(R.id.vp_post_media)
        private val mtvUserName: MaterialTextView = v.findViewById(R.id.mtvLocation)
        private val mbActionOne: MaterialButton = v.findViewById(R.id.mbActionOne)
        val dotsIndicator: WormDotsIndicator = v.findViewById(R.id.dots_indicator)

        init {

        }

        @SuppressLint("SetTextI18n")
        override fun action(extensionInfo: ExtensionInfo) {
            when (extensionInfo.action) {

                SelectiveAction.NONE -> {
                }

                SelectiveAction.ATTACHED_WIN -> {
                    mtvUserName.text = "ATTACHED_WIN"
                    mbActionOne.text = "ATTACHED_WIN"
                    (vp.adapter as ViewPager2Adapter?)!!.resumeAllOperation()
                }

                SelectiveAction.ATTACHED_LOST -> {
                    mtvUserName.text = "ATTACHED_LOST"
                    mbActionOne.text = "ATTACHED_LOST"
                    (vp.adapter as ViewPager2Adapter?)!!.pauseAllOperations()
                }

                SelectiveAction.ATTACHED_CANDIDATE -> {
                }

                SelectiveAction.DETACHED -> {
                    (vp.adapter as ViewPager2Adapter?)!!.abortAllOperation()
                }
            }
        }
    }


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