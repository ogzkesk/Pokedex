package com.ogzkesk.settings

import androidx.fragment.app.viewModels
import com.ogzkesk.core.base.base.BaseFragment
import com.ogzkesk.details.databinding.FragmentDetailsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailsFragment : BaseFragment<FragmentDetailsBinding, DetailViewModel>(
    FragmentDetailsBinding::inflate,
) {

    override val viewModel: DetailViewModel by viewModels()

    override fun onInitialize() {

    }
}
