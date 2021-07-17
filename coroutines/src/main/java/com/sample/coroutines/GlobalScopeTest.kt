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
            test.callCreateCoroutine()
            test.callLaunchCoroutine()
            test.callCoroutineContext()
        }
    }

    /**
     * 创建协程, 并探索挂起函数的奥秘
     */
    fun callCreateCoroutine() {
        testCreateCoroutine()

        // 3 - 1 变种
        lanchCreateCoroutine {
            println("lanchCreateCoroutine - start")
            println(name)

            println("lanchCreateCoroutine: ${System.currentTimeMillis()}")
            val resultFun = suspendFunc02()
            println("lanchCreateCoroutine: suspendFunc02-$resultFun-${System.currentTimeMillis()}")

            val result = notSuspend()
            println("lanchCreateCoroutine: notSuspend-$result-${System.currentTimeMillis()}")
        }
    }

    /**
     * 也是创建协程, 但是这种方式创建的协程可以调用 receiver 的函数和状态信息.
     */
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
    }

    /**
     * 协程上下文, 拦截器
     */
    fun callCoroutineContext() {
        var coroutineContext:CoroutineContext = EmptyCoroutineContext
        coroutineContext += CoroutineName("c0-01")
        coroutineContext += CoroutineExceptionHandler { coroutineContext, throwable ->  }
        coroutineContext += LogInterceptor()

        suspend {
            suspendFunc02()
            suspendFunc02()
//            notSuspend()
//            notSuspend()
        }.startCoroutine(object : Continuation<Int> {
            override val context: CoroutineContext = EmptyCoroutineContext

            override fun resumeWith(result: Result<Int>) {
                result.onFailure {
                    context[CoroutineExceptionHandler]?.handleException(context, it)
                }
                result.onSuccess {
                    println("Coroutine End: ${context[CoroutineName]?.name}, $result")
                }
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
        Thread.sleep(1000)
        continuation.resumeWith(Result.success(5))
    }
}

// ------------------------------------------------------------

// 3 - 8
suspend fun notSuspend() = suspendCoroutine<Int> { continuation ->
    continuation.resume(5)
}

// 3 - 20
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