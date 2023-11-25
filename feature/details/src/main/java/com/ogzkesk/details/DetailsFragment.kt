package com.ogzkesk.details

import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import coil.decode.SvgDecoder
import coil.load
import com.ogzkesk.core.R
import com.ogzkesk.core.base.BaseFragment
import com.ogzkesk.core.ext.collectFlowWithLifeCycle
import com.ogzkesk.core.ext.getArgument
import com.ogzkesk.core.model.PokemonModel
import com.ogzkesk.core.ui.ErrorDialog
import com.ogzkesk.core.util.Constants.POKEMON_NAME_KEY
import com.ogzkesk.details.adapter.StatAdapter
import com.ogzkesk.details.content.ColorModifier
import com.ogzkesk.details.databinding.FragmentDetailsBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.text.DecimalFormat

@AndroidEntryPoint
class DetailsFragment : BaseFragment<FragmentDetailsBinding, DetailsViewModel>(
    FragmentDetailsBinding::inflate,
) {

    private lateinit var statAdapter: StatAdapter
    private lateinit var colorModifier: ColorModifier
    override val viewModel: DetailsViewModel by viewModels()

    override fun onInitialize() {
        initData()
        observeEvents()
    }

    private fun initData() {
        statAdapter = StatAdapter()
        colorModifier = ColorModifier(requireContext(), binding)

        val arg = findNavController().getArgument<String>(POKEMON_NAME_KEY)
        viewModel.getPokemon(arg)

        collectFlowWithLifeCycle(viewModel.pokemon) { data ->
            initUI(data)
            colorModifier.init(data).addChips().setColors()
        }
    }

    private fun observeEvents() {
        val errorDialog = ErrorDialog(requireContext())

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {

                viewModel.event.collect { event ->
                    when (event) {
                        is DetailsEvent.Error -> {
                            binding.layoutLoading.root.isVisible = false
                            errorDialog.show(event.message) {}
                        }

                        DetailsEvent.Loading -> {
                            binding.layoutLoading.root.isVisible = true
                        }

                        DetailsEvent.Success -> {
                            binding.layoutLoading.root.isVisible = false
                        }
                    }
                }
            }
        }
    }

    private fun initUI(data: PokemonModel?) = with(binding) {

        btnNavigation.setOnClickListener {
            findNavController().popBackStack()
        }

        if (data == null) return@with

        statAdapter.submitList(data.stats)
        rvStats.adapter = statAdapter

        tvId.text = data.no
        tvTitle.text = data.name
        tvInfo.text = data.info
        tvMoves.text = data.abilities
        tvWeight.text = getString(R.string.weight_stat, data.weight.toDouble() / 10)
        tvHeight.text = getString(R.string.height_stat, data.height.toDouble() / 10)
        ivPokemon.load(data.imgUrl) {
            decoderFactory(SvgDecoder.Factory())
            crossfade(true)
            crossfade(300)
        }
    }
}


