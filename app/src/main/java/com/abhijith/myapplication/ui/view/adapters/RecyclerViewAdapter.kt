package com.abhijith.myapplication.ui.view.adapters

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.view.get
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.abhijith.myapplication.R
import com.abhijith.myapplication.ui.PlayerFlags
import com.abhijith.myapplication.ui.PlayerManager
import com.abhijith.myapplication.ui.statemodel.RecyclerViewStateModel
import com.google.android.material.textview.MaterialTextView
import com.tbuonomo.viewpagerdotsindicator.WormDotsIndicator


class RecyclerViewAdapter(val stateModel: RecyclerViewStateModel) :
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
                val dataList = stateModel.getRVData()[pos]
                vp2.adapter = ViewPager2Adapter(stateModel.getRVData()[pos])
                vp2.registerOnPageChangeCallback(object :
                    ViewPager2.OnPageChangeCallback() {
                    override fun onPageSelected(position: Int) {
                        super.onPageSelected(position)
                        (vp2.adapter as ViewPager2Adapter).also { vp2Adapter ->
                            vp2Adapter.currentViewHolder = vp2Adapter.viewHolderList[position]
                        }
                        (vp2.adapter as ViewPager2Adapter)
                            .viewHolderList
                            .forEach {
                                if (it.myPosition == position) {
                                    it.imageView.beInvisible()
                                    it.action(ExtensionInfo(SelectiveAction.ATTACHED_WIN))
                                } else {
//                                    Log.e("Lion",dataList.viewPagerData[position].id.toString())
                                    it.imageView.beInvisible()
                                    it.action(ExtensionInfo(SelectiveAction.ATTACHED_LOST))
                                }
                            }
                    }
                })

                VH.btnOne.setOnClickListener { btn ->
                    PlayerFlags.isMute = !PlayerFlags.isMute;
                    PlayerManager.currentMySimpleExoPlayer?.let {
                        it.mute()
                    }
                }
            }
            VH.dotsIndicator.setViewPager2(VH.vp)
        }
    }

    override fun onViewAttachedToWindow(holder: ViewHolder) {
        super.onViewAttachedToWindow(holder)
    }


    override fun getItemCount(): Int {
        return stateModel.getRVData().size
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