package com.ulas.kotlincurrencyapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ulas.kotlincurrencyapp.adapter.RecyclerViewAdapter
import com.ulas.kotlincurrencyapp.databinding.ActivityMainBinding
import com.ulas.kotlincurrencyapp.model.CryptoModel
import com.ulas.kotlincurrencyapp.service.CryptoAPI
import io.reactivex.rxjava3.disposables.CompositeDisposable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class MainActivity : AppCompatActivity(), RecyclerViewAdapter.Listener {
    private lateinit var binding: ActivityMainBinding
    private val BASE_URL = "https://api.freecurrencyapi.com/v1/"
    private var currencyModels: List<CryptoModel>? = null
    private var adapter: RecyclerViewAdapter? = null
    private var compositeDisposable: CompositeDisposable? = null
    private var job: Job? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        compositeDisposable = CompositeDisposable()

        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = layoutManager

        loadData()
    }

    private fun loadData() {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        job = CoroutineScope(Dispatchers.IO).launch {
            val service = retrofit.create(CryptoAPI::class.java)
            val response = service.getData()

            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    response.body()?.let { cryptoModel ->
                        currencyModels = cryptoModel.data.entries.map { entry ->
                            CryptoModel(mapOf(entry.key to entry.value))
                        }
                        currencyModels?.let {
                            adapter = RecyclerViewAdapter(it, this@MainActivity)
                            binding.recyclerView.adapter = adapter
                        }

                    }
                }

                /* val service = retrofit.create(CryptoAPI::class.java)
        val call = service.getData()

        call.enqueue(object: Callback<CryptoModel> {
            override fun onFailure(call: Call<CryptoModel>, t: Throwable) {
                t.printStackTrace()
            }

            override fun onResponse(call: Call<CryptoModel>, response: Response<CryptoModel>) {
                if (response.isSuccessful) {
                    response.body()?.let { cryptoModel ->
                        currencyModels = cryptoModel.data.entries.map { entry ->
                            CryptoModel(mapOf(entry.key to entry.value))
                        }

                        currencyModels?.let {
                            adapter = RecyclerViewAdapter(it, this@MainActivity)
                            binding.recyclerView.adapter = adapter
                        }
                    }
                }
            }
        })
        */
            }
        }

    }

    override fun onItemClick(currencyModel: CryptoModel) {
        TODO("Not yet implemented")
    }
}

