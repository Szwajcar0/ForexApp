package com.example.forexapp

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.ui.AppBarConfiguration
import com.example.forexapp.databinding.ActivityTradingBinding
import com.google.gson.Gson
import org.w3c.dom.Text
import java.io.InputStreamReader
import java.net.URL
import javax.net.ssl.HttpsURLConnection

class TradingActivity : AppCompatActivity() {

    private lateinit var back1: Button
    private lateinit var Cname: TextView
    private lateinit var Cvalue: TextView
    var num: Int = 1
    private lateinit var binding: ActivityTradingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTradingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        fetchCureencyData().start()

        back1 = findViewById(R.id.back)
        Cname = findViewById(R.id.Cname)
        Cvalue = findViewById(R.id.Cvalue)

        back1.setOnClickListener {
            val intent = Intent(applicationContext, MainActivity::class.java)
            startActivity(intent)
        }
    }

    private fun fetchCureencyData(): Thread {
        return Thread {
//            ApiClient.apiKey["token"] = "d860638462msh92d2233752140aap1d2636jsnbb6259b9622c"
            var url = URL("https://open.er-api.com/v6/latest/usd")
            val connection = url.openConnection() as HttpsURLConnection


            if (connection.responseCode == 200) {
                val inputSystem = connection.inputStream
                val inputStreamReader = InputStreamReader(inputSystem, "UTF-8")
                val request = Gson().fromJson(inputStreamReader, Request::class.java)
                updateUI(request)
                inputStreamReader.close()
                inputSystem.close()

            } else {
            }
        }
    }

    private fun updateUI(request: Request) {
        runOnUiThread {
            kotlin.run {
                binding.Cname.text = String.format("USD/JPY")
                binding.Cvalue.text = String.format("%.3f", request.rates.JPY)
                binding.arrowR.setOnClickListener {
                    num += 1
                    binding.Cname.animate().apply {
                        translationX(-1000f).setDuration(1000)
                        alpha(0f).setDuration(1000)
                    }
                    binding.Cvalue.animate().apply {
                        translationX(-1000f).setDuration(1000)
                        alpha(0f).setDuration(1000)
                    }.withEndAction {
                        if (num == 1){
                            binding.Cname.text = String.format("USD/JPY")
                            binding.Cvalue.text = String.format("%.3f", request.rates.JPY)
                        }
                        else if(num == 2){
                            binding.Cname.text = String.format("USD/EUR")
                            binding.Cvalue.text = String.format("%.3f", request.rates.EUR)
                        }
                        else if(num == 3){
                            binding.Cname.text = String.format("USD/CHF")
                            binding.Cvalue.text = String.format("%.3f", request.rates.CHF)
                        }
                        else if(num == 4){
                            binding.Cname.text = String.format("USD/CAD")
                            binding.Cvalue.text = String.format("%.3f", request.rates.CAD)
                        }
                        else if(num == 5){
                            binding.Cname.text = String.format("USD/GBP")
                            binding.Cvalue.text = String.format("%.3f", request.rates.GBP)
                        }
                        else {
                            num == 0
                        }
                        binding.Cname.animate().apply {
                            translationX(0f).setDuration(1)
                    }.withEndAction {
                        binding.Cname.animate().alpha(1f).setDuration(1000)
                        }
                        binding.Cvalue.animate().apply {
                            translationX(0f).setDuration(1)
                        }.withEndAction {
                            binding.Cvalue.animate().alpha(1f).setDuration(1000)
                        }

                }
            }
                binding.arrowL.setOnClickListener {
                    num -= 1
                    binding.Cname.animate().apply {
                        translationX(1000f).setDuration(1000)
                        alpha(0f).setDuration(1000)
                    }
                    binding.Cvalue.animate().apply {
                        translationX(1000f).setDuration(1000)
                        alpha(0f).setDuration(1000)
                    }.withEndAction {
                        if (num == 1){
                            binding.Cname.text = String.format("USD/JPY")
                            binding.Cvalue.text = String.format("%.3f", request.rates.JPY)
                        }
                        else if(num == 2){
                            binding.Cname.text = String.format("USD/EUR")
                            binding.Cvalue.text = String.format("%.3f", request.rates.EUR)
                        }
                        else if(num == 3){
                            binding.Cname.text = String.format("USD/CHF")
                            binding.Cvalue.text = String.format("%.3f", request.rates.CHF)
                        }
                        else if(num == 4){
                            binding.Cname.text = String.format("USD/CAD")
                            binding.Cvalue.text = String.format("%.3f", request.rates.CAD)
                        }
                        else if(num == 5){
                            binding.Cname.text = String.format("USD/GBP")
                            binding.Cvalue.text = String.format("%.3f", request.rates.GBP)
                        }
                        else {
                            num == 0
                        }
                        binding.Cname.animate().apply {
                            translationX(0f).setDuration(1)
                        }.withEndAction {
                            binding.Cname.animate().alpha(1f).setDuration(1000)
                        }
                        binding.Cvalue.animate().apply {
                            translationX(0f).setDuration(1)
                        }.withEndAction {
                            binding.Cvalue.animate().alpha(1f).setDuration(1000)
                        }

                    }
                }
                binding.sell.setOnClickListener {
                    binding.order.animate().alpha(1f).setDuration(1000)
                    val order: TextView = findViewById(R.id.order)
                    val orderText = "SELL: " + Cname.text.toString() + "  " + Cvalue.text.toString() + "  " + Cvalue.text.toString() + "  " + binding.wolumen.text.toString()
                    order.text = orderText
                }
                binding.buy.setOnClickListener {
                    binding.order.animate().alpha(1f).setDuration(1000)
                    val order: TextView = findViewById(R.id.order)
                    val orderText = "BUY: " + Cname.text.toString() + "  " + Cvalue.text.toString() + "  " + Cvalue.text.toString() + "  " + binding.wolumen.text.toString()
                    order.text = orderText
                }
        }


    }
}
}
