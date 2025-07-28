package it.softbrain.barcomall.presentation.adapter

import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import it.softbrain.barcomall.R

class ImageSliderAdapter(private val imageList: List<String>,private val onClick:(String)->Unit) :
    RecyclerView.Adapter<ImageSliderAdapter.ImageSliderViewHolder>() {


    class ImageSliderViewHolder(val imageView: ImageView) :
        RecyclerView.ViewHolder(imageView) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageSliderViewHolder {
        val imageView = ImageView(parent.context).apply {
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT,
            )
        }

        return ImageSliderViewHolder(imageView)
    }

    override fun getItemCount(): Int = imageList.size

    override fun onBindViewHolder(holder: ImageSliderViewHolder, position: Int) {
        Glide.with(holder.imageView.context)
            .load(imageList[position])
            .placeholder(R.drawable.logo)
            .override(800, 600)                  // resize to prevent memory issues
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(holder.imageView)


        holder.imageView.setOnClickListener{
            onClick(imageList[position])
        }
    }
}