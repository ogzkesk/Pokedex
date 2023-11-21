package com.ogzkesk.details

import androidx.fragment.app.viewModels
import com.ogzkesk.core.base.BaseFragment
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
