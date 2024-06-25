package tk.zwander

import kotlinx.coroutines.runBlocking
import tk.zwander.common.tools.Request

fun main() {
    println("Starting...")
    runBlocking {
        val info = Request.retrieveBinaryFileInfo(
            fw = "T290XXS5CWG5/T290OXM5CWG5/T290XXS5CWG5/T290XXS5CWG5",
            model = "SM-T290",
            region = "PHN",
            imeiSerial = "R9WMC1ZDYZJ",
            onFinish = {
                println(it)
            },
            onVersionException = null
        )
        println(info)
    }
}