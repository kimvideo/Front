package com.enomusence.projectgm

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class storyAdapter (val itemList: ArrayList<friendData>): RecyclerView.Adapter<storyAdapter.stroyViewHolder>() {
    override fun onCreateViewHolder( parent: ViewGroup, viewType: Int): stroyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.story_layout,parent,false)
        return stroyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return itemList.count()
    }

    override fun onBindViewHolder(holder:stroyViewHolder, position: Int) {
       // holder.userid.text=itemList[position].Userid
        //holder.userimg.text=itemList[position].State

    }
    inner class stroyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val userid = itemView.findViewById<TextView>(R.id.friendId)
        val userimg = itemView.findViewById<TextView>(R.id.friend_feel)

    }

}