package com.example.googlemap_printful

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import java.net.Socket
import java.util.*

class MainActivity : AppCompatActivity() {

    private var active = false
    private var data = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val address = "ios-test.printful.lv:6111"
        val port = 6111
        val button = findViewById<Button>(R.id.button)
        button.setOnClickListener {
            CoroutineScope(IO).launch{
                Log.i("TAG", "client: CLICKED")
                client(address,port)
            }

        }
    }

    private suspend fun client(address: String){
        val connection = Socket(address)

        val reader = Scanner(connection.getInputStream())
        while(active){
            var input = ""
            input = reader.nextLine()
            if(data.length < 300){
                data = "\n$input"
            }
            else{
                data = input
                Log.i("TAG", "client: $data")
            }
        }
        reader.close()
        connection.close()
    }
}