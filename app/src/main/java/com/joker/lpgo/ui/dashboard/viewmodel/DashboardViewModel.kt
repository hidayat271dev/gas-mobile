package com.joker.lpgo.ui.dashboard.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.joker.lpgo.data.model.Product
import com.joker.lpgo.data.model.User
import com.joker.lpgo.ui.dashboard.repository.DashboardRepository
import com.joker.lpgo.ui.main.repository.MainRepository
import com.joker.lpgo.utils.NetworkHelper
import com.joker.lpgo.utils.Resource
import kotlinx.coroutines.launch

class DashboardViewModel @ViewModelInject constructor(
    private val repository: DashboardRepository,
    private val networkHelper: NetworkHelper
) : ViewModel() {

    private val _products = MutableLiveData<Resource<List<Product>>>()
    val products: LiveData<Resource<List<Product>>>
        get() = _products

    init {
        fetchProducts()
    }

    private fun fetchProducts() {
        viewModelScope.launch {
            _products.postValue(Resource.loading(null))
            if (networkHelper.isNetworkConnected()) {
                repository.getProducts().let {
                    if (it.isSuccessful) {
                        _products.postValue(Resource.success(it.body()?.data))
                    } else _products.postValue(Resource.error(it.errorBody().toString(), null))
                }
            } else _products.postValue(Resource.error("No internet connection", null))
        }
    }

}