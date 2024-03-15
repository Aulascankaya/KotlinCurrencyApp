package com.ulas.kotlincurrencyapp

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ulas.kotlincurrencyapp.adapter.RecyclerViewAdapter
import com.ulas.kotlincurrencyapp.databinding.ActivityMainBinding
import com.ulas.kotlincurrencyapp.model.CryptoModel
import com.ulas.kotlincurrencyapp.service.CryptoAPI
import io.reactivex.rxjava3.disposables.CompositeDisposable
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class MainActivity : AppCompatActivity(), RecyclerViewAdapter.Listener {
    private lateinit var binding : ActivityMainBinding
    private val BASE_URL = "https://api.freecurrencyapi.com/v1/"
    private var currencyModels: List<CryptoModel>? = null
    private var adapter: RecyclerViewAdapter? = null
    private var compositeDisposable: CompositeDisposable? = null

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

        val service = retrofit.create(CryptoAPI::class.java)
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
    }

    override fun onItemClick(currencyModel: CryptoModel) {
        // Burada item tıklandığında yapılacak işlemleri gerçekleştirin
        Toast.makeText(this, "Clicked: ${currencyModel.data.entries.first().key}", Toast.LENGTH_SHORT).show()
    }
}
