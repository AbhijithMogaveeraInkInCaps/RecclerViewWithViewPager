package com.abhijith.myapplication.ui.statemodel

import android.content.Context
import android.net.Uri
import com.google.android.exoplayer2.C

object RecyclerViewStateModel {

    lateinit var applicationContext: Context

    private val dataList = listOf(
        ViewHolderData(
            listOf(
                SubViewHolderData(Uri.parse(""), 0),
                SubViewHolderData(Uri.parse(""), 1),
            ), 0
        ),
        ViewHolderData(
            listOf(
                SubViewHolderData(Uri.parse(""), 0),
                SubViewHolderData(Uri.parse(""), 1),
            ), 1
        ),
        ViewHolderData(
            listOf(
                SubViewHolderData(Uri.parse(""), 0),
                SubViewHolderData(Uri.parse(""), 1),
            ), 2
        ),
        ViewHolderData(
            listOf(
                SubViewHolderData(Uri.parse(""), 0),
                SubViewHolderData(Uri.parse(""), 1),
            ), 3
        ),
        ViewHolderData(
            listOf(
                SubViewHolderData(Uri.parse(""), 0),
                SubViewHolderData(Uri.parse(""), 1),
            ), 4
        ),
        ViewHolderData(
            listOf(
                SubViewHolderData(Uri.parse(""), 0),
                SubViewHolderData(Uri.parse(""), 1),
            ), 5
        ),
        ViewHolderData(
            listOf(
                SubViewHolderData(Uri.parse(""), 0),
                SubViewHolderData(Uri.parse(""), 1),
            ), 6
        ),
    )

    fun getRVData(): List<ViewHolderData> {
        return dataList
    }

    data class ViewHolderData(
        var viewPagerData: List<SubViewHolderData>,
        var id: Int
    )

    private val list: MutableList<SubViewHolderMetadata> = mutableListOf()

    data class SubViewHolderData(val uri: Uri, var id: Int) {

        lateinit var metadata: SubViewHolderMetadata

        fun saveLastLocation(pos: Long) {
            if (!this::metadata.isInitialized)
                metadata = SubViewHolderMetadata(this, pos)
            list.add(id,metadata.apply {
                lastPlayedLocation = pos
            })
        }

        fun getLastPlayedLocation(): Long = if (this::metadata.isInitialized)
            list[id].lastPlayedLocation
        else
            C.TIME_UNSET

    }

    data class SubViewHolderMetadata(val owner: SubViewHolderData, var lastPlayedLocation: Long)

}


