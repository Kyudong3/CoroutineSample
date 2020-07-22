package com.example.coroutinesample.basic

import kotlinx.coroutines.*

/*
* fire and forget 작업. 코루틴 빌더인 launch() 를 사용해 생성하는 것이 일반적
* LifeCycle
* New(생성) 존재하지만 아직 실행되지 않는 잡
* Active(활성) 실행중인 잡
* Completed(완료 됨) 잡이 더 이상 실행되지 않음
* Canceling(취소 중) 실행 중인 잡에서 cancel() 이 호출된 중간 상태
* Cancelled(취소 됨) 취소로 인해 실행이 완료된
* */

fun main() = runBlocking {
    val job = GlobalScope.launch {
        // Do background task
    }
    val job2 = Job()

    /* 잡이 자동으로 시작되지 않도록 Lazy 설정 */
    val lazyJob = GlobalScope.launch {
    }.invokeOnCompletion { cause ->
        cause?.let {
            println("job cancelled due to ${it.message}")
        }
    }

    // 잡이 완료될때까지 기다리지 않음 ( 일시 중단하지 않음 )
    // lazyJob.start()

    // job이 완료될때까지 대기 ( join은 실행을 일시 중단할 수 있어서 코루틴이나 일시 중단 함수에서 호출해야 한다 )
    // lazyJob.join()

    delay(2000)

    /* */
}