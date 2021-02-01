package com.abhijith.myapplication.ui.viewpager.adapter

import android.content.Context
import android.net.Uri
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Lifecycle
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.abhijith.myapplication.R
import com.abhijith.myapplication.ui.fragments.VideoFragment
import com.abhijith.myapplication.ui.postcontainer.adapter.ExtensionInfo
import com.abhijith.myapplication.ui.postcontainer.adapter.SelectiveAction
import com.abhijith.myapplication.ui.postcontainer.adapter.ViewHolderExtension
import com.abhijith.myapplication.ui.view.MySimpleExoPlayer
/*

class PostContentAdapterVP(fragmentActivity: FragmentActivity) :
    FragmentStateAdapter(fragmentActivity) {

    val list: MutableList<Fragment> = mutableListOf()

    private val titles = arrayOf("Video", "Photo")

    override fun getItemCount(): Int {
        return titles.size
    }


    override fun createFragment(position: Int): Fragment {
        val fragment: Fragment = when (position) {
            0 -> {
                VideoFragment.newInstance(titles[0])
            }
            1 -> {
                VideoFragment.newInstance(titles[1])
            }
            else -> {
                VideoFragment.newInstance("")
            }
        }
        list.add(fragment)
        return fragment
    }

    fun abortAllOperation() {
        list.forEach {
            if (it.lifecycle.currentState == Lifecycle.State.RESUMED) {
                if (it is VideoFragment) {
                    it.abortAllOperation()
                }
            }
        }
    }

    fun resumeAllOperation() {
        list.forEach {
            if (it.lifecycle.currentState == Lifecycle.State.RESUMED) {
                if (it is VideoFragment) {
                    it.resumeAllOperation()
                }
            }
        }
    }
}
*/

//Experimental
class PostContentAdapterNew() : RecyclerView.Adapter<PostViewHolder>() {

//    val listOfMembers:MutableList<PostViewHolder> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        return PostViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.fragment_video,parent,false))
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        holder.apply {
//            listOfMembers.add(this)
            myPosition = position
                        val path = "android.resource://" + "com.abhijith.myapplication" + "/" + R.raw.videoplayback
            mySimpleExoPlayer.setUri(Uri.parse(path))
        }
    }

    override fun getItemCount(): Int {
        return 2
    }

    lateinit var currentViewHolder:PostViewHolder
    override fun onViewAttachedToWindow(holder: PostViewHolder) {
        super.onViewAttachedToWindow(holder)
        currentViewHolder = holder
        holder.apply {
            mySimpleExoPlayer.play()
        }
    }


    override fun onViewDetachedFromWindow(holder: PostViewHolder) {
        super.onViewDetachedFromWindow(holder)
        holder.apply {
            mySimpleExoPlayer.pause()
        }
    }

    fun abortAllOperation() {
        currentViewHolder.mySimpleExoPlayer.pause()
    }

    fun resumeAllOperation() {
        currentViewHolder.mySimpleExoPlayer.play()
    }

}

class PostViewHolder(v: View) : RecyclerView.ViewHolder(v) {

    var myPosition:Int=0
//    override fun action(extensionInfo: ExtensionInfo) {
//        when (extensionInfo.action) {
//            SelectiveAction.NONE -> {
//
//            }
//            SelectiveAction.ATTACHED_WIN -> {
//                mySimpleExoPlayer.play()
//            }
//            SelectiveAction.ATTACHED_LOST -> {
//                mySimpleExoPlayer.pause()
//            }
//            SelectiveAction.ATTACHED_CANDIDATE -> {
//
//            }
//            SelectiveAction.DETACHED -> {
//
//            }
//        }
//
//    }

    val mySimpleExoPlayer: MySimpleExoPlayer = v.findViewById(R.id.mySimpleExoPlayer)
}