package ru.aasmc.cryptocurrencies.presentation.coinhistory

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.ValueFormatter
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.flow.collect
import ru.aasmc.cryptocurrencies.R
import ru.aasmc.cryptocurrencies.databinding.FragmentCoinHistoryBinding
import ru.aasmc.cryptocurrencies.domain.model.NetworkUnavailable
import ru.aasmc.cryptocurrencies.presentation.CoinsSharedViewModel
import ru.aasmc.cryptocurrencies.presentation.CoinsSharedViewModelFactory
import ru.aasmc.cryptocurrencies.presentation.SharedViewEffects

class CoinHistoryFragment : Fragment() {
    companion object {
        const val COIN_ID = "COIN_ID"
        const val COIN_NAME = "COIN_NAME"
    }

    private var _binding: FragmentCoinHistoryBinding? = null

    private val binding: FragmentCoinHistoryBinding get() = _binding!!

    private val viewModel: CoinHistoryFragmentViewModel by viewModels { CoinHistoryFragmentViewModelFactory }
    private val sharedViewModel: CoinsSharedViewModel by activityViewModels { CoinsSharedViewModelFactory }

    private val screenTitle: String by lazy {
        val defaultTitle = getString(R.string.app_name)
        arguments?.getString(COIN_NAME, defaultTitle) ?: defaultTitle
    }

    private val coinId: String by lazy {
        requireArguments().getString(COIN_ID).orEmpty()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCoinHistoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupUI()
    }

    private fun setupUI() {
        requestCoinHistory()
        setupToolBar()
        setupChartXAxis()
        setupChartYAxis()
        setupChartBackground()
        observeViewStateUpdates()
        subscribeToViewEffects()
        subscribeToSharedViewEffects()
    }

    private fun requestCoinHistory() {
        viewModel.getCoinHistory(coinId)
    }

    private fun setupToolBar() {
        (requireActivity() as AppCompatActivity).supportActionBar?.apply {
            title = screenTitle
            setDisplayHomeAsUpEnabled(true)
        }
    }

    private fun setupChartXAxis() {
        binding.coinHistoryChart.xAxis.isEnabled = false
    }

    private fun setupChartYAxis() {
        binding.coinHistoryChart.axisLeft.apply {
            axisMinimum = 0f
            setPosition(YAxis.YAxisLabelPosition.INSIDE_CHART)
            setDrawGridLines(false)

            valueFormatter = object : ValueFormatter() {
                override fun getFormattedValue(value: Float): String {
                    return "$value"
                }
            }
        }
        binding.coinHistoryChart.axisRight.isEnabled = false
    }

    private fun setupChartBackground() {
        binding.coinHistoryChart.apply {
            description.isEnabled = false

            setTouchEnabled(false)
            setPinchZoom(false)
            setDrawGridBackground(false)
            invalidate()
        }
    }

    private fun observeViewStateUpdates() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.viewState.collect {
                updateUi(it)
            }
        }
    }

    private fun updateUi(viewState: CoinHistoryFragmentViewState) {
        val (minYAxisValue, history, chartColor) = viewState

        if (history.pricesOverTime.isEmpty()) return

        val dataSet =
            LineDataSet(history.pricesOverTime, "Price variation over the last 24 hours.").apply {
                mode = LineDataSet.Mode.CUBIC_BEZIER
                color = ContextCompat.getColor(requireContext(), R.color.colorPrimary)
                fillDrawable = ContextCompat.getDrawable(requireContext(), chartColor)
                cubicIntensity = 0.2f

                setDrawIcons(false)
                setDrawFilled(true)
                setDrawCircles(false)
            }

        val data = LineData(dataSet)
        binding.coinHistoryChart.apply {
            isVisible = true
            animateX(250)
            axisLeft.axisMinimum = minYAxisValue
            axisLeft.axisMaximum = history.highestValue
            invalidate()
            setData(data)
            notifyDataSetChanged()
        }

        binding.loadingProgressBar.isVisible = false
    }

    private fun subscribeToViewEffects() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.viewEffects.collect {
                when (it) {
                    is CoinHistoryFragmentViewEffects.Failure -> handleFailure(it.cause)
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

    private fun handleFailure(cause: Throwable) {
        binding.loadingProgressBar.isInvisible = true
        val message = when (cause) {
            is NetworkUnavailable -> getString(R.string.network_unavailable_error_message)
            else -> getString(R.string.generic_error_message)
        }

        showSnackBar(message)
    }

    private fun notifyOfPriceVariation(variation: Int) {
        val message = getString(R.string.price_variation_message)
        showSnackBar(message)
    }

    private fun showSnackBar(message: String) {
        Snackbar.make(requireView(), message, Snackbar.LENGTH_SHORT).show()
    }


    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
    }

}



























