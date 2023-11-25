package com.ogzkesk.home

import android.view.inputmethod.InputMethodManager
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import com.ogzkesk.core.base.BaseFragment
import com.ogzkesk.core.ext.NavArg
import com.ogzkesk.core.ext.collectFlowWithLifeCycle
import com.ogzkesk.core.ext.navigateWithSlide
import com.ogzkesk.core.util.Constants.POKEMON_NAME_KEY
import com.ogzkesk.home.adapter.HomePagingDataAdapter
import com.ogzkesk.home.content.SortDialog
import com.ogzkesk.home.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

private const val RV_ITEM_TYPE_GRID = 0
private const val RV_ITEM_TYPE_LINEAR = 1

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding, HomeViewModel>(
    FragmentHomeBinding::inflate
) {

    override val viewModel: HomeViewModel by viewModels()
    private lateinit var pagingAdapter: HomePagingDataAdapter
    private val imm by lazy { getSystemService(requireContext(), InputMethodManager::class.java) }
    private val sortDialog by lazy { SortDialog(requireContext()) }

    override fun onInitialize() {
        initAdapter()
        initUI()
    }

    private fun initAdapter() {
        pagingAdapter = HomePagingDataAdapter()
        pagingAdapter.setOnItemClickListener {
            findNavController().navigateWithSlide(
                com.ogzkesk.core.R.string.route_details, NavArg(POKEMON_NAME_KEY, it?.lowercase())
            )
        }

        initAdapterStates()

        collectFlowWithLifeCycle(viewModel.pagingFlow) { data ->
            pagingAdapter.submitData(viewLifecycleOwner.lifecycle, data)
        }
    }


    private fun initAdapterStates() {

        pagingAdapter.addLoadStateListener { state ->
            binding.layoutRefresh.apply {

                val refresh = state.refresh
                progress.isVisible = refresh is LoadState.Loading
                tvError.isVisible = refresh is LoadState.Error
                btnRetry.isVisible = refresh is LoadState.Error

                if (refresh is LoadState.Error) {
                    tvError.text = refresh.error.message
                    btnRetry.setOnClickListener { pagingAdapter.retry() }
                }
            }
        }
    }


    private fun initUI() = with(binding) {

        rvHome.adapter = pagingAdapter.withLoadStateFooter(
            footer = pagingAdapter.footerStateAdapter
        )

        rvHome.layoutManager = GridLayoutManager(requireContext(), 3).apply {
            spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                override fun getSpanSize(position: Int): Int {
                    return when (rvHome.adapter?.getItemViewType(position)) {
                        RV_ITEM_TYPE_GRID -> 1
                        RV_ITEM_TYPE_LINEAR -> 3
                        else -> 1
                    }
                }
            }
        }


        tvSearch.doAfterTextChanged { edt ->
            if (edt == null) return@doAfterTextChanged

            viewLifecycleOwner.lifecycleScope.launch {
                delay(500)
                viewModel.onSearch(edt.toString())
            }
        }

        btnSort.setOnClickListener {
            sortDialog.showDialog(viewModel::onSortTypeChanged)
        }

        inputLayoutSearch.setEndIconOnClickListener {
            tvSearch.setText("")
            tvSearch.clearFocus()
            imm?.hideSoftInputFromWindow(binding.tvSearch.windowToken, 0)
        }
    }
}
