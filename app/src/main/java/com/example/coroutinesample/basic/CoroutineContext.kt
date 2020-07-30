package com.example.coroutinesample.basic

import kotlinx.coroutines.*
import kotlin.system.measureTimeMillis

/* Coroutine Context */
/*
    코루틴은 항상 컨텍스트 안에서 시작된다. 컨텍스트는 코루틴이 어떻게 실행되고 동작해야 하는지를 정의할 수 있게 해주는 요소들의 그룹.
    다양한 컨텍스트 요소들의 집합. 주된 요소로는 Job, Dispatcher 등이 있다.
    Dispatcher 는 코루틴이 실행될 스레드를 결정한다. 시작될 곳과 중단 후 재개될 곳 모두 포함.
    Common Pool은 Cpu 바운드 작업을 위해 프레임워크에 의해 자동으로 생성되는 스레드 풀.
    스레드 풀의 최대 크기는 시스템의 코어 수에서 - 1

    Unconfined - 첫 번째 중단 지점에 도달할 때까지 현재 스레드에 있는 코루틴 실행. 코루틴은 일시 중지된 후에, 일시 중단 연산에서 사용된
    기존 스레드에서 다시 시작된다

    single thread context ( 단일 스레드 컨텍스트 ) - 항상 코루틴이 특정 스레드 안에서 실행된다는 것 보장

    Thread pool - 스레드 풀을 가지며, 해당 풀에서 가용한 스레드에서 코루틴 시작, 재개. 런타임에 스레드를 정하고 부하 분산을 위한 방법도 정해서 따로 할 것은 없다

    예외 처리 - coroutine context 의 다른 중요한 것은 예측이 어려운 예외에 대한 동작을 정의하는 것이다. CoroutineExceptionHandler 구현

    Non-cancellable -
 */
fun main(args: Array<String>)= runBlocking {
    // 기본 디스패처인 Dispatchers.Default 사용
    //GlobalScope.launch(Dispatchers.Default) { }

    // Unconfined
    GlobalScope.launch(Dispatchers.Unconfined) {
        println("Starting in ${Thread.currentThread().name}")
        delay(1000)
        println("Resuming in ${Thread.currentThread().name}")
    }.join()

    delay(500)

    // single thread context
    val dispatcher = newSingleThreadContext("myThread")
    GlobalScope.launch(dispatcher) {
        println("Starting in ${Thread.currentThread().name}")
        delay(1000)
        println("Resuming in ${Thread.currentThread().name}")
    }.join()

    delay(500)

    // Thread Pool
    val threadPoolDispatcher = newFixedThreadPoolContext(4, "myPool")
    GlobalScope.launch(threadPoolDispatcher) {
        println("Starting in ${Thread.currentThread().name}")
        delay(500)
        println("Resuming in ${Thread.currentThread().name}")
    }.join()

    // 예외 처리
    val handler = CoroutineExceptionHandler { context, throwable ->
        println("Error captured in $context")
        println("Message: ${throwable.message}")
    }
    GlobalScope.launch(handler) {
        TODO("Not implemented yet!")
    }

    // wait for the error to happen
    delay(500)

    // Non-cancellable
    val duration = measureTimeMillis {
        val job = launch {
            try {
                while (isActive) {
                    delay(500)
                    println("still running")
                }
            } finally {
//                println("cancelled, will end now")
//                // 아래의 delay 와 println 은 실행되지 않는다
//                // 취소 중인 코루틴은 일시 중단될 수 없도록 설계되었기 때문이다
//                // 코루틴이 취소되는 동안 일시 중지가 필요한 경우 NonCancellable context를 사용해야 한다
//                delay(5000)
//                println("delay completed, bye bye")

                // withContext 는 코루틴의 취소여부와 관계없이 전달 된 일시 중단 람다가 일시 중단될 수 있도록 보장
                withContext(NonCancellable) {
                    println("cancelled, will end now")
                    delay(5000)
                    println("delay completed, bye bye")
                }
            }
        }

        delay(1200)
        job.cancelAndJoin()
    }

    println("Took $duration ms")
}