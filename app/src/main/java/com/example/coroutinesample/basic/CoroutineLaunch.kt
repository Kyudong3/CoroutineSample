package com.example.coroutinesample.basic

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

/* 결과를 반환하지 않는 coroutine - launch
*  연산이 실패한 경우에만 통보 받기 원하는 fire-and-forget
*  fire-and-forget -> 미사일을 발사하면 미사일은 알아서 표적을 향해 날아가는데, 미사일에 대해 잊고 있어도 알아서 표적에 명중한다는 것.
*  실행 후 결과에 대해서 신경 쓸 필요 없는 경우 */
fun main() = runBlocking {
    val task = GlobalScope.launch {
        doSomething()
    }
    task.join()
    println("completed")
}
/* 예외가 출력되지만 실행이 중단되지는 않음 */

fun doSomething() {
    throw UnsupportedOperationException("Can't do")
}