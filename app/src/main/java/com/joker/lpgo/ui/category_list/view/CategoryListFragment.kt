package com.joker.lpgo.ui.category_list.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.joker.lpgo.data.mock.ProductRecommendedListMock
import com.joker.lpgo.databinding.ScreenCategoryBinding
import com.joker.lpgo.ui.category_list.adapter.CategoryAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CategoryListFragment : Fragment() {

    private var bindingView: ScreenCategoryBinding? = null
    private lateinit var adapterCategory: CategoryAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        bindingView = ScreenCategoryBinding.inflate(inflater, container, false)
        val view = bindingView?.root
        if (view != null) {
            setupView()
        }
        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        bindingView = null
    }

    fun setupView() {
        adapterCategory = CategoryAdapter(arrayListOf())

        context?.let {
            bindingView?.recyclerView3?.layoutManager = GridLayoutManager(it, 4)
            bindingView?.recyclerView3?.adapter = adapterCategory
        }

        adapterCategory.addData(ProductRecommendedListMock().data)
    }

}
