package com.example.coroutinesample.basic

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main() {
    GlobalScope.launch {
        delay(1000)
        println(1)
    }

    println(2)

    runBlocking {
        delay(3000)
        println(3)
    }

    println(4)
}