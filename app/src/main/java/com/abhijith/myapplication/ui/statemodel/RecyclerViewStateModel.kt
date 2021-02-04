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
                SubViewHolderData(Uri.parse(""), 2),
                SubViewHolderData(Uri.parse(""), 3),
            ), 1
        ),
        ViewHolderData(
            listOf(
                SubViewHolderData(Uri.parse(""), 4),
                SubViewHolderData(Uri.parse(""), 5),
            ), 2
        ),
        ViewHolderData(
            listOf(
                SubViewHolderData(Uri.parse(""), 6),
                SubViewHolderData(Uri.parse(""), 7),
            ), 3
        ),
        ViewHolderData(
            listOf(
                SubViewHolderData(Uri.parse(""), 8),
                SubViewHolderData(Uri.parse(""), 9),
            ), 4
        ),
        ViewHolderData(
            listOf(
                SubViewHolderData(Uri.parse(""), 10),
                SubViewHolderData(Uri.parse(""), 11),
            ), 5
        ),
        ViewHolderData(
            listOf(
                SubViewHolderData(Uri.parse(""), 12),
                SubViewHolderData(Uri.parse(""), 13),
            ), 6
        ),
        ViewHolderData(
            listOf(
                SubViewHolderData(Uri.parse(""), 14),
                SubViewHolderData(Uri.parse(""), 15),
            ), 7
        ),
        ViewHolderData(
            listOf(
                SubViewHolderData(Uri.parse(""), 16),
                SubViewHolderData(Uri.parse(""), 17),
            ), 8
        ),
        ViewHolderData(
            listOf(
                SubViewHolderData(Uri.parse(""), 17),
                SubViewHolderData(Uri.parse(""), 18),
            ), 9
        ),
        ViewHolderData(
            listOf(
                SubViewHolderData(Uri.parse(""), 19),
                SubViewHolderData(Uri.parse(""), 20),
            ), 10
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
            list.add(metadata.apply {
                lastPlayedLocation = pos
            })
        }

        fun getLastPlayedLocation(): Long =
            if (this::metadata.isInitialized)
                this.metadata.lastPlayedLocation
            else
                C.TIME_UNSET

    }

    data class SubViewHolderMetadata(val owner: SubViewHolderData, var lastPlayedLocation: Long)

}


