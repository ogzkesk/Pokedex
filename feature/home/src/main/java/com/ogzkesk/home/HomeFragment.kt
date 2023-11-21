package com.ogzkesk.home

import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.ogzkesk.core.base.BaseFragment
import com.ogzkesk.core.ext.NavArg
import com.ogzkesk.core.ext.collectFlowWithLifeCycle
import com.ogzkesk.core.ext.navigateWithSlide
import com.ogzkesk.core.ui.CustomDialog
import com.ogzkesk.home.adapter.HomeListAdapter
import com.ogzkesk.home.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

private const val POKEMON_NAME_KEY = "pokemon_name"

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding, HomeViewModel>(
    FragmentHomeBinding::inflate
) {

    override val viewModel: HomeViewModel by viewModels()
    private lateinit var listAdapter: HomeListAdapter

    // TODO fix checked radios.
    // TODO fix recycler not in center.
    // TODO find search api or search from list.
    // TODO fix searchBar hint.
    // TODO then go details screen without viewPager.
    // TODO then implement pagination maybe
    private val sortDialog by lazy { SortDialog(requireContext()) }

    override fun onInitialize() {
        initAdapter()
        initUI()
        observeEvents()
        observeData()
    }

    private fun observeData() {
        collectFlowWithLifeCycle(viewModel.pokemons) { data ->
            listAdapter.submitList(data.results)
        }
    }

    private fun observeEvents() {
        val customDialog = CustomDialog(requireContext())
        collectFlowWithLifeCycle(viewModel.event) { event ->
            when (event) {
                is HomeEvent.Error -> {
                    customDialog.dismiss()
                    customDialog.showError(event.message)
                }

                HomeEvent.Loading -> {
                    println("homeEvent LOADING ..........")
                    customDialog.showProgress()
                }

                HomeEvent.Success -> {
                    customDialog.dismiss()
                }
            }
        }
    }

    private fun initAdapter() {
        listAdapter = HomeListAdapter()
        listAdapter.setOnClickListener { pokemon ->
            findNavController().navigateWithSlide(
                com.ogzkesk.core.R.string.route_details,
                NavArg(POKEMON_NAME_KEY, pokemon.name)
            )
        }
    }

    private fun initUI() = with(binding) {
        rvHome.adapter = listAdapter
        rvHome.setHasFixedSize(true)

        tvSearch.doAfterTextChanged { edt ->
            edt?.let { editable ->
                viewLifecycleOwner.lifecycleScope.launch {
                    delay(1000)
                    viewModel.onSearch(editable.toString())
                }
            }
        }

        btnSort.setOnClickListener {
            SortDialog(requireContext()).showDialog(viewModel::onSortTypeChanged)
        }
    }
}
