package com.example.androidfinalproject

import android.app.Application

class MyApplication :Application(){
    val applicationComponent= DaggerApplicationComponent.create()
}