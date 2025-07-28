package it.softbrain.barcomall.presentation.adapter

import android.content.Context
import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import it.softbrain.barcomall.R
import it.softbrain.barcomall.data.models.Products
import it.softbrain.barcomall.databinding.HotDealParentItemBinding

class HotDealsAdapter(
    private val titleList: ArrayList<String>,
    private val hotDealsProductList: ArrayList<ArrayList<Products>>,
    private val context: Context,
    private val itemClick: (Products) -> Unit
) : RecyclerView.Adapter<HotDealsAdapter.HotDealsViewHolder>() {

    val sharedPool = RecyclerView.RecycledViewPool()

    class HotDealsViewHolder(
        private val binding: HotDealParentItemBinding
    ) :
        RecyclerView.ViewHolder(binding.root) {

        fun binds(
            title: String,
            productList: ArrayList<Products>,
            context: Context,
            sharedPool: RecyclerView.RecycledViewPool,
            itemClick: (Products) -> Unit
        ) {
            binding.apply {
                if (title.contains("_")) {
                    tvTitle.text = title.replace("_", " ")
                } else {
                    tvTitle.text = title
                }


                val adapter = ProductsAdapter(productList, "HOT_DEALS")
                { product ->
                    itemClick(product)
                }

                val layoutManager =
                    GridLayoutManager(context, 1, GridLayoutManager.HORIZONTAL, false)
                layoutManager.initialPrefetchItemCount = 4
                productsRecycler.adapter = adapter
                productsRecycler.layoutManager = layoutManager
                productsRecycler.setHasFixedSize(true)
                productsRecycler.setRecycledViewPool(sharedPool)


            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HotDealsViewHolder {
        val binding =
            HotDealParentItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HotDealsViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return titleList.size
    }

    override fun onBindViewHolder(holder: HotDealsViewHolder, position: Int) {
        holder.binds(
            titleList[position],
            hotDealsProductList[position],
            context,
            sharedPool,
            itemClick
        )

        val itemBgColor = when (position % 3) {
            0 -> R.color.light_pink
            1 -> R.color.light_blue
            else -> R.color.light_orange
        }

        holder.itemView.setBackgroundColor(
            ContextCompat.getColor(holder.itemView.context, itemBgColor)
        )

    }
}