package com.example.downloadworkmanagertest

import android.content.ContextWrapper
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.work.*
import com.example.downloadworkmanagertest.module.DOWNLOAD_INFO
import com.example.downloadworkmanagertest.module.DownloadInfo
import com.example.downloadworkmanagertest.module.DownloadWorkManager
import com.example.downloadworkmanagertest.module.Util
import com.google.gson.Gson
import java.io.File

/**
 * WorkManager非succeed會自動重啟，關閉APP會同步結束，不適用於下載檔案
 */

//要求權限
//notification建立
//用觀察者模式更新下載狀態
//Retrofit的BASE_URL可調整為想要的
//準備下載清單
private const val TAG = "MainActivity"
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val cw = ContextWrapper(this)
        val directory = cw.getDir("downloadFiles", MODE_PRIVATE)
        val downloadInfo = DownloadInfo(
            File(directory, "MOI_OSM_Taiwan_TOPO_Rudy.map.zip").absolutePath,
        "http://moi.kcwu.csie.org/MOI_OSM_Taiwan_TOPO_Rudy.map.zip"
        )
//        val downloadInfo = DownloadInfo(
//            File(directory, "02d@2x.png").absolutePath,
//            "http://openweathermap.org/img/wn/02d@2x.png"
//        )

        val constraints = androidx.work.Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).build()
        val inputData = Data.Builder().putString(DOWNLOAD_INFO, Util.serializeToJson(downloadInfo)).build()
        val task = OneTimeWorkRequest.Builder(DownloadWorkManager::class.java)
            .setInputData(inputData)
            .setConstraints(constraints)
            .build()
        WorkManager.getInstance(this).enqueue(task)

        WorkManager.getInstance(this).getWorkInfoByIdLiveData(task.id)
            .observe(this, Observer {
                val progress = it.progress.getInt(DownloadWorkManager.PROGRESS, 0)
                Toast.makeText(this, "progress: $progress", Toast.LENGTH_SHORT).show()
                when(it.state){
                    WorkInfo.State.SUCCEEDED -> {
                        Log.d(TAG, "onCreate: succeed")
                        Log.d(TAG, "onCreate: succeed progress: " + it.progress.getInt(DownloadWorkManager.PROGRESS, 0))
                        //notification顯示完成
//                        Toast.makeText(this, "succeed progress: $progress", Toast.LENGTH_SHORT).show()
                    }
                    WorkInfo.State.FAILED -> {
                        Log.d(TAG, "onCreate: failed")
                        Log.d(TAG, "onCreate: failed progress: " + it.progress.getInt(DownloadWorkManager.PROGRESS, 0))
                        //notification顯示失敗
//                        Toast.makeText(this, "failed progress: $progress", Toast.LENGTH_SHORT).show()
                        WorkManager.getInstance(this).cancelWorkById(task.id)
                    }
                    WorkInfo.State.RUNNING -> {
                        Log.d(TAG, "onCreate: running")
                        Log.d(TAG, "onCreate: running progress: " + it.progress.getInt(DownloadWorkManager.PROGRESS, 0))
                        //notification更新進度條
//                        Toast.makeText(this, "running progress: $progress", Toast.LENGTH_SHORT).show()
                    }
                }
            })
    }
}