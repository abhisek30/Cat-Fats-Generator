package com.example.catfacts

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.example.catfacts.Constants.Companion.BASE_URL
import com.example.catfacts.databinding.ActivityMainBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.awaitResponse
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.Exception

class MainActivity : AppCompatActivity() {
    private var TAG = "MainActivity"
    //viewBinding
    private lateinit var binding : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //inflate the activityMain layout by ViewBinding
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        getCurrentData()

        binding.generateNewFact.setOnClickListener {
            getCurrentData()
        }
    }
    private fun getCurrentData() {

        binding.factTextView.visibility = View.INVISIBLE
        binding.timeStampTextView.visibility = View.INVISIBLE
        binding.progressBar.visibility = View.VISIBLE

        val api = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiRequest::class.java)

        GlobalScope.launch(Dispatchers.IO) {
            try {
                val response = api.getCatFacts().awaitResponse()
                if(response.isSuccessful){
                    val data = response.body()!!
                    Log.d(TAG,data.text)

                    withContext(Dispatchers.Main){
                        binding.factTextView.visibility = View.VISIBLE
                        binding.timeStampTextView.visibility = View.VISIBLE
                        binding.progressBar.visibility = View.GONE

                        binding.factTextView.text = data.text
                        binding.timeStampTextView.text = data.createdAt
                    }
                }
            } catch (e:Exception){
                withContext(Dispatchers.Main){
                    Toast.makeText(applicationContext,"No Internet Connection, Please Check your Connection.",Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}