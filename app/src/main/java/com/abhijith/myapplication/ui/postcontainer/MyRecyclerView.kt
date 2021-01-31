package com.abhijith.myapplication.ui.postcontainer

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.annotation.Px
import androidx.recyclerview.widget.RecyclerView
import com.abhijith.myapplication.ui.postcontainer.adapter.ExtensionInfo
import com.abhijith.myapplication.ui.postcontainer.adapter.PostAdapterRV
import com.abhijith.myapplication.ui.postcontainer.adapter.SelectiveAction
import com.abhijith.myapplication.ui.postcontainer.adapter.ViewHolderExtension
import kotlin.math.ceil

class MyRecyclerView : RecyclerView {
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int)
            : super(context!!, attrs, defStyleAttr)

    constructor(context: Context?, attrs: AttributeSet?)
            : super(context!!, attrs)

    constructor(context: Context?, color: Int) : super(context!!) {

    }

    constructor(context: Context?) : super(context!!) {}

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
    }

    val listOfAttachedCandidates = mutableListOf<PostAdapterRV.PostViewHolder>()
//    var isScrolledDown = false


    override fun onChildAttachedToWindow(child: View) {
        super.onChildAttachedToWindow(child)
        val childViewHolder = getChildViewHolder(child)
        if (childViewHolder is PostAdapterRV.PostViewHolder) {
            childViewHolder.also { VH ->
                (VH as ViewHolderExtension).also { VHE ->
                    VHE.action(ExtensionInfo(SelectiveAction.ATTACHED_CANDIDATE))
                    listOfAttachedCandidates.add(VH)
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
                }
            }
        }
    }

    override fun onScrolled(@Px dx: Int, @Px dy: Int) {
        super.onScrolled(dx, dy)
//        isScrolledDown = dy < 0
    }

    override fun onScrollStateChanged(state: Int) {
        super.onScrollStateChanged(state)
        when (state) {
            SCROLL_STATE_IDLE -> {
                val luckyWinner = ceil((listOfAttachedCandidates.size / 2.0)).toInt()
                listOfAttachedCandidates.forEachIndexed { index, postViewHolder ->
                    (postViewHolder as ViewHolderExtension).apply {
                        action(
                            if (index == luckyWinner)
                                ExtensionInfo(SelectiveAction.ATTACHED_WIN)
                            else
                                ExtensionInfo(SelectiveAction.ATTACHED_LOST)
                        )
                    }
                }
            }
            SCROLL_STATE_DRAGGING -> {
//                val luckyWinner = ceil((listOfAttachedCandidates.size / 2.0)).toInt()
//                listOfAttachedCandidates.forEachIndexed { index, postViewHolder ->
//                    (postViewHolder as ViewHolderExtension).apply {
//                        action(
//                            if (index == luckyWinner) ExtensionInfo(SelectiveAction.ATTACHED_WIN) else ExtensionInfo(
//                                SelectiveAction.ATTACHED_LOST
//                            )
//                        )
//                    }
//                }
            }
            SCROLL_STATE_SETTLING -> {

            }
        }
    }

    override fun onScreenStateChanged(screenState: Int) {
        super.onScreenStateChanged(screenState)
    }

    override fun onWindowFocusChanged(hasWindowFocus: Boolean) {
        super.onWindowFocusChanged(hasWindowFocus)
        listOfAttachedCandidates.forEach {
            it.apply {
                action(
                    if (itemView.isFocused)
                        ExtensionInfo(SelectiveAction.ATTACHED_WIN)
                    else
                        ExtensionInfo(SelectiveAction.ATTACHED_LOST)
                )
            }
        }
    }

    override fun onWindowVisibilityChanged(visibility: Int) {
        super.onWindowVisibilityChanged(visibility)
    }
}