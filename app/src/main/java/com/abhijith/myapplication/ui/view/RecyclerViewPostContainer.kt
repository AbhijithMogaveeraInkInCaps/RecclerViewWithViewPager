package com.abhijith.myapplication.ui.view

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.View
import androidx.annotation.Px
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.abhijith.myapplication.ui.PlayerManager
import com.abhijith.myapplication.ui.PlayerManagerEvent
import com.abhijith.myapplication.ui.view.adapters.ExtensionInfo
import com.abhijith.myapplication.ui.view.adapters.RecyclerViewAdapter
import com.abhijith.myapplication.ui.view.adapters.SelectiveAction
import com.abhijith.myapplication.ui.view.adapters.ViewHolderExtension
import com.google.android.exoplayer2.C

var isScrollingFast: Boolean = false

class RecyclerViewPostContainer : RecyclerView {
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int)
            : super(context!!, attrs, defStyleAttr)

    constructor(context: Context?, attrs: AttributeSet?)
            : super(context!!, attrs)

    constructor(context: Context?, color: Int) : super(context!!) {

    }

    constructor(context: Context?) : super(context!!) {}

    private val listOfAttachedCandidates = mutableListOf<RecyclerViewAdapter.ViewHolder>()

    companion object {
        var isScrolledDownn = false
        var isScrolled = false
    }


    private var flag = true
    init {
        val function: (t: PlayerManagerEvent) -> Unit = {
            if (flag) {
                flag = false
                listOfAttachedCandidates.forEach { postViewHolder ->
                    (postViewHolder as ViewHolderExtension).apply {
                        action(
                            if (postViewHolder.myPosition == 0)
                                ExtensionInfo(SelectiveAction.ATTACHED_WIN)
                            else
                                ExtensionInfo(SelectiveAction.ATTACHED_LOST)
                        )
                    }
                }
            }
        }
        PlayerManager.liveData.observeForever(function)
    }

    override fun onChildAttachedToWindow(child: View) {
        super.onChildAttachedToWindow(child)
        val childViewHolder = getChildViewHolder(child)
        if (childViewHolder is RecyclerViewAdapter.ViewHolder) {
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
        if(!isScrolledDownn)
            Log.e("AloorT", "Aloor ${MySimpleExoPlayer.position}")
        MySimpleExoPlayer.position = C.TIME_UNSET
        val childViewHolder = getChildViewHolder(child)
        if (childViewHolder is RecyclerViewAdapter.ViewHolder) {
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
        isScrolledDownn = dy < 0
        Log.e("RecyclerView", "Fast scrolling start")
        isScrollingFast = true
        isScrolled = true
    }

    var lastScrollFocus: Int = -1

    override fun onScrollStateChanged(state: Int) {
        super.onScrollStateChanged(state)
        val visibleItemPosition: Int = if (!isScrolledDownn) {
            if ((layoutManager as LinearLayoutManager).findLastCompletelyVisibleItemPosition() == -1 && state == SCROLL_STATE_IDLE)
                (layoutManager as LinearLayoutManager).findLastVisibleItemPosition()
            else
                (layoutManager as LinearLayoutManager).findLastCompletelyVisibleItemPosition()
        } else {
            if ((layoutManager as LinearLayoutManager).findLastCompletelyVisibleItemPosition() == -1 && state == SCROLL_STATE_IDLE)
                (layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()
            else
                (layoutManager as LinearLayoutManager).findLastCompletelyVisibleItemPosition()
        }
        if (visibleItemPosition != -1)
            when (state) {
                SCROLL_STATE_IDLE -> {
                    Log.e("RecyclerView", "Fast scrolling end")
                    isScrollingFast = false
                    if (lastScrollFocus != visibleItemPosition) {
                        lastScrollFocus = visibleItemPosition
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
                }
                SCROLL_STATE_DRAGGING -> {
                }
                SCROLL_STATE_SETTLING -> {
                }
            }
    }
}

