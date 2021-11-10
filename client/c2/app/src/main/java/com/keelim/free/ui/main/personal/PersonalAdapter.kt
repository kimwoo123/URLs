package com.keelim.free.ui.main.personal

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.keelim.data.model.Folder
import com.keelim.free.databinding.ItemFolderBinding


class PersonalAdapter(
    private val longClick: (Folder) -> Unit,
) : ListAdapter<Folder, PersonalAdapter.ViewHolder>(diffUtil) {
    inner class ViewHolder(private val binding: ItemFolderBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Folder)  = with(binding){
            root.setOnLongClickListener {
                longClick(item)
                return@setOnLongClickListener true
            }
            if(item.shared) {
                permission.text = "공유된 폴더 입니다."
            } else{
                permission.text = "개인 폴더 입니다."
            }
            title.text = item.folder_id
            description.text  = item.folder_name
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemFolderBinding.inflate(
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
        val diffUtil = object : DiffUtil.ItemCallback<Folder>() {
            override fun areItemsTheSame(oldItem: Folder, newItem: Folder): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: Folder, newItem: Folder): Boolean {
                return oldItem == newItem
            }
        }
    }
}