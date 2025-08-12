package it.softbrain.barcomall.presentation.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import it.softbrain.barcomall.data.models.Products
import it.softbrain.barcomall.databinding.HotDealsProductsBinding
import it.softbrain.barcomall.databinding.ProductsItemBinding

class ProductsAdapter(
    private var dataList: ArrayList<Products>,
    private val viewType: String = "Products",
    private val onItemClick: (Products) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    public fun filterData(filterList:ArrayList<Products>)
    {
        dataList=filterList
        notifyDataSetChanged()
    }


    class ProductViewHolder(private val binding: ProductsItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun binds(product: Products, onItemClick: (Products) -> Unit) {
            binding.apply {
                Glide.with(imgProduct).load("https://bercomall.com${product.image}")
                    .into(imgProduct)

                tvName.text = product.productName
                tvPrice.text = "₹ ${product.price}"
                tvDiscount.text =
                    "${product.discountPercentage}% Off (₹ Saving ${product.discountAmount})"
                tvMrp.text = "₹ ${product.mrp}"
            }

            itemView.setOnClickListener { onItemClick(product) }
        }

    }

    class HotDealsViewHolder(private val binding: HotDealsProductsBinding) :
        RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun binds(product: Products, onItemClick: (Products) -> Unit) {
            binding.apply {
                Glide.with(imgProduct).load("https://bercomall.com${product.image}")
                    .into(imgProduct)

                tvName.text = product.productName
                tvPrice.text = "₹ ${product.price}"
                tvDiscount.text =
                    "${product.discountPercentage}% Off (₹ Saving ${product.discountAmount})"
                tvMrp.text = "₹ ${product.mrp}"

            }

            itemView.setOnClickListener { onItemClick(product) }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewtype: Int): RecyclerView.ViewHolder {

        if (viewType.equals("HOT_DEALS", true)) {
            val binding =
                HotDealsProductsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return HotDealsViewHolder(binding)
        } else {
            val binding =
                ProductsItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return ProductViewHolder(binding)
        }
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (viewType.equals("HOT_DEALS", true)) {
            holder as HotDealsViewHolder
            holder.binds(dataList[position], onItemClick)

        } else {
            holder as ProductViewHolder
            holder.binds(dataList[position], onItemClick)

        }

    }
}