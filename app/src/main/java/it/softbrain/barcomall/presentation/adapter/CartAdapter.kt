package it.softbrain.barcomall.presentation.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import it.softbrain.barcomall.data.models.Cart
import it.softbrain.barcomall.databinding.CartItemBinding

class CartAdapter(private val dataList: List<Cart>) :
    RecyclerView.Adapter<CartAdapter.CartViewHolder>() {


    class CartViewHolder(private val binding: CartItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun binds(cart: Cart) {
            binding.apply {
                Glide.with(imgProduct.context).load("https://bercomall.com${cart.picture}")
                    .into(imgProduct)
                tvName.text = cart.productName
                tvMrp.text = "MRP : ${cart.mrp}"
                tvPtr.text = "Our Price : ${cart.total}"
                tvDiscount.text ="Discount ${cart.discount}"
                etQuantity.setText(cart.quantity)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val binding = CartItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CartViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        val cart = dataList[position]
        holder.binds(cart)
    }
}