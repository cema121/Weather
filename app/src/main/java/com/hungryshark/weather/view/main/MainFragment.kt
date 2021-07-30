package com.hungryshark.weather.view.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import com.hungryshark.weather.R
import com.hungryshark.weather.databinding.FragmentMainBinding
import com.hungryshark.weather.model.Weather
import com.hungryshark.weather.view.details.DetailsFragment
import com.hungryshark.weather.viewModel.AppState
import com.hungryshark.weather.viewModel.MainViewModel

class MainFragment : Fragment(){
    private var _binding: FragmentMainBinding?=null
    private val binding get()=_binding!!

    private lateinit var viewModel: MainViewModel

    //флаг: следит за "подгрузкой" данных
    private var isDataSetRus:Boolean=true

    //создаем интерфейс(через object) и передаем его в адаптер
    private val adapter=MainFragmentAdapter(object:OnItemViewClickListener{
        override fun onItemViewClick(weather: Weather){
            //к менеджеру фрагментов обращаемся через activity
            val manager=activity?.supportFragmentManager
            if(manager!=null){
                //создаем Bundle
                val bundle= Bundle()
                bundle.putParcelable(DetailsFragment.BUNDLE_EXTRA,weather)
                manager.beginTransaction()
                    .add(R.id.container,DetailsFragment.newInstance(bundle))
                    .addToBackStack("")
                    .commitAllowingStateLoss()
            }
        }
    })

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState:Bundle?
    ): View {
        _binding=FragmentMainBinding.inflate(inflater,container,false)
        return binding.getRoot()
    }

    override fun onViewCreated(view:View,savedInstanceState:Bundle?){
        super.onViewCreated(view,savedInstanceState)
        binding.mainFragmentRecyclerView.adapter=adapter
        binding.mainFragmentFAB.setOnClickListener{changeWeatherDataSet()}
        viewModel= ViewModelProvider(this).get(MainViewModel::class.java)
        viewModel.requestLiveData().observe(viewLifecycleOwner){renderData(it as AppState)}
        viewModel.requestWeatherFromLocalSourceRus()
    }

    override fun onDestroy(){
        adapter.removeListener()
        super.onDestroy()
    }

    private fun changeWeatherDataSet(){
        if(isDataSetRus){
            viewModel.requestWeatherFromLocalSourceWorld()
            binding.mainFragmentFAB.setImageResource(R.drawable.ic_world_wide)
        }else{
            viewModel.requestWeatherFromLocalSourceRus()
            binding.mainFragmentFAB.setImageResource(R.drawable.ic_no_world_wide)
        }
        isDataSetRus=!isDataSetRus
    }

    private fun renderData(appState:AppState){
        when(appState){
            is AppState.Success->{
                binding.mainFragmentLoadingLayout.visibility=View.GONE
                adapter.setWeather(appState.weatherData)
            }
            is AppState.Loading->{
                binding.mainFragmentLoadingLayout.visibility=View.VISIBLE
            }
            is AppState.Error->{
                binding.mainFragmentLoadingLayout.visibility=View.GONE
                Snackbar
                    .make(
                        binding.mainFragmentFAB,getString(R.string.error),
                        Snackbar.LENGTH_INDEFINITE
                    )
                    .setAction(getString(R.string.reload)){
                        viewModel.requestWeatherFromLocalSourceRus()
                    }
                    .show()
            }
        }
    }

    interface OnItemViewClickListener {
        fun onItemViewClick(weather:Weather)
    }

    companion object {
        fun newInstance()=MainFragment()
    }
}