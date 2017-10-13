package com.slamli.mydemo

import android.Manifest
import android.media.AudioFormat
import android.media.AudioRecord
import android.media.MediaRecorder
import android.os.Bundle
import android.os.Environment
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Toast
import com.tbruyelle.rxpermissions2.RxPermissions
import java.io.DataOutputStream
import java.io.File
import java.io.FileOutputStream

/**
 * Created by slam.li on 2017/8/15.
 * http://www.jianshu.com/p/af7787b409a2
 * audio record, audio track
 */
class AudioActivity: AppCompatActivity() {
    var audioRecord: AudioRecord? = null
    var bufferSizeInBytes: Int = 0
    var isStart: Boolean = false
    var dos: DataOutputStream? = null
    var recordThread: Thread? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_audio)
        val rxPermissions = RxPermissions(this)
        rxPermissions.request(Manifest.permission.RECORD_AUDIO, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .subscribe({ if (it) Toast.makeText(this, "get permission success", Toast.LENGTH_SHORT).show() })

        bufferSizeInBytes = AudioRecord.getMinBufferSize(44100, AudioFormat.CHANNEL_IN_MONO, AudioFormat.ENCODING_PCM_16BIT)

        findViewById(R.id.btn_start).setOnClickListener({startRecord(Environment.getExternalStorageDirectory().absolutePath+"/temp.wav")})
        findViewById(R.id.btn_stop).setOnClickListener({ stopRecord() })
    }

    var recordRunnable = Runnable { try {
        android.os.Process.setThreadPriority(android.os.Process.THREAD_PRIORITY_AUDIO)
        val tempBuffer: ByteArray = ByteArray(bufferSizeInBytes)
        audioRecord?.startRecording()

        var bytesRecord: Int
        while (isStart) {
            bytesRecord = audioRecord?.read(tempBuffer, 0, bufferSizeInBytes) as Int
            if (bytesRecord == AudioRecord.ERROR_INVALID_OPERATION ||
                    bytesRecord == AudioRecord.ERROR_BAD_VALUE ||
                    bytesRecord == AudioRecord.ERROR_DEAD_OBJECT) {
                continue
            }
            if (bytesRecord != AudioRecord.ERROR) {
                //在此可以对录制音频的数据进行二次处理 比如变声，压缩，降噪，增益等操作
                //我们这里直接将pcm音频原数据写入文件 这里可以直接发送至服务器 对方采用AudioTrack进行播放原数据
                dos?.write(tempBuffer, 0, bytesRecord)
            } else {
                break
            }
        }
    } catch (e: Exception) {
        e.printStackTrace()
    }}

    fun initAudioRecord() {
        if (audioRecord == null) {
            audioRecord = AudioRecord(MediaRecorder.AudioSource.MIC, 44100, AudioFormat.CHANNEL_IN_MONO, AudioFormat.ENCODING_PCM_16BIT, bufferSizeInBytes)
        }
    }

    fun setOutputPath(path: String) {
        val file = File(path)
        if (file.exists()) {
            file.delete()
        }
        Log.i("path", path)
        file.createNewFile()
        dos = DataOutputStream(FileOutputStream(file))
    }

    fun destroyThread() {
        try {
            isStart = false
            if (recordThread != null && recordThread?.state == Thread.State.RUNNABLE) {
                try {
                    Thread.sleep(500)
                    recordThread?.interrupt()
                } catch (e: Exception) {
                    recordThread = null
                }
            }
            recordThread = null
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            recordThread = null
        }
    }

    fun startThread() {
        destroyThread()
        initAudioRecord()
        isStart = true
        if (recordThread == null) {
            recordThread = Thread(recordRunnable)
            recordThread?.start()
        }
    }

    fun startRecord(path: String) {
        try {
            setOutputPath(path)
            startThread()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun stopRecord() {
        try {
            destroyThread()
            if (audioRecord?.state == AudioRecord.STATE_INITIALIZED) {
                audioRecord?.stop()
            }
            audioRecord?.release()

            dos?.flush()
            dos?.close()
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            dos?.flush()
            dos?.close()
            audioRecord = null
        }
    }
}