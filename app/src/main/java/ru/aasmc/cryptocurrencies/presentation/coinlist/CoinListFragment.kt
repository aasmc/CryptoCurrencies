package ru.aasmc.cryptocurrencies.presentation.coinlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.fragment.app.*
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.flow.collect
import ru.aasmc.cryptocurrencies.R
import ru.aasmc.cryptocurrencies.databinding.FragmentCoinListBinding
import ru.aasmc.cryptocurrencies.domain.model.NetworkUnavailable
import ru.aasmc.cryptocurrencies.presentation.CoinsSharedViewModel
import ru.aasmc.cryptocurrencies.presentation.CoinsSharedViewModelFactory
import ru.aasmc.cryptocurrencies.presentation.SharedViewEffects
import ru.aasmc.cryptocurrencies.presentation.coinhistory.CoinHistoryFragment
import ru.aasmc.cryptocurrencies.presentation.coinlist.adapter.CoinAdapter

class CoinListFragment : Fragment() {

    private var _binding: FragmentCoinListBinding? = null

    private val binding: FragmentCoinListBinding get() = _binding!!
    private val viewModel: CoinListFragmentViewModel by viewModels { CoinListFragmentViewModelFactory }
    private val sharedViewModel: CoinsSharedViewModel by activityViewModels { CoinsSharedViewModelFactory }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCoinListBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupUI()
        requestCoinsList()
    }

    private fun setupUI() {
        setupToolbar()
        val adapter = createAdapter()
        setupRecyclerView(adapter)
        observeViewStateUpdates(adapter)
        subscribeToViewEffects()
        subscribeToSharedViewEffects()
    }

    private fun setupToolbar() {
        (requireActivity() as AppCompatActivity).supportActionBar?.apply {
            title = getString(R.string.app_name)
        }
    }

    private fun createAdapter(): CoinAdapter {
        return CoinAdapter { coinId, name -> navigateToHistory(coinId, name) }
    }

    private fun navigateToHistory(coinId: String, name: String) {
        val bundle = bundleOf(
            CoinHistoryFragment.COIN_ID to coinId,
            CoinHistoryFragment.COIN_NAME to name
        )

        parentFragmentManager.commit {
            setReorderingAllowed(true)
            replace<CoinHistoryFragment>(R.id.fragment_container, args = bundle)
            addToBackStack(null)
        }
    }

    private fun setupRecyclerView(coinAdapter: CoinAdapter) {
        binding.coinsRecyclerView.apply {
            adapter = coinAdapter

            addItemDecoration(
                DividerItemDecoration(
                    requireContext(),
                    DividerItemDecoration.VERTICAL
                )
            )
        }
    }

    private fun observeViewStateUpdates(adapter: CoinAdapter) {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.viewState.collect { updateUi(it, adapter) }
        }
    }

    private fun updateUi(viewState: CoinListFragmentViewState, adapter: CoinAdapter) {
        adapter.submitList(viewState.coins)
        binding.loadingProgressBar.isVisible = viewState.loading
    }

    private fun subscribeToViewEffects() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.viewEffects.collect {
                when (it) {
                    is CoinListFragmentViewEffects.Failure -> handleFailure(it.cause)
                }
            }
        }
    }

    private fun subscribeToSharedViewEffects() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            sharedViewModel.sharedViewEffects.collect {
                when (it) {
                    is SharedViewEffects.PriceVariation -> notifyOfPriceVariation(it.variation)
                }
            }
        }
    }

    private fun notifyOfPriceVariation(variation: Int) {
        val message = getString(R.string.price_variation_message, variation)
        showSnackBar(message)
    }

    private fun handleFailure(cause: Throwable) {
        binding.loadingProgressBar.isInvisible = true

        val message = when (cause) {
            is NetworkUnavailable -> getString(R.string.network_unavailable_error_message)
            else -> getString(R.string.generic_error_message)
        }

        showSnackBar(message)
    }

    private fun showSnackBar(message: String) {
        Snackbar.make(requireView(), message, Snackbar.LENGTH_SHORT).show()
    }

    private fun requestCoinsList() {
        viewModel.requestCoins()
    }

    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
    }
}