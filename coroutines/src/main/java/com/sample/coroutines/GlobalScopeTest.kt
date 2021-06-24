package com.sample.coroutines

import kotlinx.coroutines.*

/**
 * @date: 2021/6/23
 * @author: ice_coffee
 * remark:
 */
class GlobalScopeTest {
    companion object {
        val test = GlobalScopeTest()
        @JvmStatic
        fun main(args: Array<String>) {
            test.runBlocking()
//            test.runGlobalScope()
//            println("coroutines!")
        }
    }

    fun runGlobalScope() {
        GlobalScope.launch { // 在后台启动一个新的协程并继续
            delay(1000L) // 非阻塞的等待 1 秒钟（默认时间单位是毫秒）
            println("World!") // 在延迟后打印输出
        }
        println("Hello,") // 协程已在等待时主线程还在继续
        Thread.sleep(2000L) // 阻塞主线程 2 秒钟来保证 JVM 存活
    }

    fun runBlocking() = runBlocking {
        launch {
            delay(200L)
            println("Task from runBlocking")
        }

        launch {
            delay(700L)
            println("Task from runBlocking11")
        }

        coroutineScope { // 创建一个协程作用域
            launch {
                delay(500L)
                println("Task from nested launch")
            }

            delay(100L)
            println("Task from coroutine scope") // 这一行会在内嵌 launch 之前输出
        }

        println("Coroutine scope is over") // 这一行在内嵌 launch 执行完毕后才输出
    }
}