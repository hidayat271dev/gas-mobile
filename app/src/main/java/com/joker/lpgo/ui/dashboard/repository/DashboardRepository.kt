package com.joker.lpgo.ui.dashboard.repository

import com.joker.lpgo.data.api.ApiHelper
import javax.inject.Inject

class DashboardRepository @Inject constructor(private val apiHelper: ApiHelper) {

    suspend fun getProducts() = apiHelper.getProducts()

}