package com.adamblack.historyofempire.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.adamblack.historyofempire.R
import com.adamblack.historyofempire.model.RealmModel
import com.squareup.picasso.Picasso


 class FeedAdapter(var feedArrayList: ArrayList<RealmModel>
                           , val listener:Listener):RecyclerView.Adapter<FeedAdapter.FeedHolder>() {

     interface Listener {
         fun onItemClicked(feedModel: RealmModel)
         fun likeClicked(realmModel: RealmModel, position: Int)
         fun bookMarkClicked(realmModel: RealmModel, position: Int)
     }

     class FeedHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

     override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeedHolder {
         val view = LayoutInflater.from(parent.context).inflate(R.layout.feed_row, parent, false)
         return FeedHolder(view)
     }

     override fun onBindViewHolder(holder: FeedHolder, position: Int) {
         val title = holder.itemView.findViewById<TextView>(R.id.feedTitleRow)
         val brief = holder.itemView.findViewById<TextView>(R.id.feedTitleBriefRow)
         val image = holder.itemView.findViewById<ImageView>(R.id.feedImageRow)
         val kindtext = holder.itemView.findViewById<TextView>(R.id.kindText)
         val likes = holder.itemView.findViewById<ImageView>(R.id.likeImage)
         val bookMark = holder.itemView.findViewById<ImageView>(R.id.bookmarkImage)
         val feedList = feedArrayList[position]

         holder.itemView.apply {
             title.text = feedList.title
             brief.text = feedList.brief
             kindtext.text = feedList.sınıf
             Picasso.get().load(feedList.imageUrl).placeholder(R.drawable.ic_launcher_foreground)
                 .resize(350, 250).into(image)
             image.setOnClickListener {
                 listener.onItemClicked(feedList)
             }
             
             bookMark.setOnClickListener {




                 likes.setOnClickListener {

                     listener.likeClicked(feedList, position)
                 }

             }

         }

     }


         override fun getItemCount(): Int {
             println("ıtemCount:${feedArrayList.size}")
             return feedArrayList.size
         }


         fun upDateCountryList(newHistory: List<RealmModel>) {
             feedArrayList.clear()
             feedArrayList.addAll(newHistory)
             notifyDataSetChanged()
         }


 }