package com.example.coroutinesample.basic

import kotlinx.coroutines.*

fun main() = runBlocking {
    val dispatcher = newSingleThreadContext("myDispatcher")
    val handler = CoroutineExceptionHandler { _, throwable ->
        println("Error captured")
        println("Message: ${throwable.message}")
    }

    // 코루틴 context 결합 ( dispatcher + handler )
    val context = dispatcher + handler
//    GlobalScope.launch(context) {
//        println("Running in ${Thread.currentThread().name}")
//        TODO("Not implemented!")
//    }.join()

    // context 분리
    val tmpCtx = context.minusKey(dispatcher.key)

    GlobalScope.launch(tmpCtx) {
        println("Running in ${Thread.currentThread().name}")
        TODO("Not implemented")
    }.join()

    // withContext ( async, await 을 withContext로 바꿀 수 있다 )
    val withContextDispatcher = newSingleThreadContext("myThread")
    val name = GlobalScope.async(withContextDispatcher) {
        "Susan Calvin"
    }.await()
    println("User: $name")

    val name2 = withContext(withContextDispatcher) {
        "Susan Calvin"
    }
    println("User: $name")
}