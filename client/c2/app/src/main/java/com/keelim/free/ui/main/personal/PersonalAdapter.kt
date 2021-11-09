package com.keelim.free.ui.main.personal

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.keelim.data.model.open.Url
import com.keelim.free.databinding.ItemUrlBinding


class PersonalAdapter(
    private val longClick: (Url) -> Unit,
) : ListAdapter<Url, PersonalAdapter.ViewHolder>(diffUtil) {
    inner class ViewHolder(private val binding: ItemUrlBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Url) {
            binding.root.setOnLongClickListener {
                longClick(item)
                return@setOnLongClickListener true
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemUrlBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<Url>() {
            override fun areItemsTheSame(oldItem: Url, newItem: Url): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: Url, newItem: Url): Boolean {
                return oldItem == newItem
            }
        }
    }
}