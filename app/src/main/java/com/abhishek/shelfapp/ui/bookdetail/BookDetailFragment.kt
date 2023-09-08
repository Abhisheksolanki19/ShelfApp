package com.abhishek.shelfapp.ui.bookdetail

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources.getDrawable
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.abhishek.shelfapp.R
import com.abhishek.shelfapp.data.model.Book
import com.abhishek.shelfapp.databinding.FragmentBookDetailBinding
import com.abhishek.shelfapp.utils.common.CalenderUtils
import com.abhishek.shelfapp.utils.common.visible
import com.squareup.picasso.MemoryPolicy
import com.squareup.picasso.NetworkPolicy
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BookDetailFragment : Fragment() {

    private val viewModel: BookDetailViewModel by viewModels()
    private lateinit var binding: FragmentBookDetailBinding
    private lateinit var book: Book

    companion object {
        private const val EXTRA_BOOK = "EXTRA_BOOK"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBookDetailBinding.inflate(layoutInflater)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupClickListeners()
        processExtraData()
        setupData()
    }

    private fun setupClickListeners() {
        binding.apply {
            ivStar.setOnClickListener {
                if(viewModel.getIsFavourite(book.id)){
                    book.isFavourite = false
                    viewModel.setIsFavourite(book.id,false)
                    ivStar.setImageDrawable(getDrawable(requireContext(),R.drawable.ic_star_border))
                } else {
                    book.isFavourite = true
                    viewModel.setIsFavourite(book.id,true)
                    ivStar.setImageDrawable(getDrawable(requireContext(),R.drawable.ic_star_filled))
                }
            }
        }
    }

    private fun setupData() {
        binding.apply {
            setupImageOption(book.image)
            tvTitle.text = book.title
            tvHits.text = getString(
                R.string.label_hits_number,
                book.hits
            )
            tvAlias.text = getString(
                R.string.label_alias,
                book.alias
            )
            tvUpdated.text = getString(
                R.string.label_updated_on,
                CalenderUtils.millisToDate(book.lastChapterDate)
            )
            if(viewModel.getIsFavourite(book.id)){
                ivStar.setImageDrawable(getDrawable(requireContext(),R.drawable.ic_star_filled))
            } else {
                ivStar.setImageDrawable(getDrawable(requireContext(),R.drawable.ic_star_border))
            }
        }
    }

    private fun setupImageOption(imageUrl: String) {
        if (imageUrl.isNotEmpty()) {
            Picasso.get().load(imageUrl)
                .memoryPolicy(MemoryPolicy.NO_CACHE)
                .networkPolicy(NetworkPolicy.NO_CACHE)
                .into(binding.ivImage)
            binding.ivImage.visible(true)
        }
    }

    private fun processExtraData() {
        book = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            arguments?.getParcelable(EXTRA_BOOK, Book::class.java)!!
        } else {
            arguments?.getParcelable<Book>(EXTRA_BOOK)!!
        }
    }
}