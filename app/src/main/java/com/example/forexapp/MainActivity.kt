package com.example.forexapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.forexapp.databinding.ActivityMainBinding
import com.google.gson.Gson
import io.finnhub.api.infrastructure.ApiClient
import java.io.InputStreamReader
import java.net.URL
import javax.net.ssl.HttpsURLConnection


class MainActivity : AppCompatActivity()
{
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        fetchCureencyData().start()


    }


    private fun fetchCureencyData(): Thread
    {
        return Thread{
//            ApiClient.apiKey["token"] = "d860638462msh92d2233752140aap1d2636jsnbb6259b9622c"
            var url = URL("https://open.er-api.com/v6/latest/usd")
            val connection = url.openConnection() as HttpsURLConnection


            if(connection.responseCode == 200)
            {
                val inputSystem = connection.inputStream
                val inputStreamReader = InputStreamReader(inputSystem, "UTF-8" )
                val request = Gson().fromJson(inputStreamReader, Request::class.java)
                updateUI(request)
                inputStreamReader.close()
                inputSystem.close()

            }
            else {
                binding.failed.text = "Failed"
            }
        }
    }

    private fun updateUI(request: Request) {
        runOnUiThread {
            kotlin.run {
                binding.failed.text = request.time_last_update_utc
                binding.jpy.text = String.format("USD/JPY                %.3f", request.rates.JPY)
                binding.eur.text = String.format("USD/EUR                    %.3f", request.rates.EUR)
                binding.chf.text = String.format("USD/CHF                    %.3f", request.rates.CHF)
                binding.cad.text = String.format("USD/CAD                    %.3f", request.rates.CAD)
                binding.gbp.text = String.format("USD/GBP                    %.3f", request.rates.GBP)
            }
        }
    }
}
