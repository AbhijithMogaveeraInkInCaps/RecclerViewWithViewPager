package com.abhijith.myapplication.ui.postcontainer

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.annotation.Px
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.abhijith.myapplication.ui.postcontainer.adapter.ExtensionInfo
import com.abhijith.myapplication.ui.postcontainer.adapter.PostAdapterRV
import com.abhijith.myapplication.ui.postcontainer.adapter.SelectiveAction
import com.abhijith.myapplication.ui.postcontainer.adapter.ViewHolderExtension

class PostContainerRV : RecyclerView {
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int)
            : super(context!!, attrs, defStyleAttr)

    constructor(context: Context?, attrs: AttributeSet?)
            : super(context!!, attrs)

    constructor(context: Context?, color: Int) : super(context!!) {

    }

    constructor(context: Context?) : super(context!!) {}

    private val listOfAttachedCandidates = mutableListOf<PostAdapterRV.PostViewHolder>()
    private var isScrolledDown = false


    override fun onChildAttachedToWindow(child: View) {
        super.onChildAttachedToWindow(child)
        val childViewHolder = getChildViewHolder(child)
        if (childViewHolder is PostAdapterRV.PostViewHolder) {
            childViewHolder.also { VH ->
                (VH as ViewHolderExtension).also { VHE ->
                    VHE.action(ExtensionInfo(SelectiveAction.ATTACHED_CANDIDATE))
                    listOfAttachedCandidates.add(VH)
                    VH.mList = listOfAttachedCandidates
                }
            }
        }

    }

    override fun onChildDetachedFromWindow(child: View) {
        super.onChildDetachedFromWindow(child)
        val childViewHolder = getChildViewHolder(child)
        if (childViewHolder is PostAdapterRV.PostViewHolder) {
            childViewHolder.also { VH ->
                (VH as ViewHolderExtension).also { VHE ->
                    VHE.action(ExtensionInfo(SelectiveAction.DETACHED))
                    listOfAttachedCandidates.remove(VH)
                    VH.mList = listOf()
                }
            }
        }
    }

    override fun onScrolled(@Px dx: Int, @Px dy: Int) {
        super.onScrolled(dx, dy)
        isScrolledDown = dy < 0
    }

    override fun onScrollStateChanged(state: Int) {
        super.onScrollStateChanged(state)

        val visibleItemPosition: Int = if (!isScrolledDown) {
            (layoutManager as LinearLayoutManager).findLastCompletelyVisibleItemPosition()
        } else {
            (layoutManager as LinearLayoutManager).findLastCompletelyVisibleItemPosition()
        }

        if (visibleItemPosition != -1)
            when (state) {

                SCROLL_STATE_IDLE -> {
                    listOfAttachedCandidates.forEach { postViewHolder ->
                        (postViewHolder as ViewHolderExtension).apply {
                            action(
                                if (postViewHolder.myPosition == visibleItemPosition)
                                    ExtensionInfo(SelectiveAction.ATTACHED_WIN)
                                else
                                    ExtensionInfo(SelectiveAction.ATTACHED_LOST)
                            )
                        }
                    }
                }
                SCROLL_STATE_DRAGGING -> {
                }
                SCROLL_STATE_SETTLING -> {
                }
            }
    }
}