package it.softbrain.barcomall.presentation.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import it.softbrain.barcomall.databinding.NavCatgeoriesItemBinding

class NavCategoryAdapter():RecyclerView.Adapter<NavCategoryAdapter.NavCategoryViewHolder>() {



    class NavCategoryViewHolder(private val binding:NavCatgeoriesItemBinding) : RecyclerView.ViewHolder(binding.root){

        fun binds()
        {

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NavCategoryViewHolder {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: NavCategoryViewHolder, position: Int) {
        TODO("Not yet implemented")
    }
}