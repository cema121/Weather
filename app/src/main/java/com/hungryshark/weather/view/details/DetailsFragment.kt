package com.hungryshark.weather.view.details

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import coil.api.load
import com.github.twocoffeesoneteam.glidetovectoryou.GlideToVectorYou
import com.hungryshark.weather.R
import com.hungryshark.weather.databinding.FragmentDetailsBinding
import com.hungryshark.weather.model.Weather
import com.hungryshark.weather.utils.CircleTransformation
import com.hungryshark.weather.utils.showSnackBar
import com.hungryshark.weather.viewModel.AppState
import com.hungryshark.weather.viewModel.DetailsViewModel
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_details.*

class DetailsFragment : Fragment() {
    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!
    private lateinit var weatherBundle: Weather

    private val viewModel: DetailsViewModel by lazy {
        ViewModelProvider(this).get(DetailsViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        weatherBundle = arguments?.getParcelable(BUNDLE_EXTRA) ?: Weather()
        viewModel.detailsLiveData.observe(viewLifecycleOwner) { renderData(it) }
        viewModel.getWeatherFromRemoteSource(weatherBundle.city.lat, weatherBundle.city.lon)
    }

    private fun renderData(appState: AppState) {
        when (appState) {
            is AppState.Success -> {
                binding.viewDetailsFragment.visibility = View.VISIBLE
                binding.loadingLayout.visibility = View.GONE
                setWeather(appState.weatherData[0])
            }
            is AppState.Loading -> {
                binding.viewDetailsFragment.visibility = View.GONE
                binding.loadingLayout.visibility = View.VISIBLE
            }
            is AppState.Error -> {
                binding.viewDetailsFragment.visibility = View.VISIBLE
                binding.loadingLayout.visibility = View.GONE
                binding.viewDetailsFragment.showSnackBar(
                    getString(R.string.error),
                    getString(R.string.reload),
                    {
                        viewModel.getWeatherFromRemoteSource(
                            weatherBundle.city.lat,
                            weatherBundle.city.lon
                        )
                    })
            }
        }
    }

    private fun setWeather(weather: Weather) {
        val city = weatherBundle.city
        binding.cityName.text = city.city
        binding.cityCoordinates.text = String.format(
            getString(R.string.city_coordinates),
            city.lat.toString(),
            city.lon.toString()
        )
        binding.temperatureValue.text = weather.temperature.toString()
        binding.feelsLikeValue.text = weather.feelsLike.toString()
        binding.weatherCondition.text = weather.condition
        /*Picasso
            .get()
            .load("https://c1.staticflickr.com/1/186/31520440226_175445c41a_b.jpg")
            .transform(CircleTransformation())
            .into(binding.headerIcon)
         */
        headerIcon.load(R.drawable.city)

        weather.icon?.let {
            GlideToVectorYou.justLoadImage(
                activity,
                Uri.parse("https://yastatic.net/weather/i/icons/blueye/color/svg/${it}.svg"),
                binding.weatherIcon
            )
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val BUNDLE_EXTRA = "weather"
        fun newInstance(bundle: Bundle): DetailsFragment {
            val fragment = DetailsFragment()
            fragment.arguments = bundle
            return fragment
        }
    }
}