package com.example.coroutinesample.basic

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking

/*
* Deferred
* 결과를 갖는다
* 다른 언어에서는 Futures, Promises 등등이 있다
* */
fun main() = runBlocking<Unit> {
    val deferred = GlobalScope.async {
    }

    // Wait for it to fail
    try {
        deferred.await()
    } catch (throwable: Throwable) {
        println("Deferred cancelled due to ${throwable.message}")
    }
}
