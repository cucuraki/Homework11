package com.example.homework11


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.homework11.databinding.Item1LayoutBinding
import com.example.homework11.databinding.Item2LayoutBinding

class MyAdapter() : ListAdapter<Item, RecyclerView.ViewHolder>(MyDiffUtil()) {
    companion object {
        private const val FIRST_TYPE = 1
        private const val DEFAULT_SPECIAL = "Type 2 item"
    }

    private lateinit var mListener: (Int) -> Unit
    private lateinit var updateListener: (Int) -> Unit
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        if (viewType == FIRST_TYPE) Item1ViewHolder(
            Item1LayoutBinding.inflate(LayoutInflater.from(parent.context))
        ) else Item2ViewHolder(
            Item2LayoutBinding.inflate(LayoutInflater.from(parent.context))
        )


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        holder.apply {
            if (this is Item1ViewHolder)
                name.text = getItem(position).name
            else if (this is Item2ViewHolder) {
                val item = getItem(position)
                name.text = item.name
                if (item.type2Special.length in 1..10)
                    type2Special.text = item.type2Special
                else
                    type2Special.text = DEFAULT_SPECIAL
            }
        }
    }

    override fun getItemViewType(position: Int) = getItem(position).type

    fun setOnDeleteListener(onDelete: (Int) -> Unit) {
        mListener = onDelete
    }

    fun setOnUpdateListener(onUpdate: (Int) -> Unit) {
        updateListener = onUpdate
    }

    inner class Item1ViewHolder(binding: Item1LayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val name = binding.name

        init {
            if (!this@MyAdapter::mListener.isInitialized)
                mListener = {}
            binding.delete.setOnClickListener { mListener(adapterPosition) }

            if (!this@MyAdapter::updateListener.isInitialized)
                updateListener = {}
            binding.update.setOnClickListener { updateListener(adapterPosition) }
        }
    }

    inner class Item2ViewHolder(binding: Item2LayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val name = binding.name
        val type2Special = binding.type2Special

        init {
            if (!this@MyAdapter::mListener.isInitialized)
                mListener = {}
            binding.delete.setOnClickListener { mListener(adapterPosition) }

            if (!this@MyAdapter::updateListener.isInitialized)
                updateListener = {}
            binding.update.setOnClickListener { updateListener(adapterPosition) }
        }
    }
}