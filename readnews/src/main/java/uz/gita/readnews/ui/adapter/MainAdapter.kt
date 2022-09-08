package uz.gita.readnews.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import uz.gita.mylibrary.NewsData
import uz.gita.readnews.R
import java.util.*

class MainAdapter:RecyclerView.Adapter<MainAdapter.Holder>() {

    var data= ArrayList<NewsData>()
    @SuppressLint("NotifyDataSetChanged")
    fun submitItems(newsItems:List<NewsData>){
        data.clear()
        data.addAll(newsItems)
        notifyDataSetChanged()
    }
    inner class Holder(view: View):RecyclerView.ViewHolder(view) {

        val title:TextView=view.findViewById(R.id.titleText)
        val desc:TextView=view.findViewById(R.id.descriptionText)
        val like:TextView=view.findViewById(R.id.LikeText)
        val notLike:TextView=view.findViewById(R.id.NotLikeText)
        val seen:TextView=view.findViewById(R.id.showText)
        val image:ImageView=view.findViewById(R.id.imageView)
        fun bind(){
            val item=data[adapterPosition]
            title.text=item.title
            desc.text=item.description
            like.text=item.like
            notLike.text=item.notlike
            seen.text=item.description
            Glide
                .with(image)
                .load(item.image)
                .centerCrop()
                .placeholder(R.drawable.ic_launcher_foreground)
                .into(image);
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
       return  Holder(LayoutInflater.from(parent.context).inflate(R.layout.item_news,parent,false))
    }

    override fun onBindViewHolder(holder: Holder, position: Int) =holder.bind()

    override fun getItemCount()=data.size
}