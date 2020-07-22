package com.example.coroutinesample.basic

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking

@InternalCoroutinesApi
fun main() = runBlocking {
    val task = GlobalScope.async {
        exceptionFunction()
    }
    /* join 은 예외를 전파하지 않고 처리 */
    task.join()
    if (task.isCancelled) {
        val exception = task.getCancellationException()
        println("Error with message : ${exception.cause}")
    } else {
        println("Success")
    }
    /*   */

    /* await 은 예외를 감싸지 않고 전파하는 unwrapping deferred */
    task.await()
    println("Completed")
    /*   */
}

fun exceptionFunction() {
    throw UnsupportedOperationException("Can't do")
}