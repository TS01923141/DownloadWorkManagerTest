package com.example.downloadworkmanagertest.module

import android.content.Context
import android.content.ContextWrapper
import androidx.appcompat.app.AppCompatActivity
import androidx.work.Data
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import java.io.File

class DownloadRepository(private val context: Context) {
    private val downloadInfos : MutableList<DownloadInfo> = arrayListOf()

//    fun prepare(): String? {
//        val cw = ContextWrapper(context)
//        val directory = cw.getDir("downloadFiles", AppCompatActivity.MODE_PRIVATE)
//        val downloadInfo = DownloadInfo(
//            File(directory, "02d@2x.png").absolutePath,
//            "http://openweathermap.org/img/wn/02d@2x.png"
//        )
//        return Util.serializeToJson(downloadInfo)
//    }
//    fun startDownload(){
//        val downloadInfoJson = prepare()
//        val constraints = androidx.work.Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).build()
//        val inputData = Data.Builder().putString(DOWNLOAD_INFO, downloadInfoJson).build()
//        val task = OneTimeWorkRequest.Builder(DownloadWorkManager::class.java)
//            .setInputData(inputData)
//            .setConstraints(constraints)
//            .build()
//        WorkManager.getInstance(this).enqueue(task)
//    }
//    fun buildNotification() {
//
//    }
}