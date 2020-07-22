package com.example.coroutinesample.basic

import kotlinx.coroutines.launch
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.runBlocking

fun main() = runBlocking {
    /* Default dispatcher */
    val task = launch {
        printCurrentThread()
    }
    task.join()

    /* custom dispatcher */
    val dispatcher = newSingleThreadContext(name = "ServiceCall")
    val task2 = launch(dispatcher) {
        printCurrentThread()
    }
    task2.join()
}

fun printCurrentThread() {
    println("Running in thread [${Thread.currentThread().name}]")
}