package com.example.myapplication

import android.graphics.Color
import kotlin.random.Random.Default.nextInt

fun randomColor() = Color.rgb(nextInt(256), nextInt(256), nextInt(256))
