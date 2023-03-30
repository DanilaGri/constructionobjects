package com.ex.constructionobjects.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ex.constructionobjects.R
import com.ex.constructionobjects.data.model.Construction
import com.ex.constructionobjects.databinding.ItemConstructionBinding

class ConstructionAdapter(
    private val mOnConstructionClickListener: OnConstructionClickListener
) : ListAdapter<Construction, ConstructionAdapter.ConstructionViewHolder>(DiffCallback) {

    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<Construction>() {
            override fun areItemsTheSame(oldItem: Construction, newItem: Construction): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Construction, newItem: Construction): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ConstructionViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemConstructionBinding.inflate(inflater, parent, false)
        return ConstructionViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ConstructionViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ConstructionViewHolder(private val binding: ItemConstructionBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(construction: Construction) {
            binding.apply {
                tvConstructionName.text = construction.name
                // загрузить изображение превью с помощью библиотеки Glide
                Glide.with(itemView.context)
                    .load(construction.preview)
                    .placeholder(R.drawable.ic_launcher_background)
                    .into(ivConstructionPreview)

                ivConstructionDelete.setOnClickListener {
                    mOnConstructionClickListener.onDelete(adapterPosition)
                }

                ivConstructionEdit.setOnClickListener {
                    mOnConstructionClickListener.onEdit(adapterPosition)
                }
            }
        }
    }

    interface OnConstructionClickListener {
        fun onEdit(position: Int)
        fun onDelete(position: Int)
    }
}
