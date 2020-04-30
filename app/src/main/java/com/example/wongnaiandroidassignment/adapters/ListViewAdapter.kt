package com.example.wongnaiandroidassignment.adapters

import android.app.Activity
import android.net.Uri
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.wongnaiandroidassignment.R
import com.example.wongnaiandroidassignment.models.Coin
import java.util.*
import com.github.twocoffeesoneteam.glidetovectoryou.GlideToVectorYou


class ListViewAdapter(
    val activity: Activity,
    val list: ArrayList<Coin>
) :
    RecyclerView.Adapter<ListViewAdapter.ViewHolder>() {

    val TYPE_1 = 1
    val TYPE_2 = 2

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        if ((position+1) % 5 == 0) {
            holder.bindItems2(position, list[position])
        } else {
            holder.bindItems(position, list[position])
        }

        when(list[position].iconType){
            "vector"->{

                val image = holder.itemView.findViewById(R.id.image) as ImageView

                val uri = Uri.parse(list[position].iconUrl)

                GlideToVectorYou
                    .init()
                    .with(activity)
                    .setPlaceHolder(R.mipmap.ic_launcher, R.mipmap.ic_launcher)
                    .load(uri, image)

            }
            "pixel"->{

                val image = holder.itemView.findViewById(R.id.image) as ImageView

                Glide.with(activity)
                    .load(list[position].iconUrl)
                    .apply(RequestOptions()
                        .placeholder(R.mipmap.ic_launcher)
                        .error(R.mipmap.ic_launcher))
                    .into(image)

            }
        }

    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun getItemViewType(position: Int): Int {

        return if ((position+1) % 5 == 0) {
            TYPE_2
        } else {
            TYPE_1
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)
            : ViewHolder {

        val itemView: View

        when (viewType) {
            TYPE_1 -> {
                itemView =
                    LayoutInflater.from(parent.context).inflate(R.layout.item_coin, parent, false)
                return ViewHolder(itemView)

            }
            TYPE_2 -> {
                itemView =
                    LayoutInflater.from(parent.context)
                        .inflate(R.layout.item_coin_fifth, parent, false)
                return ViewHolder(itemView)
            }
            else -> {
                itemView =
                    LayoutInflater.from(parent.context)
                        .inflate(R.layout.item_coin, parent, false)
                return ViewHolder(itemView)
            }
        }

    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bindItems(
            position: Int,
            coin: Coin
        ) {
            //init view
            val textTitle = itemView.findViewById(R.id.textTitle) as TextView
            val textDesc = itemView.findViewById(R.id.textDesc) as TextView
            val image = itemView.findViewById(R.id.image) as ImageView

            textTitle.text = coin.name
            textDesc.text = coin.description

//            when(coin.iconType){
//                "vector"->{
//                    val url = coin.iconUrl
//                    SvgLoader.pluck()
//                        .with(activity)
//                        .setPlaceHolder(R.mipmap.ic_launcher, R.mipmap.ic_launcher)
//                        .load(url, image)
//                }
//                "pixel"->{
//                    Glide.with(image.context)
//                        .load(coin.iconUrl)
//                        .apply(RequestOptions()
//                            .placeholder(R.mipmap.ic_launcher)
//                            .error(R.mipmap.ic_launcher))
//                        .into(image)
//                }
//            }

        }

        fun bindItems2(
            position: Int,
            coin: Coin
        ) {
            //init view
            val textTitle = itemView.findViewById(R.id.textTitle) as TextView
            val image = itemView.findViewById(R.id.image) as ImageView

            textTitle.text = coin.name

//            when(coin.iconType){
//                "vector"->{
////                    val url = coin.iconUrl
////                    VectorLoader.fetchSvg(image.context,url,image)
//                }
//                "pixel"->{
//                    Glide.with(image.context)
//                        .load(coin.iconUrl)
//                        .apply(RequestOptions()
//                            .placeholder(R.mipmap.ic_launcher)
//                            .error(R.mipmap.ic_launcher))
//                        .into(image)
//                }
//            }

        }


    }


}