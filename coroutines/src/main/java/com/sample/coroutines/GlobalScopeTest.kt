package com.sample.coroutines

import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineName
import kotlin.concurrent.thread
import kotlin.coroutines.*

/**
 * @date: 2021/6/23
 * @author: ice_coffee
 * remark:
 */
class GlobalScopeTest {
    private val name = "张三"

    companion object {
        val test = GlobalScopeTest()

        @JvmStatic
        fun main(args: Array<String>) {
//            test.testCreateCoroutine()
            test.callCoroutineContext()
        }
    }

//    fun runGlobalScope() {
//        GlobalScope.launch { // 在后台启动一个新的协程并继续
//            println("World!") // 在延迟后打印输出
//        }
//    }
//
//    fun runBlocking() = runBlocking {
//        launch {
//            delay(200L)
//            println("Task from runBlocking")
//        }
//        println("Coroutine scope is over") // 这一行在内嵌 launch 执行完毕后才输出
//    }


    fun callLaunchCoroutine() {
        // 3 - 2 -> 由于 delay() 函数将协程延迟了一段时间, 而主线程在调用过 callLaunchCoroutine()
        launchCoroutine(ProducerScope<Int>()) {
            println("launchReceiverCoroutine - start")
            produce(1000)
            /**
             * 由于 delay() 会将将协程延迟一段时间, 所以要保证之后的代码运行需要调用线程也延迟一段时间
             * delay(1000)
             */
            produce(4000)
            println("launchReceiverCoroutine - end")
        }

        // 3 - 1 变种
        lanchCreateCoroutine {
            println("lanchCreateCoroutine - start")
            println(name)

            val resultFun = suspendFunc02()
            println("suspendFunc02: $resultFun")

            val result = notSuspend()
            println("notSuspend: $result")
        }
    }

    fun callCoroutineContext() {
//        var coroutineContext:CoroutineContext = EmptyCoroutineContext
//        coroutineContext += CoroutineName("c0-01")
//        coroutineContext += CoroutineExceptionHandler { coroutineContext, throwable ->  }

        suspend {
            suspendFunc02()
            suspendFunc02()
        }.startCoroutine(object : Continuation<Int> {
            override val context: CoroutineContext = LogInterceptor()

            override fun resumeWith(result: Result<Int>) {
                result.onFailure {
                    context[CoroutineExceptionHandler]?.handleException(context, it)
                }
                result.onSuccess {
                    println("Coroutine End: $result")
"?:"                }
            }
        })
    }
}

// 3 - 1
fun testCreateCoroutine() {
    val continuation = suspend {
        println("In Coroutine")
        5
    }.createCoroutine(object : Continuation<Int> {
        override fun resumeWith(result: Result<Int>) {
            println(result)
        }

        override val context = EmptyCoroutineContext
    })

    continuation.resume(Unit)
}

// ------------------------------------------------------------

// 3 - 1 封装
fun <T> lanchCreateCoroutine(block: suspend () -> T) {
    block.startCoroutine(object : Continuation<T> {
        override val context = EmptyCoroutineContext

        override fun resumeWith(result: Result<T>) {
            println("Coroutine End: $result")
        }
    })
}

// ------------------------------------------------------------

// 3 - 2
fun <R, T> launchCoroutine(receiver: R, block: suspend R.() -> T) {
    block.startCoroutine(receiver, object : Continuation<T> {
        override val context = EmptyCoroutineContext

        override fun resumeWith(result: Result<T>) {
            println("Coroutine End: $result")
        }
    })
}

class ProducerScope<T> {
    suspend fun produce(value: T) {
        println(value)
    }
}

// ------------------------------------------------------------

// 3 - 7
suspend fun suspendFunc02() = suspendCoroutine<Int> { continuation ->
    thread {
        continuation.resumeWith(Result.success(5))
    }
}

// ------------------------------------------------------------

// 3 - 8
suspend fun notSuspend() = suspendCoroutine<Int> { continuation ->
    continuation.resume(5)
}

class LogInterceptor : ContinuationInterceptor {
    override val key = ContinuationInterceptor

    override fun <T> interceptContinuation(continuation: Continuation<T>) = LogContinuation(continuation)
}

class LogContinuation<T>(private val continuation: Continuation<T>) : Continuation<T> by continuation {

    override fun resumeWith(result: Result<T>) {
        println("before resumeWith: $result")
        continuation.resumeWith(result)
        println("after resumeWith")
    }
}