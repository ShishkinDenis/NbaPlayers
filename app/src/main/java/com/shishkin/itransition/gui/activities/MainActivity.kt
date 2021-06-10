package com.shishkin.itransition.gui.activities

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.shishkin.itransition.R
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map


class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        TODO DELETE
        val job1 : Job =   GlobalScope.launch {
            testJob1()
        }
        val job2 : Job =   GlobalScope.launch(Dispatchers.Main) {
            job1.join()
            testJob2()

        val result = withContext(Dispatchers.IO){
                testJob3()
            }
            Toast.makeText(this@MainActivity, result,Toast.LENGTH_LONG).show()
        }

        val helloFlow  = flow {
            for (i in 1..5) {
                delay(500)
                emit("Hello flow $i")
            }
        }

        GlobalScope.launch {
            helloFlow
                .map {item-> "$item map" }
                .collect{
                Log.d("Flow", it)
            }
        }


    }

    private suspend fun testJob1(){
        for(i in 1..5) {
            delay(1000)
            Log.d("Coroutines", "$i job1")
        }
    }
    private suspend fun testJob2(){
        for(i in 1..5) {
            delay(1000)
            Log.d("Coroutines", "$i job2")
        }
    }
    private suspend fun testJob3() : String{
        for(i in 1..10) {
            delay(500)
            Log.d("Coroutines", "$i job3")
        }
        return "testJob3";
    }
}