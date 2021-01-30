package com.abhijith.myapplication.ui.postcontainer.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.abhijith.myapplication.R
import com.abhijith.myapplication.ui.viewpager.adapter.PostContentAdapterVP
import com.google.android.material.textview.MaterialTextView

class PostAdapterRV(private val fragmentActivity: FragmentActivity) : RecyclerView.Adapter<PostAdapterRV.PostViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        return PostViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.layour_post, parent, false)
        )
    }


    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        holder.apply {
            vp.apply {
                adapter = PostContentAdapterVP(fragmentActivity)
            }
        }
    }

    override fun getItemCount(): Int {
        return 3
    }

    inner class PostViewHolder(v: View) : RecyclerView.ViewHolder(v), ViewHolderExtension {

        val vp: ViewPager2 = v.findViewById(R.id.vp_post_media)
        val mtv: MaterialTextView = v.findViewById(R.id.mtvUserName)

        override fun action(extensionInfo: ExtensionInfo) {
            when (extensionInfo.action) {

                SelectiveAction.NONE -> {
                    mtv.text = "NONE"
                }

                SelectiveAction.ATTACHED_WIN -> {
                    mtv.text = "ATTACHED_WIN"
                }

                SelectiveAction.ATTACHED_LOST -> {
                    mtv.text = "ATTACHED_LOST"
                }

                SelectiveAction.ATTACHED_CANDIDATE -> {
                    mtv.text = "ATTACHED_CANDIDATE"
                }

                SelectiveAction.DETACHED -> {
                    mtv.text = "DETACHED"
                }
            }
        }

        override fun wantsToAttach(): Boolean {
            return true
        }

        override fun getAttachOrder(): Int {
            TODO()
        }

        override fun getAttachView(): View {
            TODO()
        }
    }

}


class AdapterData(data: List<Data>) {

    inner class Data(type: TYPE, url: String)

    enum class TYPE {
        VIDEO, PHOTO
    }
}

interface ViewHolderExtension {
    /**Every state change via this method from RecyclerView*/
    fun action(extensionInfo: ExtensionInfo) {

    }

    /** Identity viewHolder*/
    fun getPosition(): Int

    /**Another option is letting outside control this item should win or lose when selecting win or lose from attach candidate list*/
    fun wantsToAttach(): Boolean

    /** It can change the action invoke order*/
    fun getAttachOrder(): Int

    fun getAttachView(): View
}

data class ExtensionInfo(var action: SelectiveAction = SelectiveAction.NONE)


enum class SelectiveAction {
    NONE,
    ATTACHED_WIN,
    ATTACHED_LOST,
    ATTACHED_CANDIDATE,
    DETACHED
}