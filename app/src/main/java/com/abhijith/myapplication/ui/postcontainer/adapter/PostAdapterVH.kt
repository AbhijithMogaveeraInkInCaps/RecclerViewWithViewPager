package com.abhijith.myapplication.ui.postcontainer.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.abhijith.myapplication.R
import com.abhijith.myapplication.ui.viewpager.adapter.PostContentAdapterNew
//import com.abhijith.myapplication.ui.viewpager.adapter.PostContentAdapterVP
import com.abhijith.myapplication.ui.viewpager.adapter.PostViewHolder
import com.google.android.material.button.MaterialButton
import com.google.android.material.textview.MaterialTextView
import com.tbuonomo.viewpagerdotsindicator.DotsIndicator
import com.tbuonomo.viewpagerdotsindicator.WormDotsIndicator


class PostAdapterRV(private val fragmentActivity: FragmentActivity)
    : RecyclerView.Adapter<PostAdapterRV.PostViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        return PostViewHolder(LayoutInflater
            .from(parent.context)
            .inflate(R.layout.layour_post, parent, false))
    }

    private lateinit var intLastClickViewHolder:PostViewHolder

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        holder.myPosition = position
        holder.also {VH->
            VH.vp.apply {
                adapter = PostContentAdapterNew()
            }
            VH.dotsIndicator.setViewPager2(VH.vp)

        }
    }

    override fun onViewAttachedToWindow(holder: PostViewHolder) {
        super.onViewAttachedToWindow(holder)
        intLastClickViewHolder = holder
    }

    override fun getItemCount(): Int {
        return 7
    }

    inner class PostViewHolder(v: View) : RecyclerView.ViewHolder(v), ViewHolderExtension {

        var myPosition:Int=-2;
        var mList:List<PostAdapterRV.PostViewHolder> = listOf()
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
                    (vp.adapter as PostContentAdapterNew?)!!.resumeAllOperation()
                }

                SelectiveAction.ATTACHED_LOST -> {
                    mtvUserName.text = "ATTACHED_LOST"
                    mbActionOne.text = "ATTACHED_LOST"
                    (vp.adapter as PostContentAdapterNew?)!!.abortAllOperation()
                }

                SelectiveAction.ATTACHED_CANDIDATE -> {
                }

                SelectiveAction.DETACHED -> {

                }
            }
        }
    }

}

interface ViewHolderExtension {
    fun action(extensionInfo: ExtensionInfo) {}
}

data class ExtensionInfo(var action: SelectiveAction = SelectiveAction.NONE)

enum class SelectiveAction { NONE, ATTACHED_WIN, ATTACHED_LOST, ATTACHED_CANDIDATE, DETACHED }