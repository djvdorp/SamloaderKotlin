package tk.zwander

import kotlinx.coroutines.runBlocking
import tk.zwander.common.tools.Request
import tk.zwander.common.tools.VersionFetch

fun main() {
    val model: String = "SM-T290"
    val region: String = "PHN"
    val imeiSerial: String = "R9WMC1ZDYZJ"

    println("Starting...")
    runBlocking {
        val fw = getLatestVersionInfo(model, region)
        println(fw)

        val fileInfo = Request.retrieveBinaryFileInfo(
            fw = fw, // "T290XXS5CWG5/T290OXM5CWG5/T290XXS5CWG5/T290XXS5CWG5"
            model = model,
            region = region,
            imeiSerial = imeiSerial,
            onFinish = {
                println(it)
            },
            onVersionException = null
        )
        println(fileInfo)
    }
}

suspend fun getLatestVersionInfo(model: String, region: String): String {
    val (fw, os, error, output) = VersionFetch.getLatestVersion(
        model = model,
        region = region,
    )

    if (error == null) {
        //println(output.replace("\t", "  "))
        println(fw)
        //println(os)
        return fw
    } else {
        println(error.message.toString())
        return ""
    }
}