import kotlinx.coroutines.*

fun main() = runBlocking {
    println("start")
    launch {
        println("finish")
    }
    launch {
        println("&")
    }
    doWorld()
    println("yo")


}

suspend fun doWorld() = coroutineScope {  // this: CoroutineScope
    launch {
        delay(2000L)
        println("World!")
    }
    launch {
        println("world2")
    }
    println("Hello")
}