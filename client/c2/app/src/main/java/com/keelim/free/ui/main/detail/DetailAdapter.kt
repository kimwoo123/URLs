package com.keelim.free.ui.main.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.keelim.data.model.open.Url
import com.keelim.free.databinding.ItemDetailBinding

class DetailAdapter(
    val click_move: (Url) -> Unit,
) : ListAdapter<Url, DetailAdapter.ViewHolder>(diffUtil) {
    inner class ViewHolder(val binding: ItemDetailBinding): RecyclerView.ViewHolder(binding.root)
    {
        fun bind(item: Url) = with(binding){
            header.text = item.url
            move.setOnClickListener {
                click_move(item)
            }
            thumbnail.load(item.thumbnail)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemDetailBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    companion object{
        val diffUtil = object:DiffUtil.ItemCallback<Url>(){
            override fun areItemsTheSame(oldItem: Url, newItem: Url): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: Url, newItem: Url): Boolean {
                return oldItem == newItem
            }
        }
    }
}