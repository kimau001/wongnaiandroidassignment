package com.example.wongnaiandroidassignment.activities

import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.wongnaiandroidassignment.R
import com.example.wongnaiandroidassignment.adapters.ListViewAdapter
import com.example.wongnaiandroidassignment.models.Coin
import kotlinx.android.synthetic.main.activity_main.*
import com.example.wongnaiandroidassignment.bases.BaseJsonObject
import com.example.wongnaiandroidassignment.https.HttpManager
import com.example.wongnaiandroidassignment.adapters.PaginationScrollListener
import com.example.wongnaiandroidassignment.bases.BaseActivity
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Response


class MainActivity : BaseActivity() {

    override fun getLayoutID(): Int = R.layout.activity_main

    var searchText = ""

    var isSwipe = false
    var isLoadingAPI = false
    var last_page = false
    var skip = 0
    val limit = 10
    var total = 0

    var prefix: String? = null
    var symbols: String? = null
    var slugs: String? = null
    var ids: String? = null

    val coinArray : ArrayList<Coin> = ArrayList()

    override fun onCreate() {

        initView()
        initAdapter()
        getDatas(reset = true, isLoadMoreItem = false)

    }

    private fun initView() {

        edit.setOnEditorActionListener { v, actionId, event ->

            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                searchText = edit.text.toString()
                checkSearchText()
                getDatas(reset = true, isLoadMoreItem = false)
            }

            return@setOnEditorActionListener false
        }

        swipeRefreshLayout.setOnRefreshListener {
            if (!isSwipe) {
                isSwipe = true
                recyclerView.isLayoutFrozen = true
                getDatas(reset = true, isLoadMoreItem = false)

            }
        }

    }


    private fun initAdapter() {

        val adapter = ListViewAdapter(mActivity, coinArray)

        val layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)

        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapter

        recyclerView.addOnScrollListener(object : PaginationScrollListener(layoutManager) {
            override val totalPageCount: Int
                get() = coinArray!!.size
            override val isLastPage: Boolean
                get() = last_page
            override val isLoading: Boolean
                get() = isLoadingAPI

            override fun loadMoreItems() {
                getDatas(reset = false, isLoadMoreItem = true)
            }
        })

    }

    private fun getDatas(reset: Boolean, isLoadMoreItem: Boolean) {

        if (isLoadingAPI) {
            return
        }

        isLoadingAPI = true

        if (reset){

            isLoadingAPI = false
            skip = 0
            last_page = false

        }

        if (!isSwipe) {
            showProgressDialog()
        }

        HttpManager.service.getCoins(limit,skip,prefix,symbols,slugs,ids).enqueue(object : retrofit2.Callback<ResponseBody> {
            override fun onResponse(
                call: Call<ResponseBody>,
                response: Response<ResponseBody>
            ) {

                isSwipe = false
                isLoadingAPI = false
                recyclerView.isLayoutFrozen = false
                swipeRefreshLayout.isRefreshing = false

                if (response.isSuccessful) {

                    dismissProgressDialog()

                    val result = response.body()?.string()
                    val json = BaseJsonObject(result)

                    val data = json.getBaseJSONObject("data")
                    val stats = data.getBaseJSONObject("stats")
                    val base = data.getBaseJSONObject("base")
                    val coins = data.getJSONArray("coins")

                    try {

                        if (reset) {
                            total = 0
                            coinArray!!.clear()
                        }

                        total += stats.getInt("offset",0)

                        for (i in 0 until coins.length()) {

                            var coin : JSONObject?

                            try {
                                coin = coins.getJSONObject(i)
                            }catch (e:java.lang.Exception){
                                coin = null
                            }

                            if (coin != null){
                                coinArray.add(Coin(BaseJsonObject(coin.toString())))
                            }

                        }

                        if (coins.length() == 0) {
                            last_page = true
                        } else if (coins.length() < limit) {
                            last_page = true
                        }

                        skip += coins.length()

                        recyclerView.adapter!!.notifyDataSetChanged()

                        if (reset && coinArray.size > 0) {
                            recyclerView.scrollToPosition(0)
                        }

                        if (coinArray.size > 0){
                            textNotFound.visibility = View.GONE
                        }else{
                            textNotFound.visibility = View.VISIBLE
                        }

                    } catch (e: Exception) {
                        showDialog("error")
                    }


                } else {

                    dismissProgressDialog()

                    try {
                        val httpStatus = response.code()
                        val result = response.errorBody()?.string()
                        val json = BaseJsonObject(result)
                        val error = json.getBaseJSONObject("error")
                        val code = error.getInt("code")

                        if (httpStatus == 400) {

                            val details = error.getString("details")
                            showDialog(details)

                        } else {

                            val message = error.getString("message")
                            showDialog(message)

                        }
                    } catch (e: Exception) {
                    }
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {

                isSwipe = false
                isLoadingAPI = false
                recyclerView.isLayoutFrozen = false
                swipeRefreshLayout.isRefreshing = false

                dismissProgressDialog()
                showDialog(getString(R.string.no_internet))

            }
        })

    }

    private fun checkSearchText() {

        prefix = null
        symbols = null
        slugs = null
        ids = null

        if (searchText != ""){

            val searchList = searchText.split(",")

            if (searchList[0].toIntOrNull() != null){
                ids = searchText
            }else{

                firstloop@ for ((index,text) in searchList.withIndex()){

                    val charArray = text.toCharArray()

                    for ((index,char) in charArray.withIndex()){
                        if (index == 0 && char.isLowerCase()){
                            break@firstloop
                        }else if (char.isLowerCase()){
                            break
                        }
                        if (index == charArray.size-1){
                            if (symbols == null){
                                symbols = ""
                                symbols += text
                            }else{
                                symbols += ",$text"
                            }
                        }
                    }

                }

            }

            if (symbols == null && ids == null){

                if (searchList[0].contains("-")){
                    slugs = searchText
                }else{
                    prefix = searchText
                }

            }

        }

    }

}
