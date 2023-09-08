package com.abhishek.shelfapp.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.abhishek.shelfapp.R
import com.abhishek.shelfapp.data.model.Book
import com.abhishek.shelfapp.databinding.ItemBookBinding
import com.abhishek.shelfapp.ui.home.HomeViewModel
import com.abhishek.shelfapp.utils.common.visible
import com.squareup.picasso.MemoryPolicy
import com.squareup.picasso.NetworkPolicy
import com.squareup.picasso.Picasso

class BookAdapter(
    private val data: List<Book>,
    private val clickListener: OnItemClickListener,
    private val viewModel: HomeViewModel,
) : RecyclerView.Adapter<BookAdapter.BookViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
        val binding = ItemBookBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return BookViewHolder(binding, viewModel)
    }

    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        val currentItem = data[position]
        holder.onBind(currentItem, clickListener)
        holder.itemView.setOnClickListener {
            clickListener.onItemClick(currentItem)
        }
    }

    override fun getItemCount(): Int = data.size

    interface OnItemClickListener {
        fun onItemClick(item: Book)
    }

    class BookViewHolder(private val binding: ItemBookBinding, private val viewModel: HomeViewModel) :
        RecyclerView.ViewHolder(binding.root) {

        fun onBind(currentItem: Book, clickListener: OnItemClickListener) = with(binding) {
            setupClickListeners(currentItem)
            setupObservers()
            setupImageOption(currentItem.image)
            tvTitle.text = currentItem.title
            tvHits.text = itemView.context.getString(
                R.string.label_hits_number,
                currentItem.hits
            )
            if(viewModel.getIsFavourite(currentItem.id)){
                ivStar.setImageDrawable(itemView.context.getDrawable(R.drawable.ic_star_filled))
            } else{
                ivStar.setImageDrawable(itemView.context.getDrawable(R.drawable.ic_star_border))
            }
        }

        private fun setupClickListeners(item: Book) {
            binding.apply {
                ivStar.setOnClickListener {
                    if(viewModel.getIsFavourite(item.id)){
                        item.isFavourite = false
                        viewModel.setIsFavourite(item.id,false)
                        ivStar.setImageDrawable(itemView.context.getDrawable(R.drawable.ic_star_border))
                    } else {
                        item.isFavourite = true
                        viewModel.setIsFavourite(item.id,true)
                        ivStar.setImageDrawable(itemView.context.getDrawable(R.drawable.ic_star_filled))
                    }
                }
            }
        }

        private fun setupObservers() {}

        private fun setupImageOption(imageUrl: String) {
            if (imageUrl.isNotEmpty()) {
                Picasso.get().load(imageUrl)
                    .memoryPolicy(MemoryPolicy.NO_CACHE)
                    .networkPolicy(NetworkPolicy.NO_CACHE)
                    .into(binding.ivImage)
                binding.ivImage.visible(true)
            }
        }

    }
}


