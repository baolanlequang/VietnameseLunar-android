package com.baolan2005.vietnameselunar

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import java.util.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val date = Date()
        val vietnameseCalendar = VietnameseCalendar(date)
        Log.d("vietnameselunar", vietnameseCalendar.vietnameseDate.toString())

    }
}