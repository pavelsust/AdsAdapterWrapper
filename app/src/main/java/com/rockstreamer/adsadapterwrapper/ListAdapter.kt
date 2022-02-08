package com.rockstreamer.adsadapterwrapper

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.rockstreamer.adsadapterwrapper.databinding.ItemListBinding

class ListAdapter :RecyclerView.Adapter<ListAdapter.CustomDataView>(){

    var dataList: MutableList<String> = ArrayList()

    fun addAll(item: List<String>){
        dataList.clear()
        dataList.addAll(item)
        notifyDataSetChanged()
    }

    inner class CustomDataView(private var binding: ItemListBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(item:String){
            binding.item = item
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomDataView {
        return CustomDataView(ItemListBinding.inflate(LayoutInflater.from(parent.context), parent ,false))
    }

    override fun onBindViewHolder(holder: CustomDataView, position: Int) {
        var item = dataList[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int  = dataList.size
}