package com.abhishek.shelfapp.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.abhishek.shelfapp.R
import com.abhishek.shelfapp.data.model.Book
import com.abhishek.shelfapp.databinding.FragmentHomeBinding
import com.abhishek.shelfapp.ui.home.adapter.BookAdapter
import com.abhishek.shelfapp.utils.common.visible
import com.abhishek.shelfapp.utils.display.Toaster
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class HomeFragment : Fragment(), BookAdapter.OnItemClickListener {
    private lateinit var binding: FragmentHomeBinding
    private val viewModel: HomeViewModel by viewModels()
    private lateinit var bookAdapter: BookAdapter
    private lateinit var bookList: List<Book>
    private var ascendingOrderSwitchIsChecked = false

    companion object {
        private const val EXTRA_BOOK = "EXTRA_BOOK"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupClickListeners()
        setupObservers()
    }

    private fun setupObservers() {
        viewModel.booksList.observe(viewLifecycleOwner, Observer {
            bookList = it
            setupRecyclerView(bookList)
        })
    }

    private fun setupClickListeners() {
        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    requireActivity().finish()
                }
            })

        binding.apply {
            btnFavs.setOnClickListener {
                btnFavs.background =
                    ContextCompat.getDrawable(requireActivity(), R.drawable.button_background)
                btnTitle.background = ContextCompat.getDrawable(
                    requireActivity(),
                    R.drawable.background_transparent_with_border
                )
                btnHits.background = ContextCompat.getDrawable(
                    requireActivity(),
                    R.drawable.background_transparent_with_border
                )
                btnFavs.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
                btnTitle.setTextColor(ContextCompat.getColor(requireContext(), R.color.black100))
                btnHits.setTextColor(ContextCompat.getColor(requireContext(), R.color.black100))
                filterDataForFavs()
            }

            btnTitle.setOnClickListener {
                btnTitle.background =
                    ContextCompat.getDrawable(requireActivity(), R.drawable.button_background)
                btnFavs.background = ContextCompat.getDrawable(
                    requireActivity(),
                    R.drawable.background_transparent_with_border
                )
                btnHits.background = ContextCompat.getDrawable(
                    requireActivity(),
                    R.drawable.background_transparent_with_border
                )
                btnTitle.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
                btnFavs.setTextColor(ContextCompat.getColor(requireContext(), R.color.black100))
                btnHits.setTextColor(ContextCompat.getColor(requireContext(), R.color.black100))
                var sortedList = arrayListOf<Book>()
                if (ascendingOrderSwitchIsChecked) {
                    sortedList.addAll(bookList.sortedBy { it.lastChapterDate })
                } else {
                    sortedList.addAll(bookList.sortedByDescending { it.lastChapterDate })
                }
                setupRecyclerView(sortedList)
            }

            btnHits.setOnClickListener {
                btnHits.background =
                    ContextCompat.getDrawable(requireActivity(), R.drawable.button_background)
                btnFavs.background = ContextCompat.getDrawable(
                    requireActivity(),
                    R.drawable.background_transparent_with_border
                )
                btnTitle.background = ContextCompat.getDrawable(
                    requireActivity(),
                    R.drawable.background_transparent_with_border
                )
                btnHits.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
                btnFavs.setTextColor(ContextCompat.getColor(requireContext(), R.color.black100))
                btnTitle.setTextColor(ContextCompat.getColor(requireContext(), R.color.black100))
                var sortedList = arrayListOf<Book>()
                if (ascendingOrderSwitchIsChecked) {
                    sortedList.addAll(bookList.sortedBy { it.lastChapterDate })
                } else {
                    sortedList.addAll(bookList.sortedByDescending { it.lastChapterDate })
                }
                setupRecyclerView(sortedList)
            }

            ascendingOrderSwitch.setOnCheckedChangeListener { buttonView, isChecked ->
                ascendingOrderSwitchIsChecked = isChecked
            }

            ivLogout.setOnClickListener {
                viewModel.setIsUserLoggedIn(false)
                Toaster.show(requireContext(),R.string.label_logout_success)
                findNavController().navigate(R.id.action_homeFragment_to_loginFragment)
            }
        }
    }

    private fun filterDataForFavs() {
        val favouriteBooksList = bookList.filter { viewModel.getIsFavourite(it.id) }
        if (!favouriteBooksList.isNullOrEmpty()) {
            var sortedList = arrayListOf<Book>()
            if (ascendingOrderSwitchIsChecked) {
                sortedList.addAll(favouriteBooksList.sortedBy { it.lastChapterDate })
            } else {
                sortedList.addAll(favouriteBooksList.sortedByDescending { it.lastChapterDate })
            }
            setupRecyclerView(sortedList)
        } else {
            binding.rvBooks.visible(false)
            binding.tvNoFavBooks.visible(true)
        }
    }

    private fun setupRecyclerView(dataList: List<Book>) {
        binding.rvBooks.run {
            bookAdapter = BookAdapter(dataList, this@HomeFragment, viewModel)
            adapter = bookAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
        binding.rvBooks.visible(true)
        binding.tvNoFavBooks.visible(false)
    }

    override fun onItemClick(item: Book) {
        val bundle = Bundle()
        bundle.putParcelable(EXTRA_BOOK, item)
        findNavController().navigate(R.id.action_homeFragment_to_bookDetailFragment, bundle)
    }
}