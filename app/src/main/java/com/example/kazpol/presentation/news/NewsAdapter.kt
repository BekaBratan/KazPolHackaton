package com.example.kazpol.presentation.news

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.kazpol.data.model.Article
import com.example.kazpol.databinding.ItemNewsBinding
import com.example.unihub.utils.RcViewItemClickIdCallback

open class NewsAdapter: RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {

    private val diffCallback = object : DiffUtil.ItemCallback<Article>() {
        override fun areItemsTheSame(
            oldItem: Article,
            newItem: Article
        ): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(
            oldItem: Article,
            newItem: Article
        ): Boolean {
            return oldItem == newItem
        }
    }

    private val differ = AsyncListDiffer(this, diffCallback)

    fun submitList(list: List<Article>) {
        differ.submitList(list)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): NewsViewHolder {
        return NewsViewHolder(
            ItemNewsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(
        holder: NewsViewHolder,
        position: Int
    ) {
        holder.onBind(differ.currentList[position])
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    var listenerClickAtItem: RcViewItemClickIdCallback? = null
    fun setOnLikeClickListener(listener: RcViewItemClickIdCallback) {
        this.listenerClickAtItem = listener
    }

    inner class NewsViewHolder(private var binding: ItemNewsBinding): RecyclerView.ViewHolder(binding.root){
        fun onBind(item: Article){
            binding.run {
                Glide.with(itemView)
                    .load(item.urlToImage)
                    .into(ivNewsImage)
                tvNewsTitle.text = item.title
                tvNewsDescription.text = item.description
                root.setOnClickListener {
                    listenerClickAtItem?.onClick(adapterPosition)
                }
            }
        }
    }
}