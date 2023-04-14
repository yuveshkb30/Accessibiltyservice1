package com.yuvesh.accessibiltyservice1

import android.accessibilityservice.AccessibilityServiceInfo
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.view.accessibility.AccessibilityManager
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.yuvesh.accessibiltyservice1.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var adapter: TextAdapter
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.startServiceButton.setOnClickListener {
            val serviceIntent = Intent(this, MyAccessibilityService::class.java)
            startService(serviceIntent)
        }
        binding.refresh.setOnClickListener {
            adapter.setData(getDataFromSharedPreference())
        }



        enableService()
     /*  val sharedPrefs = getSharedPreferences("packages", Context.MODE_PRIVATE)
        val arrayList : ArrayList<TextMessage> = arrayListOf()
        sharedPrefs.all.forEach {
            arrayList.add(TextMessage(it.key, it.value.toString()))
        }*/
        setupAdapter()
    }

    private fun setupAdapter() {
        adapter = TextAdapter(getDataFromSharedPreference())
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = this@MainActivity.adapter
        }
    }

    fun getDataFromSharedPreference(): ArrayList<TextMessage>{
        val sharedPrefs = getSharedPreferences("packages", Context.MODE_PRIVATE)
        val arrayList : ArrayList<TextMessage> = arrayListOf()
        sharedPrefs.all.forEach {
            arrayList.add(TextMessage(it.key, it.value.toString()))
        }
        return arrayList
    }

    private fun enableService() {
        // Check if the service is enabled
        val accessibilityManager = getSystemService(ACCESSIBILITY_SERVICE) as AccessibilityManager
        val accessibilityServices =
            accessibilityManager.getEnabledAccessibilityServiceList(AccessibilityServiceInfo.FEEDBACK_GENERIC)
        val isServiceEnabled = accessibilityServices.any { serviceInfo ->
            serviceInfo.id == ComponentName(
                this,
                MyAccessibilityService::class.java
            ).flattenToString()
        }

        if (!isServiceEnabled) {
            // Prompt the user to enable the accessibility service
            AlertDialog.Builder(this)
                .setTitle("Enable MyAccessibilityService")
                .setMessage("MyAccessibilityService is not currently enabled. Do you want to enable it?")
                .setPositiveButton("Enable") { _, _ ->
                    // Open the accessibility settings page
                    val intent = Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS)
                    startActivity(intent)
                }
                .setNegativeButton("Cancel", null)
                .show()
        }
    }
}