package com.tarush27.marvelcharacters

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.tarush27.marvelAdapter.MarvelComicAdapter
import com.tarush27.marvelComicModel.MarvelComicCharacters
import com.tarush27.networking.MarvelCharacterResponse
import com.tarush27.networking.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MarvelMainActivity : AppCompatActivity() {
    private lateinit var characters: List<MarvelComicCharacters>
    private val recyclerView: RecyclerView
        get() = findViewById(R.id.marvelComicRv)
    private val swipeRefreshLayout: SwipeRefreshLayout
        get() = findViewById(R.id.refreshComicRv)
    private lateinit var marvelAdapter: MarvelComicAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.marvel_main_activity)
        marvelRecyclerView()
        loadData()
        swipeRefreshLayout.setOnRefreshListener {
            swipeRefreshLayout.isRefreshing = true
            getMarvelCharacters()
        }

    }

    private fun marvelRecyclerView() {
        recyclerView.layoutManager = LinearLayoutManager(this)
        marvelAdapter = MarvelComicAdapter()
        recyclerView.adapter = marvelAdapter
    }

    private fun saveData() {
        val sharedPreferences = getSharedPreferences("Marvel Collection", MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        val gson = Gson()
        val json = gson.toJson(characters)
        Log.d("json", json)
        editor.putString("Characters", json)
        editor.apply()
    }

    override fun onStop() {
        saveData()
        super.onStop()
        Log.d("onStop", "hi")
    }

    private fun loadData() {
        val sharedPreferences = getSharedPreferences("Marvel Collection", MODE_PRIVATE)
        val json = sharedPreferences.getString("Characters", "")
        val type = object : TypeToken<List<MarvelComicCharacters>>() {}.type
        characters = Gson().fromJson(json, type)
        Log.d("getCharacters", characters.toString())
        Log.d("MarvelActivity", characters.size.toString())
        marvelAdapter.updateCharacters(characters as ArrayList)
    }


    private fun getMarvelCharacters() {
        val apiService = RetrofitClient.marvelsApiService
        val returnCharacters: Call<MarvelCharacterResponse> = apiService.getCharacters()
        returnCharacters.enqueue(object : Callback<MarvelCharacterResponse> {
            override fun onResponse(
                call: Call<MarvelCharacterResponse>,
                response: Response<MarvelCharacterResponse>
            ) {
                Log.d("response", response.body().toString())
                swipeRefreshLayout.isRefreshing = false
                if (response.isSuccessful) {
                    val responseFile: MarvelCharacterResponse? = response.body()
                    characters =
                        responseFile?.marvelCollection?.results?.map {
                            it.toMarvelCharacter()
                        }!!
                    Log.d("characters", characters.toString())
                    marvelAdapter.updateCharacters(characters as ArrayList)

                } else {
                    loadData()
                }

            }

            override fun onFailure(call: Call<MarvelCharacterResponse>, t: Throwable) {
                swipeRefreshLayout.isRefreshing = false
                loadData()
            }

        })
    }
}