package com.joker.lpgo.ui.main.view

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.joker.lpgo.data.model.User
import com.joker.lpgo.databinding.ActivityMainBinding
import com.joker.lpgo.ui.main.adapter.MainAdapter
import com.joker.lpgo.ui.main.viewmodel.MainViewModel
import com.joker.lpgo.utils.Status
import dagger.hilt.android.AndroidEntryPoint
import androidx.lifecycle.Observer
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var bindingView: ActivityMainBinding

    private val mainViewModel : MainViewModel by viewModels()
    private lateinit var adapter: MainAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingView = ActivityMainBinding.inflate(layoutInflater)
        val view = bindingView.root
        setContentView(view)
        setupUI()
        setupObserver()
    }

    private fun setupUI() {
        bindingView.recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = MainAdapter(arrayListOf())
        bindingView.recyclerView.addItemDecoration(
            DividerItemDecoration(
                bindingView.recyclerView.context,
                (bindingView.recyclerView.layoutManager as LinearLayoutManager).orientation
            )
        )
        bindingView.recyclerView.adapter = adapter
    }

    private fun setupObserver() {
        mainViewModel.users.observe(this, Observer {
            when (it.status) {
                Status.SUCCESS -> {
                    bindingView.progressBar.visibility = View.GONE
                    it.data?.let { users -> renderList(users) }
                    bindingView.recyclerView.visibility = View.VISIBLE
                }
                Status.LOADING -> {
                    bindingView.progressBar.visibility = View.VISIBLE
                    bindingView.recyclerView.visibility = View.GONE
                }
                Status.ERROR -> {
                    bindingView.progressBar.visibility = View.GONE
                    Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                }
            }
        })
    }

    private fun renderList(users: List<User>) {
        adapter.addData(users)
        adapter.notifyDataSetChanged()
    }

}
