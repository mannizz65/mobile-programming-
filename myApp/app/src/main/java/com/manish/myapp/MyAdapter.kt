package com.manish.myapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.manish.myapp.R
import com.manish.myapp.MyDataItem
import kotlinx.android.synthetic.main.row_items.view.*

class MyAdapter(val context: Context, val userList: List<MyDataItem>): RecyclerView.Adapter<MyAdapter.ViewHolder>() {
    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var userId: TextView
        var title: TextView

        init {
            userId = itemView.user_id
            title = itemView.title_id
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var itemView = LayoutInflater.from(context).inflate(R.layout.row_items, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.userId.text = userList[position].userId.toString()
        holder.title.text = userList[position].title
    }

    override fun getItemCount(): Int {
        return userList.size
    }
}