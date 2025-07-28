package it.softbrain.barcomall.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import it.softbrain.barcomall.data.models.Categories
import it.softbrain.barcomall.databinding.CategoryItemBinding
import it.softbrain.barcomall.databinding.NavCatgeoriesItemBinding

class CategoryAdapter(
    private val dataList: ArrayList<Categories>,
    private val type: String = "Categories",
    private val onItemClick: (Categories) -> Unit

) :
    RecyclerView.Adapter<ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        if (type.equals("NavMenu", true)) {
            val binding =
                NavCatgeoriesItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return CategoryNavViewHolder(binding)

        } else {
            val binding =
                CategoryItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

            return CategoryViewHolder(binding)
        }


    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (type.equals("NavMenu", true)) {
            holder as CategoryNavViewHolder
            holder.binds(dataList[position], onItemClick)
        } else {
            holder as CategoryViewHolder
            holder.binds(dataList[position], onItemClick)
        }

    }
}

class CategoryViewHolder(private val categoryItemBinding: CategoryItemBinding) :
    ViewHolder(categoryItemBinding.root) {

    fun binds(categoryModel: Categories, onItemClick: (Categories) -> Unit) {
        categoryItemBinding.apply {
            Glide.with(imgItem).load("https://bercomall.com${categoryModel.image}")
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imgItem)


            tvName.text = categoryModel.name


            itemView.setOnClickListener {
                onItemClick(categoryModel)
            }
        }
    }

}

class CategoryNavViewHolder(private val categoryItemBinding: NavCatgeoriesItemBinding) :
    ViewHolder(categoryItemBinding.root) {

    fun binds(categoryModel: Categories, onItemClick: (Categories) -> Unit) {
        categoryItemBinding.apply {
            Glide.with(imgItem).load("https://bercomall.com${categoryModel.image}")
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imgItem)


            tvName.text = categoryModel.name


            itemView.setOnClickListener {
                onItemClick(categoryModel)
            }
        }
    }

}
