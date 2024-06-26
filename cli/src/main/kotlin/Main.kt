package tk.zwander

import io.ktor.utils.io.*
import io.ktor.utils.io.core.internal.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import tk.zwander.common.data.BinaryFileInfo
import tk.zwander.common.tools.CryptUtils
import tk.zwander.common.tools.FusClient
import tk.zwander.common.tools.Request
import tk.zwander.common.tools.VersionFetch
import tk.zwander.common.tools.delegates.Downloader
import tk.zwander.common.util.*
import tk.zwander.commonCompose.model.DownloadModel
import tk.zwander.samloaderkotlin.resources.MR
import kotlin.time.ExperimentalTime

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

        if (fileInfo != null) {
            performDownload(fileInfo, fw, region, model)
        }
    }
}

suspend fun getLatestVersionInfo(model: String, region: String): String {
    val (fw, os, error, output) = VersionFetch.getLatestVersion(
        model = model,
        region = region,
    )

    if (error == null) {
        //println(output.replace("\t", "  "))
        //println(fw)
        //println(os)
        return fw
    } else {
        println(error.message.toString())
        return ""
    }
}

// Incredibly beautiful copy of common/src/commonMain/kotlin/tk/zwander/common/tools/delegates/Downloader.kt:78
@OptIn(DangerousInternalIoApi::class, ExperimentalTime::class)
suspend fun performDownload(info: BinaryFileInfo, fw: String, region: String, model: String) {
    try {
        val (path, fileName, size, crc32, v4Key) = info
        val request = Request.createBinaryInit(fileName, FusClient.getNonce())

        FusClient.makeReq(FusClient.Request.BINARY_INIT, request)

        val fullFileName = fileName.replace(
            ".zip",
            "_${fw.replace("/", "_")}_${region}.zip",
        )

        val decryptionKeyFileName = if (BifrostSettings.Keys.enableDecryptKeySave()) {
            "DecryptionKey_${fullFileName}.txt"
        } else {
            null
        }

        eventManager.sendEvent(
            Event.Download.GetInput(fullFileName, decryptionKeyFileName) { inputInfo ->
                if (inputInfo != null) {
                    inputInfo.decryptKeyFile?.openOutputStream(false)?.use { output ->
                        if (fullFileName.endsWith(".enc2")) {
                            output.write(
                                CryptUtils.getV2Key(
                                    fw,
                                    model,
                                    region,
                                ).second.toByteArray(),
                            )
                        }

                        v4Key?.let {
                            output.write(v4Key.second.toByteArray())
                        }
                    }

                    val outputStream =
                        inputInfo.downloadFile.openOutputStream(true) ?: return@GetInput
                    val md5 = try {
                        FusClient.downloadFile(
                            path + fileName,
                            inputInfo.downloadFile.getLength(),
                            size,
                            outputStream,
                            inputInfo.downloadFile.getLength(),
                        ) { current, max, bps ->
                            //model.progress.value = current to max
                            //model.speed.value = bps

                            eventManager.sendEvent(
                                Event.Download.Progress(
                                    MR.strings.downloading(),
                                    current,
                                    max,
                                )
                            )
                        }
                    } finally {
                        outputStream.flush()
                        outputStream.close()
                    }

                    if (crc32 != null) {
                        //model.speed.value = 0L
                        //model.statusText.value = MR.strings.checkingCRC()
                        val result = CryptUtils.checkCrc32(
                            inputInfo.downloadFile.openInputStream() ?: return@GetInput,
                            size,
                            crc32,
                        ) { current, max, bps ->
                            //model.progress.value = current to max
                            //model.speed.value = bps

                            eventManager.sendEvent(
                                Event.Download.Progress(
                                    MR.strings.checkingCRC(),
                                    current,
                                    max
                                )
                            )
                        }

                        if (!result) {
                            //model.endJob(MR.strings.crcCheckFailed())
                            return@GetInput
                        }
                    }

                    if (md5 != null) {
                        //model.speed.value = 0L
                        //model.statusText.value = MR.strings.checkingMD5()

                        eventManager.sendEvent(
                            Event.Download.Progress(
                                MR.strings.checkingMD5(),
                                0,
                                1
                            )
                        )

                        val result = withContext(Dispatchers.Default) {
                            CryptUtils.checkMD5(
                                md5,
                                inputInfo.downloadFile.openInputStream(),
                            )
                        }

                        if (!result) {
                            //model.endJob(MR.strings.md5CheckFailed())
                            return@GetInput
                        }
                    }

                    //model.speed.value = 0L
                    //model.statusText.value = MR.strings.decrypting()

                    val key =
                        if (fullFileName.endsWith(".enc2")) {
                            CryptUtils.getV2Key(
                                fw,
                                model,
                                region,
                            ).first
                        } else {
                            info.v4Key?.first!!
                        }

                    CryptUtils.decryptProgress(
                        inputInfo.downloadFile.openInputStream() ?: return@GetInput,
                        inputInfo.decryptFile.openOutputStream() ?: return@GetInput,
                        key,
                        size,
                    ) { current, max, bps ->
                        //model.progress.value = current to max
                        //model.speed.value = bps

                        eventManager.sendEvent(
                            Event.Download.Progress(
                                MR.strings.decrypting(),
                                current,
                                max
                            )
                        )
                    }

                    if (BifrostSettings.Keys.autoDeleteEncryptedFirmware()) {
                        inputInfo.downloadFile.delete()
                    }

                    //model.endJob(MR.strings.done())
                } else {
                    //model.endJob("")
                }
            }
        )
    } catch (e: Throwable) {
        val message = if (e !is CancellationException) "${e.message}" else ""
        //model.endJob(message)
    }

    eventManager.sendEvent(Event.Download.Finish)
}