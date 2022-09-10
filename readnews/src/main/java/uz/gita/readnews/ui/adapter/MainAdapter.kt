package uz.gita.readnews.ui.adapter

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.Color.alpha
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.ViewPropertyAnimatorListener
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import jp.wasabeef.recyclerview.animators.holder.AnimateViewHolder

import uz.gita.mylibrary.NewsData
import uz.gita.readnews.R
import java.util.*

class MainAdapter:RecyclerView.Adapter<MainAdapter.Holder>() {

    var onClickListener:((NewsData)->Unit)?=null
    fun onClickItem(block:(NewsData)->Unit){
        onClickListener=block
    }
    var data= ArrayList<NewsData>()
    @SuppressLint("NotifyDataSetChanged")
    fun submitItems(newsItems:List<NewsData>){
        data.clear()
        data.addAll(newsItems)
        notifyDataSetChanged()
    }
    inner class Holder(view: View):RecyclerView.ViewHolder(view){

        private val title:TextView=view.findViewById(R.id.txtTitle)
        private val desc:TextView=view.findViewById(R.id.description)
        private val like:TextView=view.findViewById(R.id.txtTag1)
        private val notLike:TextView=view.findViewById(R.id.txtTag2)
        private val seen:TextView=view.findViewById(R.id.txtVies)
        private val date:TextView=view.findViewById(R.id.txtDate)
        private val image:ImageView=view.findViewById(R.id.imageView)
        init {
            view.setOnClickListener {
                onClickListener?.invoke(data[absoluteAdapterPosition])
            }
        }
        fun bind(){
            val item=data[absoluteAdapterPosition]
            title.text=item.title
            desc.text=item.description
            like.text=item.like
            notLike.text=item.notlike
            date.text=item.date
            seen.text=item.seen
            Glide
                .with(image)
                .load(item.image)
                .centerCrop()
                .placeholder(R.drawable.ic_launcher_foreground)
                .into(image);
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
       return  Holder(LayoutInflater.from(parent.context).inflate(R.layout.item_test,parent,false))
    }

    override fun onBindViewHolder(holder: Holder, position: Int) =holder.bind()

    override fun getItemCount()=data.size



}