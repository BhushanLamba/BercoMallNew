package it.softbrain.barcomall.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import it.softbrain.barcomall.data.models.Brands
import it.softbrain.barcomall.databinding.BrandsItemBinding

class BrandsAdapter(
    private val dataList: ArrayList<Brands>,
    private val onItemClick: (Brands) -> Unit
) :
    RecyclerView.Adapter<BrandsViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BrandsViewHolder {
        val binding = BrandsItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BrandsViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(holder: BrandsViewHolder, position: Int) {
        holder.binds(dataList[position], onItemClick)

    }


}

class BrandsViewHolder(private val brandsItemBinding: BrandsItemBinding) :
    RecyclerView.ViewHolder(brandsItemBinding.root) {

    fun binds(brands: Brands, onItemClick: (Brands) -> Unit) {
        brandsItemBinding.apply {
            Glide.with(imgItem).load("https://bercomall.com${brands.image}").into(imgItem)

            itemView.setOnClickListener {
                onItemClick(brands)
            }
        }
    }

}
