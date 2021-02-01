package com.abhijith.myapplication.ui.fragments

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.abhijith.myapplication.R
import com.abhijith.myapplication.ui.view.MySimpleExoPlayer


private const val VIDEO_URL = "param1"

const val EMPTY = ""

class VideoFragment : Fragment() {

    private var url: String = EMPTY

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            url = it.getString(VIDEO_URL, EMPTY)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_video, container, false)
    }


    private lateinit var views: View
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        views = view
        view.apply {
//            val path = "android.resource://" + activity!!.packageName + "/" + R.raw.videoplayback
            lifecycle.addObserver(findViewById<MySimpleExoPlayer>(R.id.mySimpleExoPlayer).apply {
//                    setUri(Uri.parse(path))
                    init(Uri.parse("https://i.imgur.com/7bMqysJ.mp4"))
            })
        }
    }

    companion object {
        fun newInstance(param1: String) =
            VideoFragment().apply {
                arguments = Bundle().apply {
                    putString(VIDEO_URL, param1)
                }
            }
    }

    fun abortAllOperation(){
        views.findViewById<MySimpleExoPlayer>(R.id.mySimpleExoPlayer)?.apply {
            pause()
        }
    }

    fun resumeAllOperation(){
        views.findViewById<MySimpleExoPlayer>(R.id.mySimpleExoPlayer)?.apply {
            play()
        }
    }
}