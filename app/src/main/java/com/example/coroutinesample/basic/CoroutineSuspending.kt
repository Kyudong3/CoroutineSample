package com.example.coroutinesample.basic

import com.example.coroutinesample.repository.ProfileServiceClient
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking

/* Using suspending Function ( operations )
*  suspending function can be called in other suspending functions or coroutines
*  일시 중단 함수는 비동기보다 유연하고, 간단하다. 비동기 함수를 사용하면 async, await 을 호출해야하는데, 그럴 필요가 없어진다 */

/*  비동기 함수 대신 일시 중단 함수를 사용하기 위한 가이드라인
*   1. 일반적으로 구현에 Job이 엮이는 것을 피하기 위해서 사용
*   2. 인터페이스를 정의할 때는 항상 일시 중단 함수 사용. 비동기 함수를 사용하면 Job을 반환하기 위해 구현을 해야한다
*   3. 마찬가지로 추상 함수를 정의할 때는 항상 일시 중단 함수 사용해야 한다. 가시성이 높은 함수일 수록 일시 중단 함수를 사용해야 한다.
* */
fun main() {
    // 실행 안됨
    // greetDelayed(1000)
    // 실행 됨
    runBlocking {
        greetDelayed(1000)
    }

    runBlocking {
        val repository = ProfileServiceClient()
        val profile = repository.asyncFetchById(12)
        println(profile)
    }
}

suspend fun greetDelayed(delayMillis: Long) {
    delay(delayMillis)
    println("Hello, World!")
}