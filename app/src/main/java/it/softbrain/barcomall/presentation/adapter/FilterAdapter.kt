package it.softbrain.barcomall.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import it.softbrain.barcomall.databinding.FilterItemBinding

class FilterAdapter(private val dataList:List<String>,private val applyFilter:(String)->Unit):RecyclerView.Adapter<FilterAdapter.FilterViewHolder>() {


    class FilterViewHolder(private val binding:FilterItemBinding,private val applyFilter:(String)->Unit):RecyclerView.ViewHolder(binding.root) {


        fun binds(filterValue:String)
        {
            binding.apply {
                ckbFilter.text=filterValue

                ckbFilter.setOnCheckedChangeListener{
                    ckb,isChecked->

                    applyFilter(filterValue)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilterViewHolder {
        val binding=FilterItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return FilterViewHolder(binding,applyFilter)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(holder: FilterViewHolder, position: Int) {
        holder.binds(dataList[position])
    }
}