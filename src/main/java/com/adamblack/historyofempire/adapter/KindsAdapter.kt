package com.adamblack.historyofempire.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.adamblack.historyofempire.R
import com.adamblack.historyofempire.model.FeedModel
import com.adamblack.historyofempire.model.KindModel
import com.adamblack.historyofempire.model.RealmModel
import com.squareup.picasso.Picasso

class KindsAdapter(private var kindArrayList: ArrayList<RealmModel>, val listener:Listener):RecyclerView.Adapter<KindsAdapter.KindHolder>() {

    interface Listener{
        fun onItemClicked(feedModel: RealmModel)
    }
    class KindHolder(itemView: View) :RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): KindHolder {
        val view=LayoutInflater.from(parent.context).inflate(R.layout.kinds_row,parent,false)
        return KindHolder(view)
    }

    override fun onBindViewHolder(holder: KindHolder, position: Int) {
        val imageUrl=holder.itemView.findViewById<ImageView>(R.id.kindsImageRow)
        val text=holder.itemView.findViewById<TextView>(R.id.kindRowText)
        val kinds=kindArrayList[position]
        holder.itemView.apply {
            Picasso.get().load(kinds.imageUrl).placeholder(R.drawable.ic_launcher_foreground).resize(188,188).into(imageUrl)
            text.text=kinds.anaSınıf
            imageUrl.setOnClickListener {
                listener.onItemClicked(kinds)
            }
        }
    }

    override fun getItemCount(): Int {
        return kindArrayList.size
    }
}