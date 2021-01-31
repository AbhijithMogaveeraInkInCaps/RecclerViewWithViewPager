package com.abhijith.myapplication.ui.postcontainer.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.abhijith.myapplication.R
import com.abhijith.myapplication.ui.viewpager.adapter.PostContentAdapterVP
import com.google.android.material.button.MaterialButton
import com.google.android.material.textview.MaterialTextView

class PostAdapterRV(private val fragmentActivity: FragmentActivity) : RecyclerView.Adapter<PostAdapterRV.PostViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        return PostViewHolder(LayoutInflater
            .from(parent.context)
            .inflate(R.layout.layour_post, parent, false))
    }

    private lateinit var intLastClickViewHolder:PostViewHolder
    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        holder.myPosition = position
        if(position==0){
            holder.action(ExtensionInfo(SelectiveAction.ATTACHED_WIN))
            intLastClickViewHolder = holder
        }
        holder.also {VH->
            VH.vp.apply {
                adapter = PostContentAdapterVP(fragmentActivity)
            }
        }
    }

    override fun onViewAttachedToWindow(holder: PostViewHolder) {
        super.onViewAttachedToWindow(holder)
        intLastClickViewHolder = holder
        holder.itemView.setOnClickListener {
            intLastClickViewHolder.mList.forEach {
                if(holder==it){
                    it.action(ExtensionInfo(SelectiveAction.ATTACHED_WIN))
                }else{
                    it.action(ExtensionInfo(SelectiveAction.ATTACHED_LOST))
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return 20
    }



    inner class PostViewHolder(v: View) : RecyclerView.ViewHolder(v), ViewHolderExtension {

        var myPosition:Int=-2;
        var mList:List<PostAdapterRV.PostViewHolder> = listOf()
        val vp: ViewPager2 = v.findViewById(R.id.vp_post_media)
        val mtvUserName: MaterialTextView = v.findViewById(R.id.mtvUserName)
        val mbActionOne: MaterialButton = v.findViewById(R.id.mbActionOne)
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
                    (vp.adapter as PostContentAdapterVP?)?.let {
                        it.resumeAllOperation()
                    }
                }

                SelectiveAction.ATTACHED_LOST -> {
                    mtvUserName.text = "ATTACHED_LOST"
                    mbActionOne.text = "ATTACHED_LOST"
                    (vp.adapter as PostContentAdapterVP?)?.let {
                        it.abortAllOperation()
                    }
                }

                SelectiveAction.ATTACHED_CANDIDATE -> {
                }

                SelectiveAction.DETACHED -> {

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


interface ViewHolderExtension {

    fun action(extensionInfo: ExtensionInfo) {}

    fun getPosition(): Int

    fun wantsToAttach(): Boolean

    fun getAttachOrder(): Int

    fun getAttachView(): View
}

data class ExtensionInfo(var action: SelectiveAction = SelectiveAction.NONE)

enum class SelectiveAction { NONE, ATTACHED_WIN, ATTACHED_LOST, ATTACHED_CANDIDATE, DETACHED }