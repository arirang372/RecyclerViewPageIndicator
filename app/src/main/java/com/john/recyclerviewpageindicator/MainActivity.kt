package com.john.recyclerviewpageindicator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.john.recyclerviewpageindicator.data.api.ApiHelperImpl
import com.john.recyclerviewpageindicator.data.api.RetrofitBuilder
import com.john.recyclerviewpageindicator.data.model.ApiUser
import com.john.recyclerviewpageindicator.ui.CirclePagerIndicatorDecoration

class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: MainViewModel
    private lateinit var adapter: ApiUserAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recycler_view)
        recyclerView = findViewById(R.id.recyclerView)
        progressBar = findViewById(R.id.progressBar)
        viewModel = MainViewModel(
            apiHelper = ApiHelperImpl(RetrofitBuilder.apiService)
        )
        setupUI()
        setupObserver()
    }

    private fun setupUI() {
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = ApiUserAdapter(
            arrayListOf()
        )

        recyclerView.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        recyclerView.adapter = adapter

        /**
         *  Try to use the page indicator with the items of RecyclerView.
         *
         */

        val snapHelper = PagerSnapHelper()
        snapHelper.attachToRecyclerView(recyclerView)
        recyclerView.addItemDecoration(CirclePagerIndicatorDecoration())

    }

    private fun setupObserver() {
        viewModel.getUsers().observe(this) {
            when (it.status) {
                Status.SUCCESS -> {
                    progressBar.visibility = View.GONE
                    it.data?.let { users -> renderList(users) }
                    recyclerView.visibility = View.VISIBLE
                }

                Status.LOADING -> {
                    progressBar.visibility = View.VISIBLE
                    recyclerView.visibility = View.GONE
                }

                Status.ERROR -> {
                    //Handle Error
                    progressBar.visibility = View.GONE
                    Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                }
            }
        }
    }

//    private fun setupViewModel() {
//        viewModel = ViewModelProviders.of(
//            this,
//            ViewModelFactory(
//                ApiHelperImpl(RetrofitBuilder.apiService)
//            )
//        ).get(MainViewModel::class.java)
//    }

    private fun renderList(users: List<ApiUser>) {
        adapter.addData(users)
        adapter.notifyDataSetChanged()
    }
}
