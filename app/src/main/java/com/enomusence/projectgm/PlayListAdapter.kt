package com.enomusence.projectgm

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide

class PlayListAdapter ( private val context: Context, private val songsTitleList: MutableList<String>, private val fileImgList: MutableList<String>, private val singerList: MutableList<String>) : BaseAdapter(){
    override fun getCount(): Int {
        return songsTitleList.size
    }

    override fun getItem(position: Int): Any {
        return position
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view = LayoutInflater.from(context).inflate(R.layout.music_list_view, parent, false)

        val albumImg: ImageView = view.findViewById(R.id.musicImage)
        val musicTitle: TextView = view.findViewById(R.id.musicTitle)
        val singer: TextView = view.findViewById(R.id.musicSigner)

        Glide.with(context)
            .load(fileImgList[position])
            .into(albumImg)

        musicTitle.text = songsTitleList[position]
        singer.text = singerList[position]


        return view
    }

}