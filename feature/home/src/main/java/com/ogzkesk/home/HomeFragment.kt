package com.ogzkesk.home

import androidx.fragment.app.viewModels
import com.ogzkesk.core.base.base.BaseFragment
import com.ogzkesk.home.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding, HomeViewModel>(
    FragmentHomeBinding::inflate
) {

    override val viewModel: HomeViewModel by viewModels()

    override fun onInitialize() {

    }

}
