package com.ownproject.mvplayer

import android.content.Context
import android.content.Intent
import android.graphics.drawable.Drawable
import android.media.ThumbnailUtils
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.MediaController
import android.widget.TextView
import android.widget.VideoView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import java.io.File
import kotlin.properties.Delegates


class MyAdapter(val context: Context,val itemlist:ArrayList<File>): RecyclerView.Adapter<vedioHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): vedioHolder {
        val view= LayoutInflater.from(parent.context).inflate(R.layout.vedioitem,parent,false)
      return vedioHolder(view)
    }

    override fun getItemCount(): Int {
    return itemlist.size
    }

    override fun onBindViewHolder(holder: vedioHolder, position: Int) {
     holder.textview.text=itemlist[position].name.toString()
        val th=ThumbnailUtils.createVideoThumbnail(itemlist[position].path,MediaStore.Video.Thumbnails.MINI_KIND)
        //println(th.height.toString()+"hiiiiiiiiii"+th.width.toString())
        holder.img.setImageBitmap(th)
       if(th.height>th.width){
            holder.img.rotation=270F
        }

        holder.cardview.setOnClickListener {

         var arr= kotlin.collections.arrayListOf<String>()
            for (i in itemlist)
                arr.add(i.path)
            val intent=Intent(context,MainActivity::class.java)
            intent.putExtra("list",arr)
            intent.putExtra("position",position)
            context.startActivity(intent)
        }
    }
}



class vedioHolder(view:View): RecyclerView.ViewHolder(view) {
val textview:TextView=view.findViewById(R.id.name)
val img:ImageView=view.findViewById(R.id.thmnail)
val cardview:CardView=view.findViewById(R.id.cardview)
}