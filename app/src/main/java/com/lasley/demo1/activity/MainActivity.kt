package com.lasley.demo1.activity

import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.Button
import com.github.kittinunf.fuel.core.FuelManager
import com.github.kittinunf.fuel.httpGet
import com.lasley.demo1.R
import com.lasley.demo1.adapter.PostAdapter
import com.lasley.demo1.controls.ClearableEditText
import com.lasley.demo1.model.RedditJSON
import com.lasley.demo1.utils.Constants
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    lateinit var _searchControl: ClearableEditText
    lateinit var _searchBtn: Button
    lateinit var _refreshView: SwipeRefreshLayout
    lateinit var _itemsView: RecyclerView

    var rAdapter: PostAdapter = PostAdapter()
    var lastSearch: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        _searchControl = SearchField
        _searchBtn = SearchBtn
        _refreshView = SwipeRefreshView
        _itemsView = ItemsView

        _searchControl.hint = Constants.DEFAULT_REDDIT
        _refreshView.setOnRefreshListener { reloadPosts() }
        _searchBtn.setOnClickListener {
            lastSearch = _searchControl.text.trim().toString()
            if (lastSearch.isEmpty())
                lastSearch = Constants.DEFAULT_REDDIT
            requestRedditPageAPI()
        }

        _itemsView.layoutManager = LinearLayoutManager(this)
        val mDividerItemDecoration = DividerItemDecoration(
                this, DividerItemDecoration.VERTICAL)
        _itemsView.addItemDecoration(mDividerItemDecoration)
        _itemsView.adapter = rAdapter

        lastSearch = Constants.DEFAULT_REDDIT
        requestRedditPageAPI()
    }

    fun reloadPosts() {
        _refreshView.isRefreshing = false
        requestRedditPageAPI()
    }

    fun requestRedditPageAPI() {

        FuelManager.instance.basePath = Constants.BASE_URL
        lastSearch.plus("/.json").httpGet().responseObject(RedditJSON.Deserializer()) { req, res, results ->
            val (data, err) = results

            runOnUiThread {
                if (res.responseMessage == "OK" && err == null) {
                    _searchControl.error = null
                    (_itemsView.adapter as PostAdapter)
                            .UpdateData(data?.data?.children ?: arrayListOf())
                } else
                    _searchControl.error = getString(R.string.invalidReddit)
            }
        }
    }
}