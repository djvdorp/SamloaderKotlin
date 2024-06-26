package tk.zwander

import kotlinx.coroutines.runBlocking
import tk.zwander.common.tools.Request
import tk.zwander.common.tools.VersionFetch

fun main(): Unit = runBlocking {
    val model = "SM-T290"
    val region = "PHN"
    val imeiSerial = "R9WMC1ZDYZJ"

    println("Starting...")

    getLatestVersionInfo(model, region)
        .onSuccess { fw ->
            println("Got latest version info: $fw")
            Request.retrieveBinaryFileInfo(
                fw = fw,
                model = model,
                region = region,
                imeiSerial = imeiSerial,
                onFinish = {
                    println(it)
                },
                onVersionException = null
            ).also {
                println(it)
            }
        }
        .onFailure {
            println(it)
        }
}

suspend fun getLatestVersionInfo(model: String, region: String): Result<String> {
    val (fw, os, error, output) = VersionFetch.getLatestVersion(
        model = model,
        region = region,
    )

    if (error == null) {
        //println(output.replace("\t", "  "))
        //println(fw)
        //println(os)
        return Result.success(fw)
    }
    return Result.failure(Exception(error.message.toString()))
}